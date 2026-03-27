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
  ;; gen.cljc:41
  (testing "gen.cljc:41" (eval (quote (clojure.test/is (= {:io (quote clojure.java.io), :walk (quote clojure.walk), :tr (quote clojure.tools.reader), :ns-file (quote clojure.tools.namespace.file), :string (quote clojure.string), :z (quote rewrite-clj.zip), :ns-parse (quote clojure.tools.namespace.parse), :rct (quote com.mjdowney.rich-comment-tests), :current (quote rct-clr.gen), :emit (quote com.mjdowney.rich-comment-tests.emit-tests), :cli (quote clojure.tools.cli)} (build-resolver (quote rct-clr.gen))))))))
(defn- rct-clr-gen-rct-block-1 []
  ;; gen.cljc:83
  (testing "gen.cljc:83" (eval (quote (clojure.test/is (= (quote (.Message e)) (resolve-reader-conditionals (quote (.Message e)) (quote rct-clr.gen)))))))
  ;; gen.cljc:89
  (testing "gen.cljc:89" (eval (quote (clojure.test/is (= (quote (try (foo) (catch System.Exception e (.Message e)))) (resolve-reader-conditionals (quote (try (foo) (catch System.Exception e (.Message e)))) (quote rct-clr.gen)))))))
  ;; gen.cljc:99
  (testing "gen.cljc:99" (eval (quote (clojure.test/is (= (quote (+ (/ pos-score visits) (Math/Sqrt visits))) (resolve-reader-conditionals (quote (+ (/ pos-score visits) (Math/Sqrt visits))) (quote rct-clr.gen)))))))
  ;; gen.cljc:107
  (testing "gen.cljc:107" (eval (quote (clojure.test/is (= (quote (+ 1 2)) (resolve-reader-conditionals (quote (+ 1 2)) (quote rct-clr.gen)))))))
  ;; gen.cljc:111
  (testing "gen.cljc:111" (eval (quote (clojure.test/is (= #inst "2025-01-01T00:00:00.000-00:00" (resolve-reader-conditionals (quote #inst "2025-01-01T00:00:00.000-00:00") (quote rct-clr.gen)))))))
  ;; gen.cljc:117
  (testing "gen.cljc:117" (eval (quote (clojure.test/is (= (quote (read-string "[1 2 3]")) (resolve-reader-conditionals (quote (read-string "[1 2 3]")) (quote rct-clr.gen)))))))
  ;; gen.cljc:121
  (testing "gen.cljc:121" (eval (quote (clojure.test/is (= nil (resolve-reader-conditionals (quote nil) (quote rct-clr.gen)))))))
  ;; gen.cljc:127
  (testing "gen.cljc:127" (eval (quote (clojure.test/is (= :fallback (resolve-reader-conditionals (quote :fallback) (quote rct-clr.gen))))))))
(defn- rct-clr-gen-rct-block-2 []
  ;; gen.cljc:163
  (testing "gen.cljc:163" (eval (quote (clojure.test/is (= 42 (read-expectation {:expectation-type (quote =>), :expectation-string "42"} (quote rct-clr.gen)))))))
  ;; gen.cljc:168
  (testing "gen.cljc:168" (eval (quote (clojure.test/is (= [1 2] (read-expectation {:expectation-type (quote =>>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen)))))))
  ;; gen.cljc:173
  (testing "gen.cljc:173" (eval (quote (clojure.test/is (= nil (read-expectation {:expectation-type (quote =>), :expectation-string nil} (quote rct-clr.gen)))))))
  ;; gen.cljc:179
  (testing "gen.cljc:179" (eval (quote (clojure.test/is (= :clr (read-expectation {:expectation-type (quote =>), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen)))))))
  ;; gen.cljc:185
  (testing "gen.cljc:185" (eval (quote (clojure.test/is (= :rct-clr.gen/foo (read-expectation {:expectation-type (quote =>), :expectation-string "::foo"} (quote rct-clr.gen)))))))
  ;; gen.cljc:191
  (testing "gen.cljc:191" (eval (quote (clojure.test/is (= :clojure.string/join (read-expectation {:expectation-type (quote =>), :expectation-string "::string/join"} (quote rct-clr.gen)))))))
  ;; gen.cljc:197
  (testing "gen.cljc:197" (eval (quote (clojure.test/is (= 3 (count (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2 ...]"} (quote rct-clr.gen))))))))
  ;; gen.cljc:203
  (testing "gen.cljc:203" (eval (quote (try (read-expectation {:expectation-type (quote =>), :expectation-string "[1 2"} (quote rct-clr.gen)) (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert {} (rct-clr.rct-generated-test/error->map e))))))))
(defn- rct-clr-gen-rct-block-3 []
  ;; gen.cljc:230
  (testing "gen.cljc:230" (eval (quote (clojure.test/is (= (quote (def x 1)) (datum->form {:expectation-type nil, :test-sexpr (quote (def x 1))} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:237
  (testing "gen.cljc:237" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= 3 (+ 1 2)))) (datum->form {:expectation-type (quote =>), :test-sexpr (quote (+ 1 2)), :expectation-string "3"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:245
  (testing "gen.cljc:245" (eval (quote (clojure.test/is (= (quote (matcho.core/assert {:status 200} (get-status))) (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (get-status)), :expectation-string "{:status 200}"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:253
  (testing "gen.cljc:253" (eval (quote (clojure.test/is (= (quote (matcho.core/assert [0 1] (range 5))) (datum->form {:expectation-type (quote =>>), :test-sexpr (quote (range 5)), :expectation-string "[0 1 ...]"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:261
  (testing "gen.cljc:261" (eval (quote (clojure.test/is (= (quote (try (boom!) (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:class Exception} (test-output-ns/error->map e))))) (datum->form {:expectation-type (quote throws=>>), :test-sexpr (quote (boom!)), :expectation-string "{:error/class Exception}"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:274
  (testing "gen.cljc:274" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= :clr (get-platform)))) (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-platform)), :expectation-string "#?(:clj :jvm :cljr :clr)"} (quote rct-clr.gen) (quote test-output-ns)))))))
  ;; gen.cljc:282
  (testing "gen.cljc:282" (eval (quote (clojure.test/is (= (quote (clojure.test/is (= :rct-clr.gen/foo (get-type)))) (datum->form {:expectation-type (quote =>), :test-sexpr (quote (get-type)), :expectation-string "::foo"} (quote rct-clr.gen) (quote test-output-ns))))))))
(defn- rct-clr-gen-rct-block-4 []
  ;; gen.cljc:297
  (testing "gen.cljc:297" (eval (quote (clojure.test/is (= "my-cool-namespace" (ns-sym->test-base (quote my.cool.namespace)))))))
  ;; gen.cljc:300
  (testing "gen.cljc:300" (eval (quote (clojure.test/is (= "single" (ns-sym->test-base (quote single))))))))
(defn- rct-clr-gen-rct-block-5 []
  ;; gen.cljc:399
  (testing "gen.cljc:399" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src"], :output "out.cljc", :namespace "my.ns"}} (validate-opts ["-o" "out.cljc" "-n" "my.ns"]))))))
  ;; gen.cljc:403
  (testing "gen.cljc:403" (eval (quote (clojure.test/is (= {:ok {:src-dirs ["src1" "src2"], :output "out.cljc", :namespace "my.ns"}} (validate-opts ["-s" "src1" "-s" "src2" "-o" "out.cljc" "-n" "my.ns"]))))))
  ;; gen.cljc:407
  (testing "gen.cljc:407" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o"]} (validate-opts ["-n" "my.ns"]))))))
  ;; gen.cljc:411
  (testing "gen.cljc:411" (eval (quote (clojure.test/is (= {:errors ["Must provide --namespace / -n"]} (validate-opts ["-o" "out.cljc"]))))))
  ;; gen.cljc:415
  (testing "gen.cljc:415" (eval (quote (clojure.test/is (= {:errors ["Must provide --output / -o" "Must provide --namespace / -n"]} (validate-opts []))))))
  ;; gen.cljc:419
  (testing "gen.cljc:419" (eval (quote (clojure.test/is (= true (contains? (validate-opts ["--bogus"]) :errors))))))
  ;; gen.cljc:422
  (testing "gen.cljc:422" (eval (quote (clojure.test/is (= true (contains? (validate-opts ["-h"]) :help)))))))
(deftest rct-clr-gen-rct
  (binding [*ns* (the-ns 'rct-clr.gen)]
    (rct-clr-gen-rct-block-0)
    (rct-clr-gen-rct-block-1)
    (rct-clr-gen-rct-block-2)
    (rct-clr-gen-rct-block-3)
    (rct-clr-gen-rct-block-4)
    (rct-clr-gen-rct-block-5)))

