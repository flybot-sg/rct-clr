(ns ^:clr-only rct-clr.sample-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [rct-clr.sample]
            [rct-clr.sample-clr]))

(defn error->map [e]
  {:error/class (type e)
   :error/message #?(:clj (.getMessage e) :cljr (.Message e))
   :error/data (ex-data e)})

(defn eval-expectation [form]
  (try
    (eval form)
    (catch #?(:clj Exception :cljr System.Exception) _
      form)))

;; rct-clr.sample
(defn- rct-clr-sample-rct-block-0 []
  ;; sample.cljc:17
  (testing "sample.cljc:17" (eval (quote (clojure.test/is (= 3 (add 1 2))))))
  ;; sample.cljc:20
  (testing "sample.cljc:20" (eval (quote (clojure.test/is (= 0 (add -1 1))))))
  ;; sample.cljc:24
  (eval (quote (def base-val 10)))
  ;; sample.cljc:27
  (testing "sample.cljc:27" (eval (quote (clojure.test/is (= 15 (add base-val 5))))))
  ;; sample.cljc:31
  (eval (quote (def doubled (* base-val 2))))
  ;; sample.cljc:33
  (testing "sample.cljc:33" (eval (quote (clojure.test/is (= 21 (add doubled 1)))))))
(defn- rct-clr-sample-rct-block-1 []
  ;; sample.cljc:48
  (testing "sample.cljc:48" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote (:h :c :s :d))) (suits))))))
  ;; sample.cljc:52
  (testing "sample.cljc:52" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote (1 2 3))) (map inc (range 3)))))))
  ;; sample.cljc:56
  (testing "sample.cljc:56" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote foo)) (a-symbol))))))
  ;; sample.cljc:60
  (testing "sample.cljc:60" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote (+ 2 2))) (count (suits))))))))
(defn- rct-clr-sample-rct-block-2 []
  ;; sample.cljc:71
  (testing "sample.cljc:71" (eval (quote (clojure.test/is (= "Hello, World!" (greet "World"))))))
  ;; sample.cljc:74
  (testing "sample.cljc:74" (eval (quote (clojure.test/is (= "HELLO, TEST!" (str/upper-case (greet "test")))))))
  ;; sample.cljc:77
  (testing "sample.cljc:77" (eval (quote (clojure.test/is (= "a, b, c" (str/join ", " ["a" "b" "c"]))))))
  ;; sample.cljc:80
  (testing "sample.cljc:80" (eval (quote (clojure.test/is (= true (str/blank? ""))))))
  ;; sample.cljc:83
  (testing "sample.cljc:83" (eval (quote (clojure.test/is (= false (str/blank? "x")))))))
(defn- rct-clr-sample-rct-block-3 []
  ;; sample.cljc:95
  (testing "sample.cljc:95" (eval (quote (clojure.test/is (= :clr (platform)))))))
(defn- rct-clr-sample-rct-block-4 []
  ;; sample.cljc:106
  (testing "sample.cljc:106" (eval (quote (clojure.test/is (= :rct-clr.sample/sample (my-type)))))))
(defn- rct-clr-sample-rct-block-5 []
  ;; sample.cljc:119
  (testing "sample.cljc:119" (eval (quote (clojure.test/is (= {:clojure.string/join :string-alias, :clojure.set/union :set-alias, :clojure.walk/walk :walk-alias} (alias-kws)))))))
(defn- rct-clr-sample-rct-block-6 []
  ;; sample.cljc:136
  (testing "sample.cljc:136" (eval (quote (clojure.test/is (= {:id 1, :name "Alice", :settings {:theme "dark", :lang "en", :notifications true}, :tags #{:active :verified}} (user-profile 1 "Alice"))))))
  ;; sample.cljc:143
  (testing "sample.cljc:143" (eval (quote (clojure.test/is (= "dark" (get-in (user-profile 1 "Alice") [:settings :theme])))))))
(defn- rct-clr-sample-rct-block-7 []
  ;; sample.cljc:159
  (testing "sample.cljc:159" (eval (quote (matcho.core/assert {:status 200, :body {:users []}} (api-response {:users []})))))
  ;; sample.cljc:163
  (testing "sample.cljc:163" (eval (quote (matcho.core/assert {:body {:count 5}, :timing {:start 0}} (api-response {:count 5}))))))
(defn- rct-clr-sample-rct-block-8 []
  ;; sample.cljc:178
  (testing "sample.cljc:178" (eval (quote (matcho.core/assert [{:name "a"}] (scored-items)))))
  ;; sample.cljc:182
  (testing "sample.cljc:182" (eval (quote (matcho.core/assert ^#:matcho{:strict true} ["a" "b" "c"] (mapv :name (scored-items)))))))
(defn- rct-clr-sample-rct-block-9 []
  ;; sample.cljc:193
  (testing "sample.cljc:193" (eval (quote (matcho.core/assert [0 1 1 2] (fibonacci 7)))))
  ;; sample.cljc:196
  (testing "sample.cljc:196" (eval (quote (matcho.core/assert ^#:matcho{:strict true} [0 1 1] (fibonacci 3))))))
(defn- rct-clr-sample-rct-block-10 []
  ;; sample.cljc:207
  (testing "sample.cljc:207" (eval (quote (clojure.test/is (= #{:c :b} (common-tags #{:c :b :a} #{:c :b :d}))))))
  ;; sample.cljc:210
  (testing "sample.cljc:210" (eval (quote (clojure.test/is (= #{:b :a} (set/union #{:a} #{:b})))))))
(defn- rct-clr-sample-rct-block-11 []
  ;; sample.cljc:225
  (testing "sample.cljc:225" (eval (quote (clojure.test/is (= "alice bob" (normalize-name "  Alice BOB  "))))))
  ;; sample.cljc:228
  (testing "sample.cljc:228" (eval (quote (clojure.test/is (= {:name "alice bob", :slug "alice-bob"} (make-user "  Alice BOB  ")))))))
(defn- rct-clr-sample-rct-block-12 []
  ;; sample.cljc:240
  (testing "sample.cljc:240" (eval (quote (try (validate-positive! -1) (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:data {:value -1}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-13 []
  ;; sample.cljc:254
  (testing "sample.cljc:254" (eval (quote (clojure.test/is (= {:host "localhost"} (parse-config {:host "localhost"}))))))
  ;; sample.cljc:258
  (testing "sample.cljc:258" (eval (quote (try (parse-config "oops") (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:data {:got System.String}} (rct-clr.sample-generated-test/error->map e)))))))
  ;; sample.cljc:262
  (testing "sample.cljc:262" (eval (quote (try (parse-config {:port 8080}) (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:data {:missing :host}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-14 []
  ;; sample.cljc:278
  (testing "sample.cljc:278" (eval (quote (clojure.test/is (= {"a" 1, "b" {"c" 2}} (stringify-keys {:b {:c 2}, :a 1})))))))
(defn- rct-clr-sample-rct-block-15 []
  ;; sample.cljc:289
  (testing "sample.cljc:289" (eval (quote (clojure.test/is (= true (truthy? 1))))))
  ;; sample.cljc:292
  (testing "sample.cljc:292" (eval (quote (clojure.test/is (= false (truthy? nil))))))
  ;; sample.cljc:295
  (testing "sample.cljc:295" (eval (quote (clojure.test/is (= false (truthy? false))))))
  ;; sample.cljc:298
  (testing "sample.cljc:298" (eval (quote (clojure.test/is (= true (nil? nil)))))))
(defn- rct-clr-sample-rct-block-16 []
  ;; sample.cljc:309
  (testing "sample.cljc:309" (eval (quote (clojure.test/is (= {} (ex-data (make-error "boom"))))))))
(deftest rct-clr-sample-rct
  (binding [*ns* (the-ns 'rct-clr.sample)]
    (rct-clr-sample-rct-block-0)
    (rct-clr-sample-rct-block-1)
    (rct-clr-sample-rct-block-2)
    (rct-clr-sample-rct-block-3)
    (rct-clr-sample-rct-block-4)
    (rct-clr-sample-rct-block-5)
    (rct-clr-sample-rct-block-6)
    (rct-clr-sample-rct-block-7)
    (rct-clr-sample-rct-block-8)
    (rct-clr-sample-rct-block-9)
    (rct-clr-sample-rct-block-10)
    (rct-clr-sample-rct-block-11)
    (rct-clr-sample-rct-block-12)
    (rct-clr-sample-rct-block-13)
    (rct-clr-sample-rct-block-14)
    (rct-clr-sample-rct-block-15)
    (rct-clr-sample-rct-block-16)))

;; rct-clr.sample-clr
(defn- rct-clr-sample-clr-rct-block-0 []
  ;; sample_clr.cljc:14
  (testing "sample_clr.cljc:14" (eval (quote (clojure.test/is (= "boom" (.Message (make-error "boom")))))))
  ;; sample_clr.cljc:18
  (testing "sample_clr.cljc:18" (eval (quote (clojure.test/is (= "boom" (.Message (make-error "boom")))))))
  ;; sample_clr.cljc:22
  (testing "sample_clr.cljc:22" (eval (quote (clojure.test/is (= :clr :clr)))))
  ;; sample_clr.cljc:26
  (testing "sample_clr.cljc:26" (eval (quote (clojure.test/is (= "error: boom" (str "error: " (.Message (make-error "boom")))))))))
(deftest rct-clr-sample-clr-rct
  (binding [*ns* (the-ns 'rct-clr.sample-clr)]
    (rct-clr-sample-clr-rct-block-0)))

