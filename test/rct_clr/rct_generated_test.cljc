(ns ^:clr-only rct-clr.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [rct-clr.gen]))

(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})

(defn run-form! [loc form]
  (try
    (eval form)
    (catch #?(:clj Exception :cljr System.Exception) e
      (clojure.test/do-report
       {:type :error
        :message (str "Got " (type e) " evaluating " loc)
        :expected nil
        :actual e}))))

(defn bind-repl-vars! [result]
  (set! *3 *2)
  (set! *2 *1)
  (set! *1 result)
  result)

;; rct-clr.gen
(defn- rct-clr-gen-rct-block-0 []
  ;; gen.clj:49
  (testing "gen.clj:49" (rct-clr.rct-generated-test/run-form! "gen.clj:49" (quote (clojure.test/is (= {(quote ns-parse) (quote clojure.tools.namespace.parse), (quote cli) (quote clojure.tools.cli), (quote string) (quote clojure.string), (quote walk) (quote clojure.walk), (quote tr) (quote clojure.tools.reader), :current (quote rct-clr.gen), (quote emit) (quote com.mjdowney.rich-comment-tests.emit-tests), (quote ns-file) (quote clojure.tools.namespace.file), (quote io) (quote clojure.java.io), (quote rct) (quote com.mjdowney.rich-comment-tests), (quote z) (quote rewrite-clj.zip)} (rct-clr.rct-generated-test/bind-repl-vars! (build-resolver (quote rct-clr.gen))))))))
  ;; gen.clj:64
  (rct-clr.rct-generated-test/run-form! "gen.clj:64" (quote (rct-clr.rct-generated-test/bind-repl-vars! (def rct-test-bare-ns (create-ns (gensym "bare-ns-"))))))
  ;; gen.clj:65
  (testing "gen.clj:65" (rct-clr.rct-generated-test/run-form! "gen.clj:65" (quote (clojure.test/is (= {:current (ns-name rct-test-bare-ns)} (rct-clr.rct-generated-test/bind-repl-vars! (let [result (build-resolver (ns-name rct-test-bare-ns))] (remove-ns (ns-name rct-test-bare-ns)) result))))))))
(defn- rct-clr-gen-rct-block-1 []
  ;; gen.clj:99
  (testing "gen.clj:99" (rct-clr.rct-generated-test/run-form! "gen.clj:99" (quote (clojure.test/is (= (quote (.Message e)) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (.Message e)) (quote rct-clr.gen))))))))
  ;; gen.clj:105
  (testing "gen.clj:105" (rct-clr.rct-generated-test/run-form! "gen.clj:105" (quote (clojure.test/is (= (quote (try (foo) (catch System.Exception e (.Message e)))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (try (foo) (catch System.Exception e (.Message e)))) (quote rct-clr.gen))))))))
  ;; gen.clj:116
  (testing "gen.clj:116" (rct-clr.rct-generated-test/run-form! "gen.clj:116" (quote (clojure.test/is (= (quote (+ 1 2)) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (+ 1 2)) (quote rct-clr.gen))))))))
  ;; gen.clj:119
  (testing "gen.clj:119" (rct-clr.rct-generated-test/run-form! "gen.clj:119" (quote (clojure.test/is (= #inst "2025-01-01T00:00:00.000-00:00" (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote #inst "2025-01-01T00:00:00.000-00:00") (quote rct-clr.gen))))))))
  ;; gen.clj:125
  (testing "gen.clj:125" (rct-clr.rct-generated-test/run-form! "gen.clj:125" (quote (clojure.test/is (= (quote (read-string "[1 2 3]")) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (read-string "[1 2 3]")) (quote rct-clr.gen))))))))
  ;; gen.clj:129
  (testing "gen.clj:129" (rct-clr.rct-generated-test/run-form! "gen.clj:129" (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote nil) (quote rct-clr.gen))))))))
  ;; gen.clj:135
  (testing "gen.clj:135" (rct-clr.rct-generated-test/run-form! "gen.clj:135" (quote (clojure.test/is (= :fallback (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote :fallback) (quote rct-clr.gen)))))))))
(defn- rct-clr-gen-rct-block-2 []
  ;; gen.clj:171
  (testing "gen.clj:171" (rct-clr.rct-generated-test/run-form! "gen.clj:171" (quote (clojure.test/is (= 42 (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string "42", :expectation-type (quote =>)} (quote rct-clr.gen))))))))
  ;; gen.clj:176
  (testing "gen.clj:176" (rct-clr.rct-generated-test/run-form! "gen.clj:176" (quote (clojure.test/is (= [1 2] (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string "[1 2 ...]", :expectation-type (quote =>>)} (quote rct-clr.gen))))))))
  ;; gen.clj:181
  (testing "gen.clj:181" (rct-clr.rct-generated-test/run-form! "gen.clj:181" (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string nil, :expectation-type (quote =>)} (quote rct-clr.gen))))))))
  ;; gen.clj:187
  (testing "gen.clj:187" (rct-clr.rct-generated-test/run-form! "gen.clj:187" (quote (clojure.test/is (= (quote (+ 1 2)) (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string "(+ 1 2)", :expectation-type (quote =>)} (quote rct-clr.gen))))))))
  ;; gen.clj:193
  (testing "gen.clj:193" (rct-clr.rct-generated-test/run-form! "gen.clj:193" (quote (clojure.test/is (= :clr (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string "#?(:clj :jvm :cljr :clr)", :expectation-type (quote =>)} (quote rct-clr.gen))))))))
  ;; gen.clj:199
  (testing "gen.clj:199" (rct-clr.rct-generated-test/run-form! "gen.clj:199" (quote (clojure.test/is (= :rct-clr.gen/foo (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string "::foo", :expectation-type (quote =>)} (quote rct-clr.gen))))))))
  ;; gen.clj:205
  (testing "gen.clj:205" (rct-clr.rct-generated-test/run-form! "gen.clj:205" (quote (clojure.test/is (= :clojure.string/join (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-string "::string/join", :expectation-type (quote =>)} (quote rct-clr.gen))))))))
  ;; gen.clj:211
  (testing "gen.clj:211" (rct-clr.rct-generated-test/run-form! "gen.clj:211" (quote (clojure.test/is (= 3 (rct-clr.rct-generated-test/bind-repl-vars! (count (read-expectation {:expectation-string "[1 2 ...]", :expectation-type (quote =>)} (quote rct-clr.gen)))))))))
  ;; gen.clj:217
  (testing "gen.clj:217" (rct-clr.rct-generated-test/run-form! "gen.clj:217" (quote (try (read-expectation {:expectation-string "[1 2", :expectation-type (quote =>)} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.clj:246
  (testing "gen.clj:246" (rct-clr.rct-generated-test/run-form! "gen.clj:246" (quote (clojure.test/is (= (quote (test-output-ns/bind-repl-vars! (def x 1))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:test-sexpr (quote (def x 1)), :expectation-type nil} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.clj:253
  (testing "gen.clj:253" (rct-clr.rct-generated-test/run-form! "gen.clj:253" (quote (clojure.test/is (= (quote (clojure.test/is (= 3 (test-output-ns/bind-repl-vars! (+ 1 2))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:test-sexpr (quote (+ 1 2)), :expectation-string "3", :expectation-type (quote =>)} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.clj:261
  (testing "gen.clj:261" (rct-clr.rct-generated-test/run-form! "gen.clj:261" (quote (clojure.test/is (= (quote (clojure.test/is (= (quote (1 2 3)) (test-output-ns/bind-repl-vars! (sizes))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:test-sexpr (quote (sizes)), :expectation-string "'(1 2 3)", :expectation-type (quote =>)} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.clj:269
  (testing "gen.clj:269" (rct-clr.rct-generated-test/run-form! "gen.clj:269" (quote (clojure.test/is (= (quote (clojure.test/is (= (+ 2 2) (test-output-ns/bind-repl-vars! (size))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:test-sexpr (quote (size)), :expectation-string "(+ 2 2)", :expectation-type (quote =>)} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.clj:277
  (testing "gen.clj:277" (rct-clr.rct-generated-test/run-form! "gen.clj:277" (quote (clojure.test/is (= (quote (matcho.core/assert {:status 200} (test-output-ns/bind-repl-vars! (get-status)))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:test-sexpr (quote (get-status)), :expectation-string "{:status 200}", :expectation-type (quote =>>)} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.clj:285
  (testing "gen.clj:285" (rct-clr.rct-generated-test/run-form! "gen.clj:285" (quote (clojure.test/is (= (quote (try (boom!) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}", :expectation-type (quote throws=>>)} (quote rct-clr.gen) (quote test-output-ns)))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.clj:305
  (testing "gen.clj:305" (rct-clr.rct-generated-test/run-form! "gen.clj:305" (quote (clojure.test/is (= "my-cool-namespace" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote my.cool.namespace)))))))))
(defn- rct-clr-gen-rct-block-5 []
  ;; gen.clj:432
  (testing "gen.clj:432" (rct-clr.rct-generated-test/run-form! "gen.clj:432" (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.clj:436
  (testing "gen.clj:436" (rct-clr.rct-generated-test/run-form! "gen.clj:436" (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.clj:440
  (testing "gen.clj:440" (rct-clr.rct-generated-test/run-form! "gen.clj:440" (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-n" "my.ns"])))))))
  ;; gen.clj:444
  (testing "gen.clj:444" (rct-clr.rct-generated-test/run-form! "gen.clj:444" (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc"])))))))
  ;; gen.clj:448
  (testing "gen.clj:448" (rct-clr.rct-generated-test/run-form! "gen.clj:448" (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts [])))))))
  ;; gen.clj:452
  (testing "gen.clj:452" (rct-clr.rct-generated-test/run-form! "gen.clj:452" (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["--bogus"]) :errors)))))))
  ;; gen.clj:454
  (testing "gen.clj:454" (rct-clr.rct-generated-test/run-form! "gen.clj:454" (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["-h"]) :help))))))))
(deftest rct-clr-gen-rct
  (binding [*ns* (the-ns 'rct-clr.gen)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-gen-rct-block-0)
    (rct-clr-gen-rct-block-1)
    (rct-clr-gen-rct-block-2)
    (rct-clr-gen-rct-block-3)
    (rct-clr-gen-rct-block-4)
    (rct-clr-gen-rct-block-5)))

