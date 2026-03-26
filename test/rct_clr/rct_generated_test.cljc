(ns ^:clr-only rct-clr.rct-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [rct-clr.gen]))

(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})

;; rct-clr.gen
(defn- rct-clr-gen-rct-block-0 []
  ;; gen.cljc:40
  (testing "gen.cljc:40" (eval (quote (clojure.test/is (= {:io (quote clojure.java.io), :tr (quote clojure.tools.reader), :ns-file (quote clojure.tools.namespace.file), :string (quote clojure.string), :z (quote rewrite-clj.zip), :ns-parse (quote clojure.tools.namespace.parse), :rct (quote com.mjdowney.rich-comment-tests), :current (quote rct-clr.gen), :emit (quote com.mjdowney.rich-comment-tests.emit-tests), :cli (quote clojure.tools.cli)} (build-resolver (quote rct-clr.gen))))))))
(defn- rct-clr-gen-rct-block-1 []
  ;; gen.cljc:80
  (testing "gen.cljc:80" (eval (quote (clojure.test/is (= 42 (read-expectation {:expectation-type (quote =>), :expectation-string "42"} (quote rct-clr.gen)))))))
  ;; gen.cljc:85
  (testing "gen.cljc:85" (eval (quote (clojure.test/is (= [1 2] (read-expectation {:expectation-type (quote =>>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen)))))))
  ;; gen.cljc:90
  (testing "gen.cljc:90" (eval (quote (clojure.test/is (= nil (read-expectation {:expectation-type (quote =>), :expectation-string nil} (quote rct-clr.gen)))))))
  ;; gen.cljc:96
  (testing "gen.cljc:96" (eval (quote (clojure.test/is (= :clr (read-expectation {:expectation-type (quote =>), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen)))))))
  ;; gen.cljc:102
  (testing "gen.cljc:102" (eval (quote (clojure.test/is (= :rct-clr.gen/foo (read-expectation {:expectation-type (quote =>), :expectation-string "::foo"} (quote rct-clr.gen)))))))
  ;; gen.cljc:108
  (testing "gen.cljc:108" (eval (quote (clojure.test/is (= :clojure.string/join (read-expectation {:expectation-type (quote =>), :expectation-string "::string/join"} (quote rct-clr.gen)))))))
  ;; gen.cljc:114
  (testing "gen.cljc:114" (eval (quote (clojure.test/is (= 3 (count (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen))))))))
  ;; gen.cljc:120
  (testing "gen.cljc:120" (eval (quote (try (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2"} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch Exception e (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-2 []
  ;; gen.cljc:147
  (testing "gen.cljc:147" (eval (quote (clojure.test/is (= (quote (def x 1)) (datum->form {:expectation-type nil, :test-sexpr (quote (def x 1))} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:154
  (testing "gen.cljc:154" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= 3 (+ 1 2)))) (datum->form {:expectation-type (quote =>), :test-sexpr (quote (+ 1 2)), :expectation-string "3"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:162
  (testing "gen.cljc:162" (eval (quote (clojure.test/is (= (quote (matcho.core/assert {:status 200} (get-status))) (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (get-status)), :expectation-string "{:status 200}"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:170
  (testing "gen.cljc:170" (eval (quote (clojure.test/is (= (quote (matcho.core/assert [0 1] (range 5))) (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (range 5)), :expectation-string "[0 1 ...]"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:178
  (testing "gen.cljc:178" (eval (quote (clojure.test/is (= (quote (try (boom!) (clojure.test/is false "Expected exception") (catch Exception e (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))) (datum->form {:expectation-type (quote throws=>>), :test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:191
  (testing "gen.cljc:191" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= :clr (get-platform)))) (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-platform)), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:199
  (testing "gen.cljc:199" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= :rct-clr.gen/foo (get-type)))) (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-type)), :expectation-string "::foo"} (quote rct-clr.gen) (quote test-output-ns))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.cljc:214
  (testing "gen.cljc:214" (eval (quote (clojure.test/is (= "my-cool-namespace" (ns-sym->test-base (quote my.cool.namespace)))))))
  ;; gen.cljc:217
  (testing "gen.cljc:217" (eval (quote (clojure.test/is (= "single" (ns-sym->test-base (quote single))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.cljc:316
  (testing "gen.cljc:316" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (validate-opts ["-o" "out.cljc" "-n" "my.ns"]))))))
  ;; gen.cljc:320
  (testing "gen.cljc:320" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"]))))))
  ;; gen.cljc:324
  (testing "gen.cljc:324" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (validate-opts ["-n" "my.ns"]))))))
  ;; gen.cljc:328
  (testing "gen.cljc:328" (eval (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (validate-opts ["-o" "out.cljc"]))))))
  ;; gen.cljc:332
  (testing "gen.cljc:332" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (validate-opts []))))))
  ;; gen.cljc:336
  (testing "gen.cljc:336" (eval (quote (clojure.test/is (= true (contains? (validate-opts ["--bogus"]) :errors))))))
  ;; gen.cljc:339
  (testing "gen.cljc:339" (eval (quote (clojure.test/is (= true (contains? (validate-opts ["-h"]) :help)))))))
(deftest rct-clr-gen-rct
  (binding [*ns* (the-ns 'rct-clr.gen)]
    (rct-clr-gen-rct-block-0)
    (rct-clr-gen-rct-block-1)
    (rct-clr-gen-rct-block-2)
    (rct-clr-gen-rct-block-3)
    (rct-clr-gen-rct-block-4)))

