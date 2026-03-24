(ns rct-clr.gen
  "Generates CLR-compatible test files from ^:rct/test blocks.
  Run via: clojure -M -m rct-clr.gen --help"
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.cli :as cli]
            [clojure.tools.namespace.file :as ns-file]
            [clojure.tools.namespace.parse :as ns-parse]
            [com.mjdowney.rich-comment-tests :as rct]
            [com.mjdowney.rich-comment-tests.emit-tests :as emit]
            [clojure.tools.reader :as tr]
            [rewrite-clj.zip :as z]))

(defn find-cljc-files
  "Find all .cljc files in dir. Ignores .clj — CLR can only run .cljc."
  [dir]
  (->> (io/file dir)
       file-seq
       (filter #(and (.isFile %) (string/ends-with? (.getName %) ".cljc")))
       sort))

(defn file->ns-sym
  "Get namespace symbol from a source file."
  [file]
  (ns-parse/name-from-ns-decl
   (ns-file/read-file-ns-decl file)))

(defn build-resolver
  "Build a rewrite-clj auto-resolve map for a loaded namespace."
  [ns-sym]
  (let [the-ns (find-ns ns-sym)
        aliases (ns-aliases the-ns)]
    (into {:current ns-sym}
          (map (fn [[alias-sym ns-obj]]
                 [(keyword alias-sym) (ns-name ns-obj)]))
          aliases)))

(defn file->rct-blocks
  "Extract RCT test data from a file, grouped by ^:rct/test block.
  Namespace must already be loaded.
  Returns a seq of seqs (one per comment block)."
  [file ns-sym]
  (let [resolver (build-resolver ns-sym)
        zloc (z/of-file file {:track-position? true
                              :auto-resolve resolver})]
    (->> (rct/rct-zlocs zloc)
         (map rct/rct-data-seq)
         (filter seq))))

(defn read-expectation
  "Read expectation string into a form, handling ellipses for =>>."
  [{:keys [expectation-string expectation-type]} ns-sym]
  (when expectation-string
    (let [s (if (= '=>> expectation-type)
              (emit/elide-ellipses-in-expectation-string expectation-string)
              expectation-string)]
      ;; Bind *ns* so :: keywords resolve to the source namespace
      ;; Use :read-cond :allow with :cljr so #? resolves to CLR branch.
      ;; Can't use :preserve — Magic's eval can't compile ReaderConditional constants.
      (binding [*ns* (the-ns ns-sym)]
        (tr/read-string {:read-cond :allow :features #{:cljr}} s)))))

(defn datum->form
  "Convert an RCT datum to a Clojure form for the generated test."
  [{:keys [test-sexpr expectation-type] :as datum} ns-sym output-ns]
  (let [error->map-sym (symbol (str output-ns) "error->map")]
    (case expectation-type
      ;; nil = side-effect form (def, require), emit raw
      nil test-sexpr
      => (list 'clojure.test/is
               (list '= (read-expectation datum ns-sym) test-sexpr))
      =>> (list 'matcho.core/assert
                (read-expectation datum ns-sym) test-sexpr)
      throws=>> (list 'try test-sexpr
                      (list 'clojure.test/is false "Expected exception")
                      (list 'catch 'Exception 'e
                            (list 'matcho.core/assert
                                  (read-expectation datum ns-sym)
                                  (list error->map-sym 'e)))))))

(defn ns-sym->test-base
  "Convert namespace symbol to base name for tests."
  [ns-sym]
  (string/replace (str ns-sym) "." "-"))

(defn write-block-fn
  "Write a defn- for a single ^:rct/test block, one eval form per line.
  Each form is wrapped in (eval (quote ...)) so it resolves in the source
  namespace at test time, not the generated test namespace."
  [w fn-sym block-data ns-sym file output-ns]
  (let [file-name (.getName file)]
    (.write w (str "(defn- " fn-sym " []\n"))
    ;; prn, not pprint — pprint drops metadata (e.g. ^:matcho/strict)
    (binding [*out* w
              *print-meta* true]
      (doseq [datum block-data]
        (let [line (some-> datum :location first)
              loc (when line (str file-name ":" line))]
          (when loc
            (.write w (str "  ;; " loc "\n")))
          (.write w "  ")
          (let [eval-form (list 'eval (list 'quote (datum->form datum ns-sym output-ns)))]
            (if (and (:expectation-type datum) loc)
              (prn (list 'testing loc eval-form))
              (prn eval-form))))))
    (.write w ")\n")))

(defn write-deftest
  "Write a deftest that calls block fns in order.
  Binds *ns* to the source namespace so eval'd forms resolve there."
  [w ns-sym block-infos]
  (let [test-sym (str (ns-sym->test-base ns-sym) "-rct")]
    (.write w (str "(deftest " test-sym "\n"))
    (.write w (str "  (binding [*ns* (the-ns '" ns-sym ")]\n"))
    (doseq [{:keys [fn-sym]} block-infos]
      (.write w (str "    (" fn-sym ")\n")))
    (.write w "))\n\n")))

(def ^:private error->map-str
  "error->map replaces RCT's error-datafy which uses ex-message
  (Clojure 1.11+, unavailable on Magic 1.10). Eval'd throws=>> forms
  reference it fully-qualified so it resolves regardless of *ns* binding.
  Raw string because #? reader conditionals can't be represented as
  Clojure data for prn to print."
  "(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})")

(defn write-preamble
  "Write the ns form and error->map helper.
  The ns is tagged ^:clr-only so Kaocha skips it on the JVM."
  [w output-ns ns-syms]
  (let [requires (sort ns-syms)
        req-lines (map #(str "            [" % "]") requires)
        docstring "Auto-generated from ^:rct/test blocks. Do not edit manually."]
    (.write w (str "(ns ^:clr-only " output-ns "\n"
                   "  \"" docstring "\"\n"
                   "  (:require [clojure.test :refer [deftest is testing]]\n"
                   "            [matcho.core]\n"
                   (string/join "\n" req-lines) "))\n"
                   "\n"
                   error->map-str "\n\n"))))

(def cli-options
  [["-s" "--src-dir DIR" "Source directory to scan (repeatable, default: src)"
    :multi true
    :default []
    :update-fn conj]
   ["-o" "--output PATH" "Output file path (required)"]
   ["-n" "--namespace NS" "Output namespace (required)"]
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options errors summary]} (cli/parse-opts args cli-options)
        {:keys [src-dir output namespace help]} options
        src-dirs (if (empty? src-dir) ["src"] src-dir)
        missing (cond-> []
                  (nil? output) (conj "Must provide --output / -o")
                  (nil? namespace) (conj "Must provide --namespace / -n"))
        errors (into (vec errors) missing)]
    (when help
      (println "Usage: clojure -M -m rct-clr.gen [options]\n")
      (println summary)
      (System/exit 0))
    (when (seq errors)
      (doseq [e errors] (println e))
      (System/exit 1))
    (let [output-ns (symbol namespace)
          files (mapcat find-cljc-files src-dirs)
          file-blocks (for [f files
                            :let [ns-sym (file->ns-sym f)
                                  _ (try (require ns-sym)
                                         (catch Exception e
                                           (throw (ex-info (str "Failed to load " ns-sym)
                                                           {:file (str f)} e))))
                                  blocks (seq (file->rct-blocks f ns-sym))]
                            :when blocks]
                        {:file f :ns-sym ns-sym :blocks (vec blocks)})
          file-blocks (sort-by :ns-sym file-blocks)
          ns-syms (map :ns-sym file-blocks)
          n-blocks (reduce + (map (comp count :blocks) file-blocks))]
      (println "Found" (count file-blocks) "namespaces,"
               n-blocks "test blocks")
      (io/make-parents output)
      (with-open [w (io/writer output)]
        (write-preamble w output-ns ns-syms)
        (doseq [{:keys [file ns-sym blocks]} file-blocks]
          (.write w (str ";; " ns-sym "\n"))
          (let [block-infos
                (mapv (fn [[i block]]
                        (let [fn-sym (symbol (str (ns-sym->test-base ns-sym)
                                                  "-rct-block-" i))]
                          (write-block-fn w fn-sym block ns-sym file output-ns)
                          {:fn-sym fn-sym}))
                      (map-indexed vector blocks))]
            (write-deftest w ns-sym block-infos))))
      (println "Generated" output))))
