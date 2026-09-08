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
  (testing "gen.cljc:49" (eval (quote (clojure.test/is (= {(quote ns-parse) (quote clojure.tools.namespace.parse), (quote cli) (quote clojure.tools.cli), (quote string) (quote clojure.string), (quote walk) (quote clojure.walk), (quote tr) (quote clojure.tools.reader), :current (quote rct-clr.gen), (quote emit) (quote com.mjdowney.rich-comment-tests.emit-tests), (quote ns-file) (quote clojure.tools.namespace.file), (quote io) (quote clojure.java.io), (quote rct) (quote com.mjdowney.rich-comment-tests), (quote z) (quote rewrite-clj.zip)} (rct-clr.rct-generated-test/bind-repl-vars! (build-resolver (quote rct-clr.gen))))))))
  ;; gen.cljc:64
  (eval (quote (rct-clr.rct-generated-test/bind-repl-vars! (def rct-test-bare-ns (create-ns (gensym "bare-ns-"))))))
  ;; gen.cljc:65
  (testing "gen.cljc:65" (eval (quote (clojure.test/is (= (rct-clr.rct-generated-test/eval-expectation (quote {:current (ns-name rct-test-bare-ns)})) (rct-clr.rct-generated-test/bind-repl-vars! (let [result (build-resolver (ns-name rct-test-bare-ns))] (remove-ns (ns-name rct-test-bare-ns)) result))))))))
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
  ;; gen.cljc:169
  (testing "gen.cljc:169" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? 42)))))))
  ;; gen.cljc:172
  (testing "gen.cljc:172" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote (:h :c :s)))))))))
  ;; gen.cljc:175
  (testing "gen.cljc:175" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote (quote (:h :c :s))))))))))
  ;; gen.cljc:178
  (testing "gen.cljc:178" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote foo))))))))
  ;; gen.cljc:180
  (testing "gen.cljc:180" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? [1 2 3])))))))
  ;; gen.cljc:183
  (testing "gen.cljc:183" (eval (quote (clojure.test/is (= false (rct-clr.rct-generated-test/bind-repl-vars! (self-evaluating? (quote {:a (1 2)})))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.cljc:205
  (testing "gen.cljc:205" (eval (quote (clojure.test/is (= (quote (quote ({:suit :h} {:suit :c}))) (rct-clr.rct-generated-test/bind-repl-vars! (quote-data-seqs (quote ({:suit :h} {:suit :c})))))))))
  ;; gen.cljc:208
  (testing "gen.cljc:208" (eval (quote (clojure.test/is (= (quote (+ 1 2)) (rct-clr.rct-generated-test/bind-repl-vars! (quote-data-seqs (quote (+ 1 2)))))))))
  ;; gen.cljc:211
  (testing "gen.cljc:211" (eval (quote (clojure.test/is (= (quote foo) (rct-clr.rct-generated-test/bind-repl-vars! (quote-data-seqs (quote foo))))))))
  ;; gen.cljc:214
  (testing "gen.cljc:214" (eval (quote (clojure.test/is (= (quote {:a (quote (1 2)), :b (+ 1 2)}) (rct-clr.rct-generated-test/bind-repl-vars! (quote-data-seqs (quote {:b (+ 1 2), :a (1 2)}))))))))
  ;; gen.cljc:217
  (testing "gen.cljc:217" (eval (quote (clojure.test/is (= (quote [#{(quote (1 2))}]) (rct-clr.rct-generated-test/bind-repl-vars! (quote-data-seqs (quote [#{(1 2)}])))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.cljc:238
  (testing "gen.cljc:238" (eval (quote (clojure.test/is (= 42 (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "42"} (quote rct-clr.gen))))))))
  ;; gen.cljc:243
  (testing "gen.cljc:243" (eval (quote (clojure.test/is (= [1 2] (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen))))))))
  ;; gen.cljc:248
  (testing "gen.cljc:248" (eval (quote (clojure.test/is (= nil (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string nil} (quote rct-clr.gen))))))))
  ;; gen.cljc:254
  (testing "gen.cljc:254" (eval (quote (clojure.test/is (= (quote (+ 1 2)) (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "(+ 1 2)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:260
  (testing "gen.cljc:260" (eval (quote (clojure.test/is (= :clr (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen))))))))
  ;; gen.cljc:266
  (testing "gen.cljc:266" (eval (quote (clojure.test/is (= :rct-clr.gen/foo (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::foo"} (quote rct-clr.gen))))))))
  ;; gen.cljc:272
  (testing "gen.cljc:272" (eval (quote (clojure.test/is (= :clojure.string/join (rct-clr.rct-generated-test/bind-repl-vars! (read-expectation {:expectation-type (quote =>), :expectation-string "::string/join"} (quote rct-clr.gen))))))))
  ;; gen.cljc:278
  (testing "gen.cljc:278" (eval (quote (clojure.test/is (= 3 (rct-clr.rct-generated-test/bind-repl-vars! (count (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen)))))))))
  ;; gen.cljc:284
  (testing "gen.cljc:284" (eval (quote (try (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2"} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-5 []
  ;; gen.cljc:320
  (testing "gen.cljc:320" (eval (quote (clojure.test/is (= (quote (test-output-ns/bind-repl-vars! (def x 1))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type nil, :test-sexpr (quote (def x 1))} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:327
  (testing "gen.cljc:327" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= 3 (test-output-ns/bind-repl-vars! (+ 1 2))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (+ 1 2)), :expectation-string "3"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:335
  (testing "gen.cljc:335" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= (quote (:h :c :s :d nil)) (test-output-ns/bind-repl-vars! (order))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (order)), :expectation-string "(:h :c :s :d nil)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:343
  (testing "gen.cljc:343" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= (test-output-ns/eval-expectation (quote (+ 2 2))) (test-output-ns/bind-repl-vars! (size))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>), :test-sexpr (quote (size)), :expectation-string "(+ 2 2)"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:351
  (testing "gen.cljc:351" (eval (quote (clojure.test/is (= (quote (matcho.core/assert {:status 200} (test-output-ns/bind-repl-vars! (get-status)))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (get-status)), :expectation-string "{:status 200}"} (quote rct-clr.gen) (quote test-output-ns))))))))
  ;; gen.cljc:359
  (testing "gen.cljc:359" (eval (quote (clojure.test/is (= (quote (try (boom!) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))) (rct-clr.rct-generated-test/bind-repl-vars! (datum->form {:expectation-type (quote throws=>>), :test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}"} (quote rct-clr.gen) (quote test-output-ns)))))))))
(defn- rct-clr-gen-rct-block-6 []
  ;; gen.cljc:379
  (testing "gen.cljc:379" (eval (quote (clojure.test/is (= "my-cool-namespace" (rct-clr.rct-generated-test/bind-repl-vars! (ns-sym->test-base (quote my.cool.namespace)))))))))
(defn- rct-clr-gen-rct-block-7 []
  ;; gen.cljc:500
  (testing "gen.cljc:500" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:504
  (testing "gen.cljc:504" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"])))))))
  ;; gen.cljc:508
  (testing "gen.cljc:508" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-n" "my.ns"])))))))
  ;; gen.cljc:512
  (testing "gen.cljc:512" (eval (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts ["-o" "out.cljc"])))))))
  ;; gen.cljc:516
  (testing "gen.cljc:516" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (rct-clr.rct-generated-test/bind-repl-vars! (validate-opts [])))))))
  ;; gen.cljc:520
  (testing "gen.cljc:520" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["--bogus"]) :errors)))))))
  ;; gen.cljc:522
  (testing "gen.cljc:522" (eval (quote (clojure.test/is (= true (rct-clr.rct-generated-test/bind-repl-vars! (contains? (validate-opts ["-h"]) :help))))))))
(deftest rct-clr-gen-rct
  (binding [*ns* (the-ns 'rct-clr.gen)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-gen-rct-block-0)
    (rct-clr-gen-rct-block-1)
    (rct-clr-gen-rct-block-2)
    (rct-clr-gen-rct-block-3)
    (rct-clr-gen-rct-block-4)
    (rct-clr-gen-rct-block-5)
    (rct-clr-gen-rct-block-6)
    (rct-clr-gen-rct-block-7)))

