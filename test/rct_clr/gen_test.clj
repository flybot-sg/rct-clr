(ns rct-clr.gen-test
  "Integration tests for rct-clr.gen writer functions and file discovery."
  (:require [clojure.test :as test :refer [deftest is testing]]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [rct-clr.gen :as gen])
  (:import [java.io File PushbackReader StringReader]))

(defn- read-all-forms
  "Read all top-level Clojure forms from s. Supports #? reader conditionals."
  [s]
  (let [rdr (PushbackReader. (StringReader. s))]
    (loop [forms []]
      (let [form (read {:read-cond :allow :features #{:cljr} :eof ::eof} rdr)]
        (if (= ::eof form)
          forms
          (recur (conj forms form)))))))

(defn- assert-parseable
  "Assert s is valid Clojure — all forms readable, at least one present."
  [s]
  (let [forms (read-all-forms s)]
    (is (pos? (count forms)) "expected at least one readable form")
    forms))

(defn- write-block-output
  "Call write-block-fn and return the output string."
  ([block-data] (write-block-output block-data "example.cljc"))
  ([block-data file-name]
   (let [sw (java.io.StringWriter.)
         f (io/file file-name)
         fn-sym (symbol (str (string/replace file-name #"\.\w+$" "") "-block-0"))]
     (gen/write-block-fn sw fn-sym block-data 'rct-clr.gen f 'test.output)
     (str sw))))

(defn- delete-tree
  "Recursively delete a directory and all contents."
  [dir]
  (doseq [f (reverse (file-seq (io/file dir)))]
    (.delete f)))

(defmacro ^:private with-tmp-dir
  "Create a temp directory, populate with files, execute body, clean up.
  files is a map of {relative-path content-string}."
  [[sym files] & body]
  `(let [~sym (io/file (System/getProperty "java.io.tmpdir")
                       (str "rct-clr-" (System/nanoTime)))]
     (try
       (.mkdirs ~sym)
       (doseq [[path# content#] ~files]
         (let [f# (io/file ~sym path#)]
           (.mkdirs (.getParentFile f#))
           (spit f# content#)))
       ~@body
       (finally
         (delete-tree ~sym)))))

(defn- rct-source
  "Minimal .cljc source with one function and one ^:rct/test block."
  [ns-sym fn-name return-val]
  (str "(ns " ns-sym ")\n"
       "(defn " fn-name " [] " return-val ")\n"
       "^:rct/test\n"
       "(comment\n"
       "  (" fn-name ")\n"
       "  ;=> " return-val "\n"
       "  )\n"))

(defn- with-extra-classpath*
  "Execute f with dirs added to the classpath via a DynamicClassLoader
  bound to Compiler/LOADER (which RT/baseLoader checks first)."
  [dirs f]
  (let [cl (clojure.lang.DynamicClassLoader.
            (.getContextClassLoader (Thread/currentThread)))]
    (doseq [dir dirs]
      (.addURL cl (.toURL (.toURI (io/file dir)))))
    (push-thread-bindings {clojure.lang.Compiler/LOADER cl})
    (try (f)
         (finally (pop-thread-bindings)))))

(defmacro ^:private with-gen-project
  "Set up a temp project with source files and classpath for generate tests.
  Binds sym to {:src-dirs [abs-paths] :output abs-path}.
  Cleans up temp files and removes namespaces listed in :remove-nses."
  [[sym {:keys [files src-dirs remove-nses]}] & body]
  `(with-tmp-dir [dir# ~files]
     (let [src-dir-strs# (mapv #(str (io/file dir# %)) ~src-dirs)
           output-str# (str (io/file dir# "out.cljc"))]
       (with-extra-classpath* src-dir-strs#
         (fn []
           (let [~sym {:src-dirs src-dir-strs# :output output-str#}]
             (try
               ~@body
               (finally
                 (doseq [ns-sym# ~remove-nses]
                   (remove-ns ns-sym#))))))))))

;; ---------------------------------------------------------------------------
;; write-preamble
;; ---------------------------------------------------------------------------

(deftest write-preamble-empty-ns-syms-test
  (let [sw (java.io.StringWriter.)
        expected (str "(ns ^:clr-only my.test\n"
                      "  \"Auto-generated from ^:rct/test blocks. Do not edit manually.\"\n"
                      "  (:require [clojure.test :refer [deftest testing]]\n"
                      "            [matcho.core]\n"
                      "))\n"
                      "\n"
                      "(defn error->map [e]\n"
                      "  {:error/class (type e)\n"
                      "   :error/message #?(:clj (.getMessage e) :cljr (.Message e))\n"
                      "   :error/data (ex-data e)})\n\n")]
    (gen/write-preamble sw 'my.test [])
    (is (= expected (str sw)))))

;; ---------------------------------------------------------------------------
;; write-deftest
;; ---------------------------------------------------------------------------

(deftest write-deftest-empty-blocks-test
  (let [sw (java.io.StringWriter.)
        expected (str "(deftest some-ns-rct\n"
                      "  (binding [*ns* (the-ns 'some.ns)]))\n\n")]
    (gen/write-deftest sw 'some.ns [])
    (is (= expected (str sw)))))

;; ---------------------------------------------------------------------------
;; write-block-fn
;; ---------------------------------------------------------------------------

(deftest write-block-fn-test
  (testing "with location: assertion wrapped in testing, side-effect bare"
    (let [block-data [{:test-sexpr (list '+ 1 2)
                       :expectation-string "3"
                       :expectation-type '=>
                       :location [10 1]}
                      {:test-sexpr (list 'def 'x 1)
                       :expectation-type nil
                       :location [12 1]}]
          out (write-block-output block-data)
          expected (str "(defn- example-block-0 []\n"
                        "  ;; example.cljc:10\n"
                        "  (testing \"example.cljc:10\" (eval (quote (clojure.test/is (= 3 (+ 1 2))))))\n"
                        "  ;; example.cljc:12\n"
                        "  (eval (quote (def x 1))))\n")]
      (is (= expected out))))
  (testing "no location: bare eval, no testing wrapper"
    (let [block-data [{:test-sexpr (list '+ 1 2)
                       :expectation-string "3"
                       :expectation-type '=>}]
          out (write-block-output block-data)
          expected (str "(defn- example-block-0 []\n"
                        "  (eval (quote (clojure.test/is (= 3 (+ 1 2))))))\n")]
      (is (= expected out)))))

(deftest write-block-fn-matcho-test
  (testing "with location: matcho assert wrapped in testing"
    (let [block-data [{:test-sexpr (list 'get-status)
                       :expectation-string "{:status 200}"
                       :expectation-type '=>>
                       :location [5 1]}]
          out (write-block-output block-data "api.cljc")
          expected (str "(defn- api-block-0 []\n"
                        "  ;; api.cljc:5\n"
                        "  (testing \"api.cljc:5\" (eval (quote (matcho.core/assert {:status 200} (get-status))))))\n")]
      (is (= expected out))))
  (testing "no location: bare eval"
    (let [block-data [{:test-sexpr (list 'get-status)
                       :expectation-string "{:status 200}"
                       :expectation-type '=>>}]
          out (write-block-output block-data "api.cljc")
          expected (str "(defn- api-block-0 []\n"
                        "  (eval (quote (matcho.core/assert {:status 200} (get-status)))))\n")]
      (is (= expected out)))))

(deftest write-block-fn-throws-test
  (testing "with location: try/catch wrapped in testing"
    (let [block-data [{:test-sexpr (list 'kaboom)
                       :expectation-string "{:error/class Exception}"
                       :expectation-type 'throws=>>
                       :location [20 1]}]
          out (write-block-output block-data "err.cljc")
          expected (str "(defn- err-block-0 []\n"
                        "  ;; err.cljc:20\n"
                        "  (testing \"err.cljc:20\" (eval (quote (try (kaboom) (clojure.test/is false \"Expected exception\") (catch Exception e (matcho.core/assert #:error{:class Exception} (test.output/error->map e))))))))\n")]
      (is (= expected out))))
  (testing "no location: bare eval"
    (let [block-data [{:test-sexpr (list 'kaboom)
                       :expectation-string "{:error/class Exception}"
                       :expectation-type 'throws=>>}]
          out (write-block-output block-data "err.cljc")
          expected (str "(defn- err-block-0 []\n"
                        "  (eval (quote (try (kaboom) (clojure.test/is false \"Expected exception\") (catch Exception e (matcho.core/assert #:error{:class Exception} (test.output/error->map e)))))))\n")]
      (is (= expected out)))))

(deftest write-block-fn-empty-block-data-test
  (let [out (write-block-output [])
        expected "(defn- example-block-0 [])\n"]
    (is (= expected out))))

(deftest write-block-fn-metadata-preservation-test
  (let [block-data [{:test-sexpr (list 'get-items)
                     :expectation-string "^:matcho/strict [1 2 3]"
                     :expectation-type '=>>
                     :location [5 1]}]
        out (write-block-output block-data "meta.cljc")
        expected (str "(defn- meta-block-0 []\n"
                      "  ;; meta.cljc:5\n"
                      "  (testing \"meta.cljc:5\" (eval (quote (matcho.core/assert ^#:matcho{:strict true} [1 2 3] (get-items))))))\n")]
    (is (= expected out))))

;; ---------------------------------------------------------------------------
;; find-cljc-files
;; ---------------------------------------------------------------------------

(deftest find-cljc-files-test
  (with-tmp-dir [dir {"foo.cljc" "(ns foo)"
                      "bar.clj"  "(ns bar)"
                      "baz.txt"  "hello"}]
    (testing "finds only .cljc files"
      (is (= ["foo.cljc"] (mapv #(.getName %) (gen/find-cljc-files (str dir))))))))

(deftest find-cljc-files-empty-dir-test
  (with-tmp-dir [dir {}]
    (is (empty? (gen/find-cljc-files (str dir))))))

(deftest find-cljc-files-nested-test
  (with-tmp-dir [dir {"top.cljc"        "(ns top)"
                      "sub/nested.cljc" "(ns sub.nested)"
                      "sub/ignored.clj" "(ns sub.ignored)"}]
    (let [names (mapv #(.getName %) (gen/find-cljc-files (str dir)))]
      (testing "finds .cljc in subdirectories, sorted"
        (is (= ["nested.cljc" "top.cljc"] names))))))

(deftest find-cljc-files-nonexistent-dir-test
  (is (empty? (gen/find-cljc-files "/tmp/rct-clr-does-not-exist-999"))))

;; ---------------------------------------------------------------------------
;; Self-referential integration: file->ns-sym, file->rct-blocks
;; ---------------------------------------------------------------------------

(deftest file->ns-sym-test
  (is (= 'rct-clr.gen
         (gen/file->ns-sym (io/file "src/rct_clr/gen.cljc")))))

(deftest file->rct-blocks-test
  (let [blocks (gen/file->rct-blocks (io/file "src/rct_clr/gen.cljc") 'rct-clr.gen)
        datums (mapcat identity blocks)
        valid-types #{nil '=> '=>> 'throws=>>}]
    (testing "finds at least one block"
      (is (pos? (count blocks))))
    (testing "every block is a non-empty seq"
      (is (every? seq blocks)))
    (testing "every datum has required keys with valid types"
      (is (every? #(and (contains? % :test-sexpr)
                        (some? (:test-sexpr %))
                        (contains? % :expectation-type)
                        (contains? % :location)
                        (contains? valid-types (:expectation-type %)))
                  datums)))))

;; ---------------------------------------------------------------------------
;; Golden file: generator output matches checked-in snapshot
;; If gen.cljc changes, regenerate with: bb gen-clr-rct
;; ---------------------------------------------------------------------------

(def ^:private golden-path
  "Checked-in snapshot of generated CLR test output."
  "test/rct_clr/rct_generated_test.cljc")
(def ^:private golden-ns
  "Namespace declared in the golden file."
  'rct-clr.rct-generated-test)

(deftest generated-output-matches-golden-file-test
  (let [tmp (File/createTempFile "rct-golden" ".cljc")]
    (try
      (gen/generate {:src-dirs ["src"] :output (str tmp) :namespace (str golden-ns)})
      (is (= (slurp golden-path) (slurp tmp))
          "generated output has changed — run bb gen-clr-rct to update the golden file")
      (finally
        (.delete tmp)))))

;; This works because the current RCT tests in src/rct_c/gen.cljc do not have
;; any CLR-specific code. But if they ever did need CLR-specific tests, then
;; we would need to use a custom file for golden testing
(deftest golden-file-tests-pass-test
  (load-file golden-path)
  (try
    (let [result (test/run-tests golden-ns)]
      (is (pos? (:test result)) "at least one test ran")
      (is (= {:fail 0 :error 0} (select-keys result [:fail :error]))))
    (finally
      (remove-ns golden-ns))))

;; ---------------------------------------------------------------------------
;; generate
;; ---------------------------------------------------------------------------

(deftest generate-multi-src-dir-test
  (with-gen-project [project {:files {"src_a/ns_a/alpha.cljc" (rct-source 'ns-a.alpha 'foo 1)
                                      "src_b/ns_b/beta.cljc"  (rct-source 'ns-b.beta 'bar 2)}
                              :src-dirs ["src_a" "src_b"]
                              :remove-nses ['ns-a.alpha 'ns-b.beta
                                            'rct-clr.multi-src-generated]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "rct-clr.multi-src-generated"})
          content (slurp (:output project))]
      (testing "discovers namespaces and blocks from both source directories"
        (is (= {:namespaces 2 :blocks 2} result)))
      (testing "generated file references both namespaces"
        (assert-parseable content)
        (is (every? #(string/includes? content %)
                    ["ns-a.alpha" "ns-b.beta"]))))))

(deftest generate-ns-load-failure-test
  (with-gen-project [project {:files {"src/broken_ns/kaboom.cljc"
                                      (str "(ns broken-ns.kaboom\n"
                                           "  (:require [nonexistent.fake.library999]))\n"
                                           "^:rct/test\n"
                                           "(comment\n"
                                           "  1\n"
                                           "  ;=> 1\n"
                                           "  )\n")}
                              :src-dirs ["src"]
                              :remove-nses []}]
    (let [ex (try
               (gen/generate {:src-dirs (:src-dirs project)
                              :output (:output project)
                              :namespace "test.fail"})
               nil
               (catch clojure.lang.ExceptionInfo e e))]
      (testing "throws ex-info with descriptive message and file path"
        (is (string/includes? (ex-message ex) "Failed to load broken-ns.kaboom"))
        (is (string/includes? (:file (ex-data ex)) "broken_ns/kaboom.cljc")))
      (testing "no partial output file left behind"
        (is (not (.exists (io/file (:output project)))))))))

(deftest generate-empty-src-dirs-test
  (with-gen-project [project {:files {}
                              :src-dirs []
                              :remove-nses ['test.empty]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "test.empty"})
          content (slurp (:output project))]
      (testing "reports zero namespaces and blocks"
        (is (= {:namespaces 0 :blocks 0} result)))
      (testing "output is valid Clojure with no deftest forms"
        (assert-parseable content)
        (is (not (string/includes? content "(deftest ")))))))

(deftest generate-no-rct-blocks-test
  (with-gen-project [project {:files {"src/plain/module.cljc"
                                      (str "(ns plain.module)\n"
                                           "(defn foo [] 1)\n")}
                              :src-dirs ["src"]
                              :remove-nses ['plain.module 'test.norct]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "test.norct"})
          content (slurp (:output project))]
      (testing "reports zero namespaces and blocks"
        (is (= {:namespaces 0 :blocks 0} result)))
      (testing "output is valid Clojure with no deftest forms"
        (assert-parseable content)
        (is (not (string/includes? content "(deftest ")))))))

(deftest generate-skips-no-ns-file-test
  (with-gen-project [project {:files {"src/has_ns/ok.cljc" (rct-source 'has-ns.ok 'x 1)
                                      "src/no_ns.cljc"     "(defn stray [] :oops)"}
                              :src-dirs ["src"]
                              :remove-nses ['has-ns.ok 'test.nons]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "test.nons"})]
      (testing "skips the no-ns file, processes the good one"
        (is (= {:namespaces 1 :blocks 1} result))
        (is (not (string/includes? (slurp (:output project)) "stray")))))))
