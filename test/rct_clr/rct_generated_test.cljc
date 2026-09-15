(ns ^:clr-only rct-clr.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [rct-clr.gen]))

(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})

(defn bind-repl-vars! [result]
  (set! *3 *2)
  (set! *2 *1)
  (set! *1 result)
  result)

;; rct-clr.gen
(defn- rct-clr-gen-rct-block-0 []
  ;; gen.cljc:49
  (testing "gen.cljc:49" (eval (quote (clojure.test/is (= {(quote ns-parse) (quote clojure.tools.namespace.parse), (quote cli) (quote clojure.tools.cli), (quote string) (quote clojure.string), (quote walk) (quote clojure.walk), (quote tr) (quote clojure.tools.reader), :current (quote rct-clr.gen), (quote emit) (quote com.mjdowney.rich-comment-tests.emit-tests), (quote ns-file) (quote clojure.tools.namespace.file), (quote io) (quote clojure.java.io), (quote rct) (quote com.mjdowney.rich-comment-tests), (quote z) (quote rewrite-clj.zip)} (rct-clr.rct-generated-test/bind-repl-vars! (build-resolver (quote rct-clr.gen))))))))
  ;; gen.cljc:64
  (eval (quote (rct-clr.rct-generated-test/bind-repl-vars! (def rct-test-bare-ns (create-ns (gensym "bare-ns-"))))))
  ;; gen.cljc:65
  (testing "gen.cljc:65" (eval (quote (clojure.test/is (= {:current (ns-name rct-test-bare-ns)} (rct-clr.rct-generated-test/bind-repl-vars! (let [result (build-resolver (ns-name rct-test-bare-ns))] (remove-ns (ns-name rct-test-bare-ns)) result))))))))
(defn- rct-clr-gen-rct-block-1 []
  ;; gen.cljc:99
  (testing "gen.cljc:99" (eval (quote (clojure.test/is (= (quote (.Message e)) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (.Message e)) (quote rct-clr.gen))))))))
  ;; gen.cljc:105
  (testing "gen.cljc:105" (eval (quote (clojure.test/is (= (quote (try (foo) (catch System.Exception e (.Message e)))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (try (foo) (catch System.Exception e (.Message e)))) (quote rct-clr.gen))))))))
  ;; gen.cljc:116
  (testing "gen.cljc:116" (eval (quote (clojure.test/is (= (quote (+ 1 2)) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (+ 1 2)) (quote rct-clr.gen))))))))
  ;; gen.cljc:119
  (testing "gen.cljc:119" (eval (quote (clojure.test/is (= #inst "2025-01-01T00:00:00.000-00:00" (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote #inst "2025-01-01T00:00:00.000-00:00") (quote rct-clr.gen))))))))
  ;; gen.cljc:125
  (testing "gen.cljc:125" (eval (quote (clojure.test/is (= (quote (read-string "[1 2 3]")) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (read-string "[1 2 3]")) (quote rct-clr.gen))))))))
  ;; gen.cljc:129
  (testing "gen.cljc:129" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote nil) (quote rct-clr.gen))))))))
  ;; gen.cljc:135
  (testing "gen.cljc:135" (eval (quote (clojure.test/is (= :fallback (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote :fallback) (quote rct-clr.gen)))))))))
(defn- rct-clr-gen-rct-block-2 []
  ;; gen.cljc:171
  (testing "gen.cljc:171" (eval (quote (clojure.test/is (= 42 (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "42"} (quote rct-clr.gen))))))))
  ;; gen.cljc:176
  (testing "gen.cljc:176" (eval (quote (clojure.test/is (= [1 2] (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen))))))))
  ;; gen.cljc:181
  (testing "gen.cljc:181" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string nil} (quote rct-clr.gen))))))))
  ;; gen.cljc:187
  (testing "gen.cljc:187" (eval (quote (clojure.test/is (= (quote (+ 1 2)) (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "(+ 1 2)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:193
  (testing "gen.cljc:193" (eval (quote (clojure.test/is (= :clr (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:199
  (testing "gen.cljc:199" (eval (quote (clojure.test/is (= :rct-clr.gen/foo (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::foo"} (quote rct-clr.gen))))))))
  ;; gen.cljc:205
  (testing "gen.cljc:205" (eval (quote (clojure.test/is (= :clojure.string/join (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::string/join"} (quote rct-clr.gen))))))))
  ;; gen.cljc:211
  (testing "gen.cljc:211" (eval (quote (clojure.test/is (= 3 (rct-clr.rct-generated-test/bind-repl-vars! (count (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen)))))))))
  ;; gen.cljc:217
  (testing "gen.cljc:217" (eval (quote (try (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2"} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.cljc:246
  (testing "gen.cljc:246" (eval (quote (clojure.test/is (= (quote (test-output-ns/bind-repl-vars! (def x 1))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type nil, :test-sexpr (quote (def x 1))} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:253
  (testing "gen.cljc:253" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= 3 (test-output-ns/bind-repl-vars! (+ 1 2))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (+ 1 2)), :expectation-string "3"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:261
  (testing "gen.cljc:261" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= (quote (1 2 3)) (test-output-ns/bind-repl-vars! (sizes))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (sizes)), :expectation-string "'(1 2 3)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:269
  (testing "gen.cljc:269" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= (+ 2 2) (test-output-ns/bind-repl-vars! (size))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (size)), :expectation-string "(+ 2 2)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:277
  (testing "gen.cljc:277" (eval (quote (clojure.test/is (= (quote (matcho.core/assert {:status 200} (test-output-ns/bind-repl-vars! (get-status)))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (get-status)), :expectation-string "{:status 200}"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:285
  (testing "gen.cljc:285" (eval (quote (clojure.test/is (= (quote (try (boom!) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote throws=>>), :test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}"} (quote rct-clr.gen) (quote test-output-ns)))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.cljc:305
  (testing "gen.cljc:305" (eval (quote (clojure.test/is (= "my-cool-namespace" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote my.cool.namespace)))))))))
(defn- rct-clr-gen-rct-block-5 []
  ;; gen.cljc:416
  (testing "gen.cljc:416" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:420
  (testing "gen.cljc:420" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:424
  (testing "gen.cljc:424" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-n" "my.ns"])))))))
  ;; gen.cljc:428
  (testing "gen.cljc:428" (eval (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc"])))))))
  ;; gen.cljc:432
  (testing "gen.cljc:432" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts [])))))))
  ;; gen.cljc:436
  (testing "gen.cljc:436" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["--bogus"]) :errors)))))))
  ;; gen.cljc:438
  (testing "gen.cljc:438" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["-h"]) :help))))))))
(deftest rct-clr-gen-rct
  (binding [*ns* (the-ns 'rct-clr.gen)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-gen-rct-block-0)
    (rct-clr-gen-rct-block-1)
    (rct-clr-gen-rct-block-2)
    (rct-clr-gen-rct-block-3)
    (rct-clr-gen-rct-block-4)
    (rct-clr-gen-rct-block-5)))

