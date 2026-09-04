# rct-clr

Generates CLR-compatible test files from [Rich Comment Tests](https://github.com/robertluo/rich-comment-tests) (`^:rct/test`) blocks.

## Rationale

A cross-platform library runs its `deftest` suites on the CLR. Its `^:rct/test` blocks are tests too, but `rich-comment-tests` needs rewrite-clj and tools.namespace to extract them, and neither runs on the CLR.

Porting RCT would mean porting its reader and its rewriter, when the blocks only have to become assertions. `nos` and `cljr` already run `deftest`, so this generates one.

## Writing cross-platform RCT tests

Standard `^:rct/test` blocks work unchanged: the generator handles the platform differences.

```clojure
;;;; Reader conditionals in test expectations
;;
;; When a function returns different values per platform, use #? in the
;; expectation.

(defn platform []
  #?(:clj :jvm :cljr :clr))

^:rct/test
(comment
  (platform) ;=> #?(:clj :jvm :cljr :clr)
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
  ;throws=>>
  {:error/message "must be positive"
   :error/data {:value -1}}
  )

;;;; Reader conditionals in test expressions
;;
;; Reader conditionals cannot be used in test expressions, use separate
;; files for each platform's interop instead. See issue #10.

;; -- examples_clr/rct_clr/sample_clr.cljc (generator scans this) --

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  (.Message (make-error "boom")) ;=> "boom"
  )

;; -- examples_jvm/rct_clr/sample_jvm.cljc (RCT runner tests this) --

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  (.getMessage (make-error "boom")) ;=> "boom"
  )
```

See [`examples/`](examples/), [`examples_clr/`](examples_clr/), and [`examples_jvm/`](examples_jvm/) for complete working examples.

## How it works

1. **Extract (JVM):** `rct-clr.gen` scans `.clj` and `.cljc` source files, loads each namespace, finds every `^:rct/test` block, and writes the assertions into a plain `.cljc` test file.
2. **Test (CLR):** Run that file with `clojure.test` on MAGIC or ClojureCLR. It needs only `clojure.test` and `matcho.core`.

## Prerequisites

- JVM Clojure, to run the generator
- [MAGIC and Nostrand](https://github.com/flybot-sg/magic), or [ClojureCLR](https://github.com/clojure/clojure-clr), to run the generated tests

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

CLR coordinates go in [`deps-clr.edn`](https://github.com/flybot-sg/magic/blob/main/docs/clr-dependency-files.md). Two entries matter here: matcho, which the generated tests call for `=>>` patterns, and the ClojureCLR test runner that drives `cljr -X:test`.

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

#### `rc_test.clj`, the RCT runner

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

[`nos test`](https://github.com/flybot-sg/magic/blob/main/docs/nos-cli.md) derives its namespaces from the source paths, so it picks up the JVM-only RCT runner too. Exclude it:

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

- A namespace with `^:clr-only` metadata, which JVM test runners filtering on it skip
- Three helpers, since the generated file requires only `clojure.test` and `matcho.core` and so cannot call RCT's own: `error->map` builds the map a `throws=>>` pattern matches against, `eval-expectation` evaluates a `=>` expectation and falls back to the form when that throws, and `bind-repl-vars!` carries each result into `*1`
- One `deftest` per source namespace, binding `*ns*` and the REPL vars, with `clojure.test/is` for `=>`, `matcho.core/assert` for `=>>`, and `try`/`catch` plus matcho for `throws=>>`
- A form with no assertion (`def`, `require`) is emitted for its side effect

Example output (abbreviated):

```clojure
(ns ^:clr-only my-project.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [my-project.core]))

(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})

(defn eval-expectation [form]
  (try
    (eval form)
    (catch #?(:clj Exception :cljr System.Exception) _
      form)))

(defn bind-repl-vars! [result]
  (set! *3 *2)
  (set! *2 *1)
  (set! *1 result)
  result)

;; my-project.core
(defn- my-project-core-rct-block-0 []
  ;; core.cljc:42
  (testing "core.cljc:42"
    (eval (quote (clojure.test/is (= 4 (my-project.rct-generated-test/bind-repl-vars! (+ 2 2)))))))
  ;; core.cljc:45
  (testing "core.cljc:45"
    (eval (quote (matcho.core/assert {:status 200} (my-project.rct-generated-test/bind-repl-vars! (fetch)))))))

(deftest my-project-core-rct
  (binding [*ns* (the-ns 'my-project.core)
            *1 nil, *2 nil, *3 nil, *e nil]
    (my-project-core-rct-block-0)))
```
