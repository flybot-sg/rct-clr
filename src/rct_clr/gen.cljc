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
            [clojure.walk :as walk]
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

^:rct/test
(comment
  (build-resolver 'rct-clr.gen)
  ;=>
  {:current 'rct-clr.gen
   :cli 'clojure.tools.cli
   :emit 'com.mjdowney.rich-comment-tests.emit-tests
   :io 'clojure.java.io
   :ns-file 'clojure.tools.namespace.file
   :ns-parse 'clojure.tools.namespace.parse
   :rct 'com.mjdowney.rich-comment-tests
   :string 'clojure.string
   :tr 'clojure.tools.reader
   :walk 'clojure.walk
   :z 'rewrite-clj.zip}

  ;; namespace with no aliases returns just :current
  (def rct-test-bare-ns (create-ns (gensym "bare-ns-")))
  (let [result (build-resolver (ns-name rct-test-bare-ns))]
    (remove-ns (ns-name rct-test-bare-ns))
    result)
  ;=> {:current (ns-name rct-test-bare-ns)}
  )

(defn resolve-reader-conditionals
  "Post-process a form from z/sexpr to resolve reader conditionals for CLR.
  rewrite-clj wraps unresolved #? forms as (read-string \"#?(...)\") — this
  walks the tree and resolves them with :features #{:cljr}."
  [form ns-sym]
  (let [target-ns (the-ns ns-sym)]
    (walk/postwalk
     (fn [x]
       (if (and (list? x)
                (= 'read-string (first x))
                (string? (second x))
                (string/includes? (second x) "#?"))
         ;; Parse with :preserve to inspect branch keys — tools.reader throws
         ;; EOF when no branch matches (e.g. #?(:clj ...)) with #{:cljr},
         ;; so CLJ-only conditionals must be detected and dropped to nil.
         ;; Extract from #? position so :preserve doesn't hit data readers.
         (let [rc (tr/read-string {:read-cond :preserve}
                                  (subs (second x) (string/index-of (second x) "#?")))
               branch-keys (set (take-nth 2 (.form rc)))]
           (when (or (branch-keys :cljr) (branch-keys :default))
             (binding [*ns* target-ns]
               (tr/read-string {:read-cond :allow :features #{:cljr}} (second x)))))
         x))
     form)))

^:rct/test
(comment
  ;; resolves reader conditional to :cljr branch
  (resolve-reader-conditionals
   '(read-string "#?(:clj (.getMessage e) :cljr (.Message e))")
   'rct-clr.gen)
  ;=> '(.Message e)

  ;; resolves nested reader conditionals inside a larger form
  (resolve-reader-conditionals
   '(try (foo)
         (catch (read-string "#?(:clj Exception :cljr System.Exception)") e
           (read-string "#?(:clj (.getMessage e) :cljr (.Message e))")))
   'rct-clr.gen)
  ;=> '(try (foo)
  ;;        (catch System.Exception e
  ;;          (.Message e)))

  ;; resolves reader conditional nested inside a regular expression
  (resolve-reader-conditionals
   '(+ (/ pos-score visits)
       (read-string "#?(:clj (Math/sqrt visits) :cljr (Math/Sqrt visits))"))
   'rct-clr.gen)
  ;=> '(+ (/ pos-score visits)
  ;;       (Math/Sqrt visits))

  ;; passes through forms without reader conditionals unchanged
  (resolve-reader-conditionals '(+ 1 2) 'rct-clr.gen)
  ;=> '(+ 1 2)

  ;; resolves #? inside a tagged literal
  (resolve-reader-conditionals
   '(read-string "#inst #?(:clj \"2024\" :cljr \"2025\")")
   'rct-clr.gen)
  ;=> #inst "2025-01-01T00:00:00.000-00:00"

  ;; doesn't touch non-reader-conditional read-string calls
  (resolve-reader-conditionals '(read-string "[1 2 3]") 'rct-clr.gen)
  ;=> '(read-string "[1 2 3]")

  ;; CLJ-only conditional (no :cljr branch) resolves to nil
  (resolve-reader-conditionals
   '(read-string "#?(:clj :jvm-only)")
   'rct-clr.gen)
  ;=> nil

  ;; :default branch used when no :cljr branch present
  (resolve-reader-conditionals
   '(read-string "#?(:clj :jvm :default :fallback)")
   'rct-clr.gen)
  ;=> :fallback
  )

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
         (map (fn [block]
                (map #(update % :test-sexpr resolve-reader-conditionals ns-sym)
                     block)))
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

^:rct/test
(comment
  (read-expectation {:expectation-string "42"
                     :expectation-type '=>}
                    'rct-clr.gen)
  ;=> 42

  (read-expectation {:expectation-string "[1 2 ...]"
                     :expectation-type '=>>}
                    'rct-clr.gen)
  ;=> [1 2]

  (read-expectation {:expectation-string nil
                     :expectation-type '=>}
                    'rct-clr.gen)
  ;=> nil

  ;; reader conditional resolves to :cljr branch
  (read-expectation {:expectation-string "#?(:clj :jvm :cljr :clr)"
                     :expectation-type '=>}
                    'rct-clr.gen)
  ;=> :clr

  ;; namespace-qualified keywords resolve to source namespace
  (read-expectation {:expectation-string "::foo"
                     :expectation-type '=>}
                    'rct-clr.gen)
  ;=> :rct-clr.gen/foo

  ;; alias-qualified keywords resolve via namespace aliases
  (read-expectation {:expectation-string "::string/join"
                     :expectation-type '=>}
                    'rct-clr.gen)
  ;=> :clojure.string/join

  ;; => preserves ... symbol (only =>> elides it)
  (count (read-expectation {:expectation-string "[1 2 ...]"
                            :expectation-type '=>}
                           'rct-clr.gen))
  ;=> 3

  ;; malformed input throws
  (read-expectation {:expectation-string "[1 2"
                     :expectation-type '=>}
                    'rct-clr.gen)
  ;throws=>> {}
  )

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
                      (list 'catch 'System.Exception 'e
                            (list 'matcho.core/assert
                                  (read-expectation datum ns-sym)
                                  (list error->map-sym 'e)))))))

^:rct/test
(comment
  ;; nil expectation: side-effect, returns raw expression
  (datum->form {:test-sexpr '(def x 1)
                :expectation-type nil}
               'rct-clr.gen
               'test-output-ns)
  ;=> '(def x 1)

  ;; => expectation: equality assertion
  (datum->form {:test-sexpr '(+ 1 2)
                :expectation-string "3"
                :expectation-type '=>}
               'rct-clr.gen
               'test-output-ns)
  ;=> '(clojure.test/is (= 3 (+ 1 2)))

  ;; =>> expectation: matcho pattern
  (datum->form {:test-sexpr '(get-status)
                :expectation-string "{:status 200}"
                :expectation-type '=>>}
               'rct-clr.gen
               'test-output-ns)
  ;=> '(matcho.core/assert {:status 200} (get-status))

  ;; =>> with ellipsis elision
  (datum->form {:test-sexpr '(range 5)
                :expectation-string "[0 1 ...]"
                :expectation-type '=>>}
               'rct-clr.gen
               'test-output-ns)
  ;=> '(matcho.core/assert [0 1] (range 5))

  ;; throws=>>: full try/catch structure with qualified error->map
  (datum->form {:test-sexpr '(boom!)
                :expectation-string "{:error/class Exception}"
                :expectation-type 'throws=>>}
               'rct-clr.gen
               'test-output-ns)
  ;=>
  '(try (boom!)
        (clojure.test/is false "Expected exception")
        (catch System.Exception e
          (matcho.core/assert {:error/class Exception}
                              (test-output-ns/error->map e))))

  ;; => with reader conditional: read-expectation resolves to CLR branch
  (datum->form {:test-sexpr '(get-platform)
                :expectation-string "#?(:clj :jvm :cljr :clr)"
                :expectation-type '=>}
               'rct-clr.gen
               'test-output-ns)
  ;=> '(clojure.test/is (= :clr (get-platform)))

  ;; => with namespace-qualified keyword in expectation
  (datum->form {:test-sexpr '(get-type)
                :expectation-string "::foo"
                :expectation-type '=>}
               'rct-clr.gen
               'test-output-ns)
  ;=> '(clojure.test/is (= :rct-clr.gen/foo (get-type)))
  )

(defn ns-sym->test-base
  "Convert namespace symbol to base name for tests."
  [ns-sym]
  (string/replace (str ns-sym) "." "-"))

^:rct/test
(comment
  (ns-sym->test-base 'my.cool.namespace)
  ;=> "my-cool-namespace"

  (ns-sym->test-base 'single)
  ;=> "single"
  )

(defn write-block-fn
  "Write a defn- for a single ^:rct/test block, one eval form per line.
  Each form is wrapped in (eval (quote ...)) so it resolves in the source
  namespace at test time, not the generated test namespace."
  [w fn-sym block-data ns-sym file output-ns]
  (let [file-name (.getName file)
        body (java.io.StringWriter.)]
    ;; prn, not pprint — pprint drops metadata (e.g. ^:matcho/strict)
    (binding [*out* body
              *print-meta* true]
      (doseq [datum block-data]
        (let [line (some-> datum :location first)
              loc (when line (str file-name ":" line))]
          (when loc
            (.write body (str "  ;; " loc "\n")))
          (.write body "  ")
          (let [eval-form (list 'eval (list 'quote (datum->form datum ns-sym output-ns)))]
            (if (and (:expectation-type datum) loc)
              (prn (list 'testing loc eval-form))
              (prn eval-form))))))
    ;; Strip trailing newline so closing ) stays on same line (cljfmt)
    (let [body-str (str body)]
      (.write w (str "(defn- " fn-sym " []"))
      (when (seq body-str)
        (.write w "\n")
        (.write w (string/trimr body-str)))
      (.write w ")\n"))))

(defn write-deftest
  "Write a deftest that calls block fns in order.
  Binds *ns* to the source namespace so eval'd forms resolve there."
  [w ns-sym block-infos]
  (let [test-sym (str (ns-sym->test-base ns-sym) "-rct")
        calls (mapv #(str "(" (:fn-sym %) ")") block-infos)]
    (.write w (str "(deftest " test-sym "\n"))
    (if (empty? calls)
      (.write w (str "  (binding [*ns* (the-ns '" ns-sym ")]))\n\n"))
      (do
        (.write w (str "  (binding [*ns* (the-ns '" ns-sym ")]\n"))
        (.write w (str "    " (string/join "\n    " calls) "))\n\n"))))))

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
                   "  (:require [clojure.test :refer [deftest testing]]\n"
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

(defn validate-opts
  "Parse and validate CLI options. Returns a tagged map:
  {:ok {:src-dirs [...] :output \"...\" :namespace \"...\"}} on success,
  {:help summary-string} for -h, {:errors [\"msg\" ...]} on failure."
  [args]
  (let [{:keys [options errors summary]} (cli/parse-opts args cli-options)
        {:keys [src-dir output namespace help]} options
        src-dirs (if (empty? src-dir) ["src"] src-dir)
        missing (cond-> []
                  (nil? output) (conj "Must provide --output / -o")
                  (nil? namespace) (conj "Must provide --namespace / -n"))
        errors (into (vec errors) missing)]
    (cond
      help {:help summary}
      (seq errors) {:errors errors}
      :else {:ok {:src-dirs src-dirs :output output :namespace namespace}})))

^:rct/test
(comment
  (validate-opts ["-o" "out.cljc" "-n" "my.ns"])
  ;=> {:ok {:src-dirs ["src"] :output "out.cljc" :namespace "my.ns"}}

  ;; multiple -s flags
  (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"])
  ;=> {:ok {:src-dirs ["src1" "src2"] :output "out.cljc" :namespace "my.ns"}}

  ;; missing --output
  (validate-opts ["-n" "my.ns"])
  ;=> {:errors ["Must provide --output / -o"]}

  ;; missing --namespace
  (validate-opts ["-o" "out.cljc"])
  ;=> {:errors ["Must provide --namespace / -n"]}

  ;; missing both
  (validate-opts [])
  ;=> {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]}

  ;; unknown flag produces errors
  (contains? (validate-opts ["--bogus"]) :errors)
  ;=> true

  (contains? (validate-opts ["-h"]) :help)
  ;=> true
  )

(defn generate
  "Scan source directories for ^:rct/test blocks and write the generated
  test file. Returns {:namespaces n :blocks n} on success.
  Throws ex-info if a namespace fails to load."
  [{:keys [src-dirs output namespace]}]
  (let [output-ns (symbol namespace)
        files (mapcat find-cljc-files src-dirs)
        file-blocks (for [f files
                          :let [ns-sym (file->ns-sym f)]
                          :when (if ns-sym
                                  true
                                  (do (println "Warning: skipping" (str f)
                                               "— no ns form found")
                                      false))
                          :let [_ (try (require ns-sym)
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
    (println "Generated" output)
    {:namespaces (count file-blocks) :blocks n-blocks}))

(defn -main [& args]
  (let [result (validate-opts args)]
    (cond
      (:help result)
      (do (println "Usage: clojure -M -m rct-clr.gen [options]\n")
          (println (:help result))
          (System/exit 0))

      (:errors result)
      (do (doseq [e (:errors result)] (println e))
          (System/exit 1))

      :else
      (generate (:ok result)))))
