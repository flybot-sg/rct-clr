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
  ;; gen.cljc:49
  (testing "gen.cljc:49" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote {:io (quote clojure.java.io), :walk (quote clojure.walk), :tr (quote clojure.tools.reader), :ns-file (quote clojure.tools.namespace.file), :string (quote clojure.string), :z (quote rewrite-clj.zip), :ns-parse (quote clojure.tools.namespace.parse), :rct (quote com.mjdowney.rich-comment-tests), :current (quote rct-clr.gen), :emit (quote com.mjdowney.rich-comment-tests.emit-tests), :cli (quote clojure.tools.cli)})) (rct-clr.rct-generated-test/bind-repl-vars! (build-resolver (quote rct-clr.gen))))))))
  ;; gen.cljc:64
  (eval (quote (rct-clr.rct-generated-test/bind-repl-vars! (def rct-test-bare-ns (create-ns (gensym "bare-ns-"))))))
  ;; gen.cljc:65
  (testing "gen.cljc:65" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote {:current (ns-name rct-test-bare-ns)})) (rct-clr.rct-generated-test/bind-repl-vars! (let [result (build-resolver (ns-name rct-test-bare-ns))] (remove-ns (ns-name rct-test-bare-ns)) result))))))))
(defn- rct-clr-gen-rct-block-1 []
  ;; gen.cljc:99
  (testing "gen.cljc:99" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (.Message e)))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (.Message e)) (quote rct-clr.gen))))))))
  ;; gen.cljc:105
  (testing "gen.cljc:105" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (try (foo) (catch System.Exception e (.Message e)))))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (try (foo) (catch System.Exception e (.Message e)))) (quote rct-clr.gen))))))))
  ;; gen.cljc:115
  (testing "gen.cljc:115" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (+ (/ pos-score visits) (Math/Sqrt visits))))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (+ (/ pos-score visits) (Math/Sqrt visits))) (quote rct-clr.gen))))))))
  ;; gen.cljc:123
  (testing "gen.cljc:123" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (+ 1 2)))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (+ 1 2)) (quote rct-clr.gen))))))))
  ;; gen.cljc:127
  (testing "gen.cljc:127" (eval (quote (clojure.test/is (= #inst "2025-01-01T00:00:00.000-00:00" (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote #inst "2025-01-01T00:00:00.000-00:00") (quote rct-clr.gen))))))))
  ;; gen.cljc:133
  (testing "gen.cljc:133" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (read-string "[1 2 3]")))) (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote (read-string "[1 2 3]")) (quote rct-clr.gen))))))))
  ;; gen.cljc:137
  (testing "gen.cljc:137" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote nil) (quote rct-clr.gen))))))))
  ;; gen.cljc:143
  (testing "gen.cljc:143" (eval (quote (clojure.test/is (= :fallback (rct-clr.rct-generated-test/bind-repl-vars! (resolve-reader-conditionals (quote :fallback) (quote rct-clr.gen)))))))))
(defn- rct-clr-gen-rct-block-2 []
  ;; gen.cljc:176
  (testing "gen.cljc:176" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? 42)))))))
  ;; gen.cljc:180
  (testing "gen.cljc:180" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote (:h :c :s)))))))))
  ;; gen.cljc:184
  (testing "gen.cljc:184" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote foo))))))))
  ;; gen.cljc:187
  (testing "gen.cljc:187" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? [1 2 3])))))))
  ;; gen.cljc:191
  (testing "gen.cljc:191" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote {:a (1 2)}))))))))
  ;; gen.cljc:194
  (testing "gen.cljc:194" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? []))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.cljc:216
  (testing "gen.cljc:216" (eval (quote (clojure.test/is (= 42 (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "42"} (quote rct-clr.gen))))))))
  ;; gen.cljc:221
  (testing "gen.cljc:221" (eval (quote (clojure.test/is (= [1 2] (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen))))))))
  ;; gen.cljc:226
  (testing "gen.cljc:226" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string nil} (quote rct-clr.gen))))))))
  ;; gen.cljc:232
  (testing "gen.cljc:232" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (+ 1 2)))) (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "(+ 1 2)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:238
  (testing "gen.cljc:238" (eval (quote (clojure.test/is (= :clr (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:244
  (testing "gen.cljc:244" (eval (quote (clojure.test/is (= :rct-clr.gen/foo (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::foo"} (quote rct-clr.gen))))))))
  ;; gen.cljc:250
  (testing "gen.cljc:250" (eval (quote (clojure.test/is (= :clojure.string/join (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::string/join"} (quote rct-clr.gen))))))))
  ;; gen.cljc:256
  (testing "gen.cljc:256" (eval (quote (clojure.test/is (= 3 (rct-clr.rct-generated-test/bind-repl-vars! (count (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen)))))))))
  ;; gen.cljc:262
  (testing "gen.cljc:262" (eval (quote (try (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2"} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.cljc:297
  (testing "gen.cljc:297" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (test-output-ns/bind-repl-vars! (def x 1))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type nil, :test-sexpr (quote (def x 1))} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:304
  (testing "gen.cljc:304" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= 3 (test-output-ns/bind-repl-vars! (+ 1 2))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (+ 1 2)), :expectation-string "3"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:312
  (testing "gen.cljc:312" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= (test-output-ns/eval-expectation (quote (:h :c :s :d nil))) (test-output-ns/bind-repl-vars! (order))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (order)), :expectation-string "(:h :c :s :d nil)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:320
  (testing "gen.cljc:320" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (matcho.core/assert {:status 200} (test-output-ns/bind-repl-vars! (get-status)))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (get-status)), :expectation-string "{:status 200}"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:328
  (testing "gen.cljc:328" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (matcho.core/assert [0 1] (test-output-ns/bind-repl-vars! (range 5)))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (range 5)), :expectation-string "[0 1 ...]"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:336
  (testing "gen.cljc:336" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (try (boom!) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote throws=>>), :test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:350
  (testing "gen.cljc:350" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= :clr (test-output-ns/bind-repl-vars! (get-platform))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-platform)), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:358
  (testing "gen.cljc:358" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote (quote (clojure.test/is (= :rct-clr.gen/foo (test-output-ns/bind-repl-vars! (get-type))))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-type)), :expectation-string "::foo"} (quote rct-clr.gen) (quote test-output-ns)))))))))
(defn- rct-clr-gen-rct-block-5 []
  ;; gen.cljc:373
  (testing "gen.cljc:373" (eval (quote (clojure.test/is (= "my-cool-namespace" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote my.cool.namespace))))))))
  ;; gen.cljc:376
  (testing "gen.cljc:376" (eval (quote (clojure.test/is (= "single" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote single)))))))))
(defn- rct-clr-gen-rct-block-6 []
  ;; gen.cljc:498
  (testing "gen.cljc:498" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:502
  (testing "gen.cljc:502" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:506
  (testing "gen.cljc:506" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-n" "my.ns"])))))))
  ;; gen.cljc:510
  (testing "gen.cljc:510" (eval (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc"])))))))
  ;; gen.cljc:514
  (testing "gen.cljc:514" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts [])))))))
  ;; gen.cljc:518
  (testing "gen.cljc:518" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["--bogus"]) :errors)))))))
  ;; gen.cljc:521
  (testing "gen.cljc:521" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["-h"]) :help))))))))
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

