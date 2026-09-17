<p align="center">
  <img src="docs/logo.svg" width="96" height="96" alt="rct-clr logo">
</p>
<h1 align="center">rct-clr</h1>
<p align="center">
  <a href="https://clojars.org/sg.flybot/rct-clr"><img src="https://img.shields.io/clojars/v/sg.flybot/rct-clr.svg" alt="Clojars"></a>
  <img src="https://github.com/flybot-sg/rct-clr/actions/workflows/test.yml/badge.svg" alt="CI">
  <img src="https://img.shields.io/badge/license-Unlicense-blue.svg" alt="License: Unlicense">
</p>

<p align="center">
  Reads <a href="https://github.com/robertluo/rich-comment-tests">Rich Comment Tests</a> (<code>^:rct/test</code>) blocks on the JVM and writes a <code>deftest</code> file for the CLR.
</p>

## Rationale

A cross-platform library runs its `deftest` suites on the CLR. Its `^:rct/test` blocks are tests too. `rich-comment-tests` needs rewrite-clj and tools.namespace to read them. Nobody has ported rewrite-clj to the CLR.

tools.namespace has David Miller's port, `clr.tools.namespace`. ClojureCLR runs it. MAGIC does not load it: `clr.tools.reader` imports `clojure.lang.Reflector`. MAGIC drops that class to compile every call ahead of time for Unity.

Porting RCT means porting rewrite-clj, then carrying that fork. The blocks only have to become assertions. [`nos`](https://github.com/flybot-sg/magic/blob/main/docs/nos-cli.md) and [`cljr`](https://github.com/clojure/clr.core.cli) already run `deftest`, so this generates one.

The generated file targets the CLR, not both platforms:

- **`throws=>>` catches `System.Exception`**: emitted plain, with no reader conditional. The JVM compiler rejects that type
- **`#?` resolves at generation time**: the generator reads with `:features #{:cljr}`, so only the CLR branch reaches the file
- **The namespace carries `^:clr-only`**: a JVM test runner filtering on that metadata skips the file

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
  -o test-clr/my_project/rct_generated_test.cljc \
  -n my-project.rct-generated-test
```

Write the file to a CLR-only directory, not to `test/`. A ClojureScript build ignores `^:clr-only`. Keep the directory off the ClojureScript source paths, so no test discovery reaches it. The generated namespace requires matcho, and ClojureScript rejects matcho's `ns` form.

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

Remember that `rct-clr` is a **JVM** lib that generates `deftest`s runnable on the CLR.

Both entries are JVM-side:

```clojure
{:aliases
 {:dev {:extra-deps {;; runs the ^:rct/test blocks on the JVM
                     io.github.robertluo/rich-comment-tests {:mvn/version "1.1.82"}
                     ;; generates the CLR test file from those same blocks
                     sg.flybot/rct-clr                      {:mvn/version "0.1.1"}}}}}
```

Pin [`robertluo/rich-comment-tests`](https://github.com/robertluo/rich-comment-tests), the fork `rct-clr` matches. It keeps the original `com.mjdowney` namespace, so that is what you require.

#### deps-clr.edn

Once the `deftest` file is generated, you need a way to run it. That happens on the **CLR** side.

The CLR reads the generated file, never the generator.

CLR coordinates go in [`deps-clr.edn`](https://github.com/flybot-sg/magic/blob/main/docs/clr-dependency-files.md). Two entries matter here: matcho, which the generated tests call for `=>>` patterns, and the ClojureCLR test runner that drives `cljr -X:test`.

```clojure
{:paths ["src"]
 :aliases
 {:test {:extra-paths ["test" "test-clr"]
         :extra-deps  {;; test runner for ClojureCLR
                       io.github.dmiller/test-runner {:git/tag "v0.5.3clr"
                                                      :git/sha "ae91dd2727bbf70eb3a6d869a19953de3819dfbc"}
                       ;; the clr-support branch, which adds the deps-clr.edn upstream healthsamurai/matcho lacks.
                       flybot-sg/matcho              {:git/url "https://github.com/flybot-sg/matcho"
                                                      :git/sha "fba2a65485f4d5b1e0a69f94a3d06c467478f53f"}}
         :exec-fn     cognitect.test-runner.api/test
         ;; the JVM-only RCT runner lives in test/ too, and cljr would load it
         :exec-args   {:dirs     ["test" "test-clr"]
                       :patterns ["my-project\\.(?!rc-test$).*"]}}}}
```

#### magic.edn

[`nos test`](https://github.com/flybot-sg/magic/blob/main/docs/nos-cli.md) derives its namespaces from the source paths, so it picks up the JVM-only RCT runner too. Exclude it:

```clojure
;; test runner config for MAGIC
{:test {:exclude [my-project.rc-test]}}
```

#### `bb.edn` - generating CLR test file

If you use Babashka to run scripts, you can do this too:

```clojure
{:tasks {gen-clr-rct
         {:doc  "Generate CLR-compatible RCT test file"
          ;; -M:dev, not -M:dev:test: a :test alias carrying kaocha's :main-opts
          ;; would shadow -m rct-clr.gen
          :task (clojure "-M:dev -m rct-clr.gen -o test-clr/my_project/rct_generated_test.cljc -n my-project.rct-generated-test")}
         magic-test
         {:doc  "Regenerate the RCT test file and run the CLR tests on MAGIC"
          :task (do (run 'gen-clr-rct) (shell "nos" "test"))}
         cljr-test
         {:doc  "Regenerate the RCT test file and run the CLR tests on ClojureCLR"
          :task (do (run 'gen-clr-rct) (shell "cljr" "-X:test"))}}}
```

Run those two in CI rather than bare `nos test` / `cljr -X:test`. Each task regenerates the file before it runs the tests.

### JVM testing setup

#### `rc_test.clj`, the RCT runner

Your lib might target the JVM as well as the CLR. You can still run the RCT tests the way you are used to.

The common way is to create a test file:

```clojure
(ns my-project.rc-test
  (:require [clojure.test :refer [deftest testing]]
            [com.mjdowney.rich-comment-tests.test-runner :as test-runner]))

(deftest ^:rct rich-comment-tests
  (testing "Rich comment tests."
    (test-runner/run-tests-in-file-tree! :dirs #{"src"})))
```

#### `tests.edn`

You might use Kaocha as the JVM test runner for all your `deftest`s, including the one above that gathers every RCT block into one.

Split the suites into `:rct` and `:unit` so you can run them independently:

```clojure
#kaocha/v1
 {:tests [{:id :rct
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

### CI

Add the generated file to your `.gitignore`.

If your CI caches untracked files (e.g. GitLab CI `cache: untracked: true`), delete it before format checks. A copy cached from an earlier run fails the check:

```bash
rm -f test-clr/my_project/rct_generated_test.cljc
```

## Generated test structure

The generated file contains:

- A namespace with `^:clr-only` metadata, which JVM test runners filtering on it skip
- Three helpers. The generated file requires only `clojure.test` and `matcho.core`, so it carries its own: `error->map` builds the map a `throws=>>` pattern matches against, `run-form!` evaluates one form and reports a throw against its own line, and `bind-repl-vars!` carries each result into `*1`
- One `deftest` per source namespace, binding `*ns*` and the REPL vars, with `clojure.test/is` for `=>`, `matcho.core/assert` for `=>>`, and `try`/`catch` plus matcho for `throws=>>`
- The generator emits a form with no assertion (`def`, `require`) for its side effect

Example output (abbreviated):

```clojure
(ns ^:clr-only my-project.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [my-project.core]))

;; error->map, run-form! and bind-repl-vars! are defined here

;; my-project.core
(defn- my-project-core-rct-block-0 []
  ;; core.cljc:42
  (testing "core.cljc:42"
    (my-project.rct-generated-test/run-form! "core.cljc:42" (quote (clojure.test/is (= 4 (my-project.rct-generated-test/bind-repl-vars! (+ 2 2)))))))
  ;; core.cljc:45
  (testing "core.cljc:45"
    (my-project.rct-generated-test/run-form! "core.cljc:45" (quote (matcho.core/assert {:status 200} (my-project.rct-generated-test/bind-repl-vars! (fetch)))))))

(deftest my-project-core-rct
  (binding [*ns* (the-ns 'my-project.core)
            *1 nil, *2 nil, *3 nil, *e nil]
    (my-project-core-rct-block-0)))
```

## Contributing

- [CONTRIBUTING.md](CONTRIBUTING.md) - issues, pull requests, the local gate, commits
- [CHANGELOG.md](CHANGELOG.md) - what changed, per release
