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
  "Minimal .cljc source with ^:rct/test blocks exercising the given assertion types.
  assertion-types is a vector of :=>, :=>>, and/or :throws=>>.
  blocks controls how many ^:rct/test blocks to produce (default 1)."
  ([ns-sym assertion-types] (rct-source ns-sym assertion-types 1))
  ([ns-sym assertion-types blocks]
   (let [parts (map-indexed
                (fn [i atype]
                  (case atype
                    :=> {:defn (str "(defn fn-" i " [] " (inc i) ")")
                         :test (str "  (fn-" i ")\n  ;=> " (inc i))}
                    :=>> {:defn (str "(defn fn-" i " [] {:result " (inc i) " :extra 99})")
                          :test (str "  (fn-" i ")\n  ;=>> {:result " (inc i) "}")}
                    :throws=>> {:defn (str "(defn fn-" i " [] (throw (ex-info \"boom\" {:code 42})))")
                                :test (str "  (fn-" i ")\n  ;throws=>> {:error/class clojure.lang.ExceptionInfo}")}))
                assertion-types)
         block-groups (partition-all (max 1 (Math/ceil (/ (count parts) blocks))) parts)]
     (str "(ns " ns-sym ")\n"
          (string/join "\n" (map :defn parts)) "\n"
          (string/join "\n"
                       (map (fn [group]
                              (str "^:rct/test\n"
                                   "(comment\n"
                                   (string/join "\n" (map :test group)) "\n"
                                   "  )\n"))
                            block-groups))))))

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
  Automatically removes any namespaces loaded during body."
  [[sym {:keys [files src-dirs]}] & body]
  `(with-tmp-dir [dir# ~files]
     (let [src-dir-strs# (mapv #(str (io/file dir# %)) ~src-dirs)
           output-str# (str (io/file dir# "out.cljc"))]
       (with-extra-classpath* src-dir-strs#
         (fn []
           (let [before# (set (map ns-name (all-ns)))
                 ~sym {:src-dirs src-dir-strs# :output output-str#}]
             (try
               ~@body
               (finally
                 (doseq [ns-obj# (all-ns)
                         :let [ns-sym# (ns-name ns-obj#)]
                         :when (not (contains? before# ns-sym#))]
                   (remove-ns ns-sym#))))))))))

;; ---------------------------------------------------------------------------
;; write-preamble
;; ---------------------------------------------------------------------------

(deftest write-preamble-empty-ns-syms-test
  (let [sw (java.io.StringWriter.)
        _ (gen/write-preamble sw 'my.test [])
        forms (read-all-forms (str sw))
        ns-form (first forms)
        ns-meta (meta (second ns-form))
        require-form (last ns-form)
        required-libs (set (map first (rest require-form)))]
    (testing "ns symbol and metadata"
      (is (= 'my.test (second ns-form)))
      (is (:clr-only ns-meta)))
    (testing "always requires clojure.test and matcho.core"
      (is (contains? required-libs 'clojure.test))
      (is (contains? required-libs 'matcho.core)))
    (testing "error->map helper present"
      (is (some #(and (seq? %) (= 'defn (first %)) (= 'error->map (second %)))
                forms)))))

(deftest write-preamble-with-ns-syms-test
  (let [sw (java.io.StringWriter.)
        _ (gen/write-preamble sw 'my.test ['zz.last 'aa.first 'mm.middle])
        forms (read-all-forms (str sw))
        ns-form (first forms)
        require-form (last ns-form)
        required-libs (mapv first (rest require-form))]
    (testing "source namespaces appear sorted in requires"
      (let [source-libs (filterv #{'aa.first 'mm.middle 'zz.last} required-libs)]
        (is (= ['aa.first 'mm.middle 'zz.last] source-libs))))
    (testing "always includes clojure.test and matcho.core"
      (let [lib-set (set required-libs)]
        (is (contains? lib-set 'clojure.test))
        (is (contains? lib-set 'matcho.core))))))

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

(deftest write-block-fn-wrapping-test
  (let [block-data [;; side-effect with location
                    {:test-sexpr '(def x 1)
                     :expectation-type nil
                     :location [5 1]}
                    ;; side-effect without location
                    {:test-sexpr '(def y 2)
                     :expectation-type nil}
                    ;; => with location
                    {:test-sexpr '(+ 1 2)
                     :expectation-string "3"
                     :expectation-type '=>
                     :location [9 1]}
                    ;; =>> with location
                    {:test-sexpr '(get-status)
                     :expectation-string "{:status 200}"
                     :expectation-type '=>>
                     :location [11 1]}
                    ;; throws=>> with location
                    {:test-sexpr '(kaboom)
                     :expectation-string "{:error/class Exception}"
                     :expectation-type 'throws=>>
                     :location [13 1]}
                    ;; assertion without location
                    {:test-sexpr '(+ 3 4)
                     :expectation-string "7"
                     :expectation-type '=>}]
        out (write-block-output block-data)
        [defn-form :as forms] (read-all-forms out)
        body (vec (drop 3 defn-form))]
    (testing "single defn- form with all six body forms"
      (is (= 1 (count forms)))
      (is (= 'defn- (first defn-form)))
      (is (= 6 (count body))))
    (testing "side-effect with location is bare eval, not testing"
      (is (= 'eval (first (nth body 0)))))
    (testing "side-effect without location is bare eval"
      (is (= 'eval (first (nth body 1)))))
    (testing "=> with location wrapped in testing"
      (is (= 'testing (first (nth body 2))))
      (is (= "example.cljc:9" (second (nth body 2)))))
    (testing "=>> with location wrapped in testing"
      (is (= 'testing (first (nth body 3)))))
    (testing "throws=>> with location wrapped in testing"
      (is (= 'testing (first (nth body 4)))))
    (testing "assertion without location is bare eval"
      (is (= 'eval (first (nth body 5)))))
    (testing "location comments for datums with locations"
      (is (string/includes? out ";; example.cljc:5"))
      (is (string/includes? out ";; example.cljc:9"))
      (is (string/includes? out ";; example.cljc:13")))
    (testing "no location comments for datums without locations"
      (is (not (re-find #";;.*def y" out)))
      (is (not (re-find #";;.*\+ 3 4" out))))))

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

(deftest file->ns-sym-no-ns-test
  (with-tmp-dir [dir {"no_ns.cljc" "(defn stray [] :oops)"}]
    (is (nil? (gen/file->ns-sym (io/file dir "no_ns.cljc"))))))

(deftest file->rct-blocks-test
  (let [blocks (gen/file->rct-blocks (io/file "src/rct_clr/gen.cljc") 'rct-clr.gen)
        datums (mapcat identity blocks)
        valid-types #{nil '=> '=>> 'throws=>>}]
    (testing "finds all RCT blocks"
      (is (= 6 (count blocks))))
    (testing "every block is a non-empty seq"
      (is (every? seq blocks)))
    (testing "first block is build-resolver with exact test-sexpr"
      (let [datum (first (first blocks))]
        (is (= '(build-resolver (quote rct-clr.gen)) (:test-sexpr datum)))
        (is (= '=> (:expectation-type datum)))))
    (testing "every datum has required keys with valid types"
      (is (every? #(and (contains? % :test-sexpr)
                        (some? (:test-sexpr %))
                        (contains? % :expectation-type)
                        (contains? % :location)
                        (contains? valid-types (:expectation-type %)))
                  datums)))))

;; ---------------------------------------------------------------------------
;; Golden file snapshots
;;
;; These tests verify that the checked-in golden files match what the generator
;; produces. bb jvm-test regenerates the golden files before running tests, so these
;; catch the case where someone regenerates locally but forgets to commit the
;; updated golden files.
;;
;; Two golden files:
;; - rct_generated_test.cljc (from src/): cannot run on CLR because it
;;   pulls in rct-clr.gen which has JVM-only deps.
;; - sample_generated_test.cljc (from examples/ + examples_clr/): CLR-runnable,
;;   no JVM-only deps. This is what runs on Magic/Nostrand.
;; ---------------------------------------------------------------------------

(def ^:private golden-files
  "Golden file configs: [src-dirs output-path namespace]."
  [[["src"] "test/rct_clr/rct_generated_test.cljc"
    "rct-clr.rct-generated-test"]
   [["examples" "examples_clr"] "test/rct_clr/sample_generated_test.cljc"
    "rct-clr.sample-generated-test"]])

(deftest generated-output-matches-golden-files-test
  (doseq [[src-dirs golden-path ns-str] golden-files]
    (testing (str golden-path " matches generated output")
      (let [tmp (File/createTempFile "rct-golden" ".cljc")]
        (try
          (gen/generate {:src-dirs src-dirs :output (str tmp) :namespace ns-str})
          (is (= (slurp golden-path) (slurp tmp))
              "generated output has changed — run bb gen-clr-rct to update golden files")
          (finally
            (.delete tmp)))))))

;; ---------------------------------------------------------------------------
;; generate
;; ---------------------------------------------------------------------------

(deftest generate-multi-src-dir-test
  (with-gen-project [project {:files {"src_a/ns_a/alpha.cljc" (rct-source 'ns-a.alpha [:=>])
                                      "src_b/ns_b/beta.cljc"  (rct-source 'ns-b.beta [:=>])}
                              :src-dirs ["src_a" "src_b"]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "rct-clr.multi-src-generated"})
          content (slurp (:output project))
          forms (read-all-forms content)
          all-syms (set (filter symbol? (tree-seq coll? seq forms)))]
      (testing "discovers namespaces and blocks from both source directories"
        (is (= {:namespaces 2 :blocks 2} result)))
      (testing "generated file requires both source namespaces"
        (is (contains? all-syms 'ns-a.alpha))
        (is (contains? all-syms 'ns-b.beta)))
      (testing "generated tests pass when loaded and run"
        (load-file (:output project))
        (let [test-result (test/run-tests 'rct-clr.multi-src-generated)]
          (is (= 2 (:test test-result)))
          (is (= {:fail 0 :error 0}
                 (select-keys test-result [:fail :error]))))))))

(deftest generate-ns-load-failure-test
  (with-gen-project [project {:files {"src/broken_ns/kaboom.cljc"
                                      (str "(ns broken-ns.kaboom\n"
                                           "  (:require [nonexistent.fake.library999]))\n"
                                           "^:rct/test\n"
                                           "(comment\n"
                                           "  1\n"
                                           "  ;=> 1\n"
                                           "  )\n")}
                              :src-dirs ["src"]}]
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

(deftest generate-no-rct-output-test
  (testing "empty src-dirs"
    (with-gen-project [project {:files {}
                                :src-dirs []}]
      (let [result (gen/generate {:src-dirs (:src-dirs project)
                                  :output (:output project)
                                  :namespace "test.empty"})
            content (slurp (:output project))]
        (is (= {:namespaces 0 :blocks 0} result))
        (assert-parseable content)
        (is (not (string/includes? content "(deftest "))))))
  (testing "files without ^:rct/test blocks"
    (with-gen-project [project {:files {"src/plain/module.cljc"
                                        (str "(ns plain.module)\n"
                                             "(defn foo [] 1)\n")}
                                :src-dirs ["src"]}]
      (let [result (gen/generate {:src-dirs (:src-dirs project)
                                  :output (:output project)
                                  :namespace "test.norct"})
            content (slurp (:output project))]
        (is (= {:namespaces 0 :blocks 0} result))
        (assert-parseable content)
        (is (not (string/includes? content "(deftest ")))))))

(deftest generate-skips-no-ns-file-test
  (with-gen-project [project {:files {"src/has_ns/ok.cljc" (rct-source 'has-ns.ok [:=>])
                                      "src/no_ns.cljc"     "(defn stray [] :oops)"}
                              :src-dirs ["src"]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "test.nons"})]
      (testing "skips the no-ns file, processes the good one"
        (is (= {:namespaces 1 :blocks 1} result))
        (is (not (string/includes? (slurp (:output project)) "stray")))))))

(deftest generate-throws-assertion-test
  (with-gen-project [project {:files {"src/throws_ns/err.cljc"
                                      (rct-source 'throws-ns.err [:throws=>>])}
                              :src-dirs ["src"]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "test.throws"})
          content (slurp (:output project))
          forms (read-all-forms content)]
      (testing "discovers the namespace and block"
        (is (= {:namespaces 1 :blocks 1} result)))
      (testing "generated output contains try/catch structure"
        (let [all-syms (set (filter symbol? (tree-seq coll? seq forms)))]
          (is (contains? all-syms 'try))
          (is (contains? all-syms 'catch))
          (is (contains? all-syms 'matcho.core/assert)))))))

(deftest generate-multi-block-per-file-test
  (with-gen-project [project {:files {"src/mb/multi.cljc" (rct-source 'mb.multi [:=> :=>>] 2)}
                              :src-dirs ["src"]}]
    (let [result (gen/generate {:src-dirs (:src-dirs project)
                                :output (:output project)
                                :namespace "test.multi-block"})
          content (slurp (:output project))
          forms (read-all-forms content)]
      (testing "reports one namespace with two blocks"
        (is (= {:namespaces 1 :blocks 2} result)))
      (testing "generates two block-fns and one deftest"
        (let [defn-forms (filter #(and (seq? %) (= 'defn- (first %))) forms)
              deftest-forms (filter #(and (seq? %) (= 'deftest (first %))) forms)]
          (is (= 2 (count defn-forms)))
          (is (= 1 (count deftest-forms)))))
      (testing "generated tests pass when loaded and run"
        (load-file (:output project))
        (let [test-result (test/run-tests 'test.multi-block)]
          (is (= 1 (:test test-result)))
          (is (= {:fail 0 :error 0}
                 (select-keys test-result [:fail :error]))))))))
