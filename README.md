# rct-clr

Generates CLR-compatible test files from [Rich Comment Tests](https://github.com/robertluo/rich-comment-tests) (`^:rct/test`) blocks.

## Why run RCT tests on the CLR?

RCT tests already run on the JVM, but `.cljc` code targets both platforms. Running the generated tests on the CLR catches issues that JVM-only testing misses:

- **Exception handling:** CLR uses `System.Exception`, not `java.lang.Exception`. `throws=>>` assertions verify the correct exception type is thrown.
- **Interop correctness:** Method names differ between platforms (e.g. `.getMessage` vs `.Message`).
- **Runtime differences:** Magic/Nostrand run on Clojure 1.10 with CLR-specific runtime behavior.

## Writing cross-platform RCT tests

Standard `^:rct/test` blocks work unchanged — the generator handles the platform differences. These examples show patterns that are especially useful for cross-platform code.

```clojure
;;;; Reader conditionals in test expectations
;;
;; When a function returns different values per platform, use #? in the
;; expectation.

(defn platform []
  #?(:clj :jvm :cljr :clr))

^:rct/test
(comment
  (platform)
  ;=> #?(:clj :jvm :cljr :clr)
  )

;;;; Exception assertions with throws=>>
;;
;; throws=>> verifies that a function throws and pattern-matches the error.
;; The generator emits catch System.Exception for CLR, so this validates
;; CLR exception types and error data.
;;
;; The generated error->map helper extracts :error/class, :error/message,
;; and :error/data from the exception, so you can match on any combination.

(defn validate-positive! [x]
  (when-not (pos? x)
    (throw (ex-info "must be positive" {:value x}))))

^:rct/test
(comment
  (validate-positive! -1)
  ;throws=>> {:error/message "must be positive"
              :error/data {:value -1}}
  )

;;;; Reader conditionals in test expressions
;;
;; Reader conditionals cannot be used in test expressions — use separate
;; files for each platform's interop instead. See issue #10.

;; -- examples_clr/rct_clr/sample_clr.cljc (generator scans this) --

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  (.Message (make-error "boom"))
  ;=> "boom"
  )

;; -- examples_jvm/rct_clr/sample_jvm.cljc (RCT runner tests this) --

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  (.getMessage (make-error "boom"))
  ;=> "boom"
  )
```

See [`examples/`](examples/), [`examples_clr/`](examples_clr/), and [`examples_jvm/`](examples_jvm/) for complete working examples.

## How it works

RCT depends on rewrite-clj and tools.namespace, which are JVM-only. This tool pre-extracts RCT test data into a plain `.cljc` test file that CLR ([Magic](https://github.com/nasser/magic)/[Nostrand](https://github.com/nasser/nostrand)) can run using only `clojure.test` and `matcho.core`.

1. **Extract (JVM):** Run `rct-clr.gen` on the JVM, where rewrite-clj and tools.namespace are available. It scans `.cljc` source files, loads each namespace, finds all `^:rct/test` comment blocks, and writes the assertions into a plain `.cljc` test file. (`.clj` files are ignored.)
2. **Test (CLR):** Run the generated file on Magic/Nostrand using `clojure.test`. No JVM-only dependencies are needed at test time.

## Prerequisites

- JVM Clojure (for running the generator)
- [Magic](https://github.com/nasser/magic)/[Nostrand](https://github.com/nasser/nostrand) on the target CLR platform (for running generated tests)

## Usage

```bash
clojure -M:dev -m rct-clr.gen \
  -o test/my_project/rct_generated_test.cljc \
  -n my-project.rct-generated-test
```

### Options

| Flag                   | Description                                                  | Default |
| ---------------------- | ------------------------------------------------------------ | ------- |
| `-s`, `--src-dir DIR`  | Source directory to scan (repeatable, e.g. `-s src -s src2`) | `src`   |
| `-o`, `--output PATH`  | Output file path (required)                                  |         |
| `-n`, `--namespace NS` | Output namespace (required)                                  |         |
| `-h`, `--help`         | Show help                                                    |         |

## Using it for your repository

### CLR testing setup

#### deps.edn

Add as a dev dependency:

```clojure
{:aliases
 {:dev {:extra-deps {io.github.flybot-sg/rct-clr
                     {:git/url "https://github.com/flybot-sg/rct-clr"
                      :git/sha "..."}}}}}
```

Since `rct-clr` transitively brings in `rich-comment-tests`, you can remove any existing direct RCT dependency from your `deps.edn`.

#### deps-clr.edn

`nos` and `cljr` read `deps-clr.edn` in place of `deps.edn`, so the CLR coordinates live there. The generated tests call `matcho.core/assert` for `=>>` patterns, so declare matcho, and the ClojureCLR test runner to drive `cljr -X:test`:

```clojure
{:paths ["src"]
 :aliases
 {:test {:extra-paths ["test"]
         :extra-deps  {io.github.dmiller/test-runner {:git/tag "v0.5.3clr"
                                                      :git/sha "ae91dd2727bbf70eb3a6d869a19953de3819dfbc"}
                       flybot-sg/matcho              {:git/url "https://github.com/flybot-sg/matcho"
                                                      :git/sha "fba2a65485f4d5b1e0a69f94a3d06c467478f53f"}}
         :exec-fn     cognitect.test-runner.api/test
         ;; the JVM-only RCT runner lives in test/ too, and cljr would load it
         :exec-args   {:dirs     ["test"]
                       :patterns ["my-project\\.(?!rc-test$).*"]}}}}
```

Pin matcho's `clr-support` branch, not `master`: only that branch ships a `deps-clr.edn`, without which `cljr` cannot resolve it.

#### `bb.edn` - generating CLR test file

If you use Babashka to run scripts, you can do this too:

```clojure
{:tasks {gen-clr-rct
         {:doc  "Generate CLR-compatible RCT test file"
          ;; -M:dev, not -M:dev:test: a :test alias carrying kaocha's :main-opts
          ;; would shadow -m rct-clr.gen
          :task (clojure "-M:dev -m rct-clr.gen -o test/my_project/rct_generated_test.cljc -n my-project.rct-generated-test")}
         magic-test
         {:doc  "Regenerate the RCT test file and run the CLR tests on MAGIC"
          :task (do (run 'gen-clr-rct) (shell "nos" "test"))}
         cljr-test
         {:doc  "Regenerate the RCT test file and run the CLR tests on ClojureCLR"
          :task (do (run 'gen-clr-rct) (shell "cljr" "-X:test"))}}}
```

Run those two in CI rather than bare `nos test` / `cljr -X:test`, so the generated file cannot go stale.

### JVM testing setup

#### `rc_test.clj` — RCT runner

Create a test file that runs RCT blocks on the JVM using the `rich-comment-tests` runner:

```clojure
(ns my-project.rc-test
  (:require [clojure.test :refer [deftest testing]]
            [com.mjdowney.rich-comment-tests.test-runner :as test-runner]))

(deftest ^:rct rich-comment-tests
  (testing "Rich comment tests."
    (test-runner/run-tests-in-file-tree! :dirs #{"src"})))
```

#### `tests.edn`

Skip the generated CLR on JVM and split tests into `:rct` and `:unit` suites so they can be run independently:

```clojure
#kaocha/v1
 {:kaocha.filter/skip-meta [:clr-only]
  :tests [{:id :rct
           :focus-meta [:rct]}
          {:id :unit
           :skip-meta [:rct]}]}
```

#### `bb.edn` - running on JVM

To run only the RCT tests on JVM without running the full test suite:

```clojure
{:tasks {rct
         {:doc  "Run rct"
          :task (clojure "-M:dev:test --focus :rct")}}}
```

### magic.edn

`nos test` derives its namespaces from the source paths, which picks up the JVM-only RCT runner as well. Exclude it:

```clojure
{:test {:exclude [my-project.rc-test]}}
```

### .gitignore

Add the generated file to your `.gitignore`.

### CI notes

- If your CI caches untracked files (e.g. GitLab CI `cache: untracked: true`), delete the generated file before format checks to avoid stale copies causing failures:

  ```bash
  rm -f test/my_project/rct_generated_test.cljc
  ```

## Generated test structure

The generated file contains:

- A namespace with `^:clr-only` metadata (skipped by JVM test runners that filter on this)
- An `error->map` helper (replaces RCT's `error-datafy` which uses `ex-message`, unavailable on Magic/Clojure 1.10)
- One `deftest` per source namespace, with assertions using `clojure.test/is` for `=>`, `matcho.core/assert` for `=>>`, and `try`/`catch` with matcho matching for `throws=>>`
- Side-effect forms (e.g. `def`, `require`) from RCT blocks that have no assertion are emitted as bare `eval` calls

Example output (abbreviated):

```clojure
(ns ^:clr-only my-project.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest is testing]]
            [matcho.core]
            [my-project.core]))

(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})

;; my-project.core
(defn- my-project-core-rct-block-0 []
  ;; core.cljc:42
  (testing "core.cljc:42" (eval (quote (clojure.test/is (= 4 (+ 2 2))))))
  ;; core.cljc:45
  (testing "core.cljc:45" (eval (quote (matcho.core/assert {:status 200} (fetch))))))

(deftest my-project-core-rct
  (binding [*ns* (the-ns 'my-project.core)]
    (my-project-core-rct-block-0)))
```
