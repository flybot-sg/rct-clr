(ns ^:clr-only rct-clr.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [rct-clr.gen]))

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

;; rct-clr.gen
(defn- rct-clr-gen-rct-block-0 []
  ;; gen.cljc:41
  (testing "gen.cljc:41" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote {:io (quote clojure.java.io), :walk (quote clojure.walk), :tr (quote clojure.tools.reader), :ns-file (quote clojure.tools.namespace.file), :string (quote clojure.string), :z (quote rewrite-clj.zip), :ns-parse (quote clojure.tools.namespace.parse), :rct (quote com.mjdowney.rich-comment-tests), :current (quote rct-clr.gen), :emit (quote com.mjdowney.rich-comment-tests.emit-tests), :cli (quote clojure.tools.cli)})) (rct-clr.rct-generated-test/bind-repl-vars! (build-resolver (quote rct-clr.gen))))))))
  ;; gen.cljc:56
  (eval (quote (rct-clr.rct-generated-test/bind-repl-vars! (def rct-test-bare-ns (create-ns (gensym "bare-ns-"))))))
  ;; gen.cljc:57
  (testing "gen.cljc:57" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote {:current (ns-name rct-test-bare-ns)})) (rct-clr.rct-generated-test/bind-repl-vars! (let [result (build-resolver (ns-name rct-test-bare-ns))] (remove-ns (ns-name rct-test-bare-ns)) result))))))))
(defn- rct-clr-gen-rct-block-1 []
  ;; gen.cljc:91
  (testing "gen.cljc:91" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (.Message e)))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (.Message e)) (quote rct-clr.gen))))))))
  ;; gen.cljc:97
  (testing "gen.cljc:97" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (try (foo) (catch System.Exception e (.Message e)))))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (try (foo) (catch System.Exception e (.Message e)))) (quote rct-clr.gen))))))))
  ;; gen.cljc:107
  (testing "gen.cljc:107" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (+ (/ pos-score visits) (Math/Sqrt visits))))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (+ (/ pos-score visits) (Math/Sqrt visits))) (quote rct-clr.gen))))))))
  ;; gen.cljc:115
  (testing "gen.cljc:115" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (+ 1 2)))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (+ 1 2)) (quote rct-clr.gen))))))))
  ;; gen.cljc:119
  (testing "gen.cljc:119" (eval (quote (clojure.test/is (= #inst "2025-01-01T00:00:00.000-00:00" (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote #inst "2025-01-01T00:00:00.000-00:00") (quote rct-clr.gen))))))))
  ;; gen.cljc:125
  (testing "gen.cljc:125" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (read-string "[1 2 3]")))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (read-string "[1 2 3]")) (quote rct-clr.gen))))))))
  ;; gen.cljc:129
  (testing "gen.cljc:129" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote nil) (quote rct-clr.gen))))))))
  ;; gen.cljc:135
  (testing "gen.cljc:135" (eval (quote (clojure.test/is (= :fallback (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote :fallback) (quote rct-clr.gen)))))))))
(defn- rct-clr-gen-rct-block-2 []
  ;; gen.cljc:168
  (testing "gen.cljc:168" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? 42)))))))
  ;; gen.cljc:172
  (testing "gen.cljc:172" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote (:h :c :s)))))))))
  ;; gen.cljc:176
  (testing "gen.cljc:176" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote foo))))))))
  ;; gen.cljc:179
  (testing "gen.cljc:179" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? [1 2 3])))))))
  ;; gen.cljc:183
  (testing "gen.cljc:183" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote {:a (1 2)}))))))))
  ;; gen.cljc:186
  (testing "gen.cljc:186" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? []))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.cljc:208
  (testing "gen.cljc:208" (eval (quote (clojure.test/is (= 42 (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "42"} (quote rct-clr.gen))))))))
  ;; gen.cljc:213
  (testing "gen.cljc:213" (eval (quote (clojure.test/is (= [1 2] (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen))))))))
  ;; gen.cljc:218
  (testing "gen.cljc:218" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string nil} (quote rct-clr.gen))))))))
  ;; gen.cljc:224
  (testing "gen.cljc:224" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (+ 1 2)))) (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "(+ 1 2)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:230
  (testing "gen.cljc:230" (eval (quote (clojure.test/is (= :clr (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:236
  (testing "gen.cljc:236" (eval (quote (clojure.test/is (= :rct-clr.gen/foo (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::foo"} (quote rct-clr.gen))))))))
  ;; gen.cljc:242
  (testing "gen.cljc:242" (eval (quote (clojure.test/is (= :clojure.string/join (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::string/join"} (quote rct-clr.gen))))))))
  ;; gen.cljc:248
  (testing "gen.cljc:248" (eval (quote (clojure.test/is (= 3 (rct-clr.rct-generated-test/bind-repl-vars! (count (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen)))))))))
  ;; gen.cljc:254
  (testing "gen.cljc:254" (eval (quote (try (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2"} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.cljc:289
  (testing "gen.cljc:289" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (test-output-ns/bind-repl-vars! (def x 1))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type nil, :test-sexpr (quote (def x 1))} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:296
  (testing "gen.cljc:296" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= 3 (test-output-ns/bind-repl-vars! (+ 1 2))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (+ 1 2)), :expectation-string "3"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:304
  (testing "gen.cljc:304" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= (test-output-ns/eval-expectation (quote (:h :c :s :d nil))) (test-output-ns/bind-repl-vars! (order))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (order)), :expectation-string "(:h :c :s :d nil)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:312
  (testing "gen.cljc:312" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (matcho.core/assert {:status 200} (test-output-ns/bind-repl-vars! (get-status)))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (get-status)), :expectation-string "{:status 200}"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:320
  (testing "gen.cljc:320" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (matcho.core/assert [0 1] (test-output-ns/bind-repl-vars! (range 5)))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (range 5)), :expectation-string "[0 1 ...]"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:328
  (testing "gen.cljc:328" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (try (boom!) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote throws=>>), :test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:342
  (testing "gen.cljc:342" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= :clr (test-output-ns/bind-repl-vars! (get-platform))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-platform)), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:350
  (testing "gen.cljc:350" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= :rct-clr.gen/foo (test-output-ns/bind-repl-vars! (get-type))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-type)), :expectation-string "::foo"} (quote rct-clr.gen) (quote test-output-ns)))))))))
(defn- rct-clr-gen-rct-block-5 []
  ;; gen.cljc:365
  (testing "gen.cljc:365" (eval (quote (clojure.test/is (= "my-cool-namespace" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote my.cool.namespace))))))))
  ;; gen.cljc:368
  (testing "gen.cljc:368" (eval (quote (clojure.test/is (= "single" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote single)))))))))
(defn- rct-clr-gen-rct-block-6 []
  ;; gen.cljc:490
  (testing "gen.cljc:490" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:494
  (testing "gen.cljc:494" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:498
  (testing "gen.cljc:498" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-n" "my.ns"])))))))
  ;; gen.cljc:502
  (testing "gen.cljc:502" (eval (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc"])))))))
  ;; gen.cljc:506
  (testing "gen.cljc:506" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts [])))))))
  ;; gen.cljc:510
  (testing "gen.cljc:510" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["--bogus"]) :errors)))))))
  ;; gen.cljc:513
  (testing "gen.cljc:513" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["-h"]) :help))))))))
(deftest rct-clr-gen-rct
  (binding [*ns* (the-ns 'rct-clr.gen)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-gen-rct-block-0)
    (rct-clr-gen-rct-block-1)
    (rct-clr-gen-rct-block-2)
    (rct-clr-gen-rct-block-3)
    (rct-clr-gen-rct-block-4)
    (rct-clr-gen-rct-block-5)
    (rct-clr-gen-rct-block-6)))

