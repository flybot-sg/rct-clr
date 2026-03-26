# rct-clr

Generates CLR-compatible test files from [Rich Comment Tests](https://github.com/parth-io/rich-comment-tests) (`^:rct/test`) blocks.

## How it works

RCT depends on rewrite-clj and tools.namespace, which are JVM-only. This tool pre-extracts RCT test data into a plain `.cljc` test file that CLR ([Magic](https://github.com/nasser/magic)/[Nostrand](https://github.com/nasser/nostrand)) can run using only `clojure.test` and `matcho.core`.

1. **Extract (JVM):** Run `rct-clr.gen` on the JVM, where rewrite-clj and tools.namespace are available. It scans `.cljc` source files, loads each namespace, finds all `^:rct/test` comment blocks, and writes the assertions into a plain `.cljc` test file. (`.clj` files are ignored.)
2. **Test (CLR):** Run the generated file on Magic/Nostrand using `clojure.test`. No JVM-only dependencies are needed at test time.

`rct-clr` itself is a JVM-only tool and runs on the JVM to generate test files.

## Prerequisites

- JVM Clojure (for running the generator)
- [Magic](https://github.com/nasser/magic)/[Nostrand](https://github.com/nasser/nostrand) on the target CLR platform (for running generated tests)

## Usage

```bash
clojure -M -m rct-clr.gen \
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

### deps.edn

Add as a dev dependency:

```clojure
{:aliases
 {:dev {:extra-deps {io.github.flybot-sg/rct-clr
                     {:git/url "https://github.com/flybot-sg/rct-clr"
                      :git/sha "..."}}}}}
```

Since `rct-clr` transitively brings in `rich-comment-tests`, you can remove any existing direct RCT dependency from your `deps.edn`.

### project.edn

Nostrand does not resolve transitive dependencies. Add [matcho](https://github.com/flybot-sg/matcho/tree/magic) directly to your `project.edn` dependencies, since the generated tests use `matcho.core/assert` for `=>>` patterns:

```clojure
{:dependencies [[:github flybot-sg/matcho "magic"
                 :sha "1edae156dda891b2f1698afc4972f5456f49d039"
                 :paths ["src"]]]}
```

### `bb.edn`

If you use Babashka to run scripts, you can do this too:

```clojure
{:tasks {gen-clr-rct
         {:doc  "Generate CLR-compatible RCT test file"
          :task (clojure "-M:dev -m rct-clr.gen -o test/my_project/rct_generated_test.cljc -n my-project.rct-generated-test")}}}
```

### tests.edn - JVM test runner (Kaocha)

The generated file has `^:clr-only` metadata on its namespace. Add this to your `tests.edn` so Kaocha skips it on JVM:

```clojure
:kaocha.filter/skip-meta [:clr-only]
```

### dotnet.clj

Add the generated test namespace to your `test-namespaces`. Also exit non-zero on test failures — `clojure.test/run-all-tests` returns a result map but doesn't set the exit code, so without this Nostrand exits 0 even when tests fail:

```clojure
(let [{:keys [fail error]} (run-all-tests)]
  (when (or (pos? fail) (pos? error))
    (Environment/Exit 1)))
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
