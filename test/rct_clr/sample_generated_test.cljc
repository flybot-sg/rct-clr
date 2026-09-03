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

(defn bind-repl-vars! [result]
  (set! *3 *2)
  (set! *2 *1)
  (set! *1 result)
  result)

;; rct-clr.sample
(defn- rct-clr-sample-rct-block-0 []
  ;; sample.cljc:17
  (testing "sample.cljc:17" (eval (quote (clojure.test/is (= 3 (rct-clr.sample-generated-test/bind-repl-vars! (add 1 2)))))))
  ;; sample.cljc:20
  (testing "sample.cljc:20" (eval (quote (clojure.test/is (= 0 (rct-clr.sample-generated-test/bind-repl-vars! (add -1 1)))))))
  ;; sample.cljc:24
  (eval (quote (rct-clr.sample-generated-test/bind-repl-vars! (def base-val 10))))
  ;; sample.cljc:27
  (testing "sample.cljc:27" (eval (quote (clojure.test/is (= 15 (rct-clr.sample-generated-test/bind-repl-vars! (add base-val 5)))))))
  ;; sample.cljc:31
  (eval (quote (rct-clr.sample-generated-test/bind-repl-vars! (def doubled (* base-val 2)))))
  ;; sample.cljc:33
  (testing "sample.cljc:33" (eval (quote (clojure.test/is (= 21 (rct-clr.sample-generated-test/bind-repl-vars! (add doubled 1))))))))
(defn- rct-clr-sample-rct-block-1 []
  ;; sample.cljc:48
  (testing "sample.cljc:48" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote (:h :c :s :d))) (rct-clr.sample-generated-test/bind-repl-vars! (suits)))))))
  ;; sample.cljc:52
  (testing "sample.cljc:52" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote (1 2 3))) (rct-clr.sample-generated-test/bind-repl-vars! (map inc (range 3))))))))
  ;; sample.cljc:56
  (testing "sample.cljc:56" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote foo)) (rct-clr.sample-generated-test/bind-repl-vars! (a-symbol)))))))
  ;; sample.cljc:60
  (testing "sample.cljc:60" (eval (quote (clojure.test/is (= (rct-clr.sample-generated-test/eval-expectation (quote (+ 2 2))) (rct-clr.sample-generated-test/bind-repl-vars! (count (suits)))))))))
(defn- rct-clr-sample-rct-block-2 []
  ;; sample.cljc:71
  (testing "sample.cljc:71" (eval (quote (clojure.test/is (= [1 2 3] (rct-clr.sample-generated-test/bind-repl-vars! (stack-push [1 2] 3)))))))
  ;; sample.cljc:75
  (testing "sample.cljc:75" (eval (quote (clojure.test/is (= 3 (rct-clr.sample-generated-test/bind-repl-vars! (count *1)))))))
  ;; sample.cljc:79
  (testing "sample.cljc:79" (eval (quote (clojure.test/is (= 3 (rct-clr.sample-generated-test/bind-repl-vars! (peek *2))))))))
(defn- rct-clr-sample-rct-block-3 []
  ;; sample.cljc:90
  (testing "sample.cljc:90" (eval (quote (clojure.test/is (= "Hello, World!" (rct-clr.sample-generated-test/bind-repl-vars! (greet "World")))))))
  ;; sample.cljc:93
  (testing "sample.cljc:93" (eval (quote (clojure.test/is (= "HELLO, TEST!" (rct-clr.sample-generated-test/bind-repl-vars! (str/upper-case (greet "test"))))))))
  ;; sample.cljc:96
  (testing "sample.cljc:96" (eval (quote (clojure.test/is (= "a, b, c" (rct-clr.sample-generated-test/bind-repl-vars! (str/join ", " ["a" "b" "c"])))))))
  ;; sample.cljc:99
  (testing "sample.cljc:99" (eval (quote (clojure.test/is (= true (rct-clr.sample-generated-test/bind-repl-vars! (str/blank? "")))))))
  ;; sample.cljc:102
  (testing "sample.cljc:102" (eval (quote (clojure.test/is (= false (rct-clr.sample-generated-test/bind-repl-vars! (str/blank? "x"))))))))
(defn- rct-clr-sample-rct-block-4 []
  ;; sample.cljc:114
  (testing "sample.cljc:114" (eval (quote (clojure.test/is (= :clr (rct-clr.sample-generated-test/bind-repl-vars! (platform))))))))
(defn- rct-clr-sample-rct-block-5 []
  ;; sample.cljc:125
  (testing "sample.cljc:125" (eval (quote (clojure.test/is (= :rct-clr.sample/sample (rct-clr.sample-generated-test/bind-repl-vars! (my-type))))))))
(defn- rct-clr-sample-rct-block-6 []
  ;; sample.cljc:138
  (testing "sample.cljc:138" (eval (quote (clojure.test/is (= {:clojure.string/join :string-alias, :clojure.set/union :set-alias, :clojure.walk/walk :walk-alias} (rct-clr.sample-generated-test/bind-repl-vars! (alias-kws))))))))
(defn- rct-clr-sample-rct-block-7 []
  ;; sample.cljc:155
  (testing "sample.cljc:155" (eval (quote (clojure.test/is (= {:id 1, :name "Alice", :settings {:theme "dark", :lang "en", :notifications true}, :tags #{:active :verified}} (rct-clr.sample-generated-test/bind-repl-vars! (user-profile 1 "Alice")))))))
  ;; sample.cljc:162
  (testing "sample.cljc:162" (eval (quote (clojure.test/is (= "dark" (rct-clr.sample-generated-test/bind-repl-vars! (get-in (user-profile 1 "Alice") [:settings :theme]))))))))
(defn- rct-clr-sample-rct-block-8 []
  ;; sample.cljc:178
  (testing "sample.cljc:178" (eval (quote (matcho.core/assert {:status 200, :body {:users []}} (rct-clr.sample-generated-test/bind-repl-vars! (api-response {:users []}))))))
  ;; sample.cljc:182
  (testing "sample.cljc:182" (eval (quote (matcho.core/assert {:body {:count 5}, :timing {:start 0}} (rct-clr.sample-generated-test/bind-repl-vars! (api-response {:count 5})))))))
(defn- rct-clr-sample-rct-block-9 []
  ;; sample.cljc:197
  (testing "sample.cljc:197" (eval (quote (matcho.core/assert [{:name "a"}] (rct-clr.sample-generated-test/bind-repl-vars! (scored-items))))))
  ;; sample.cljc:201
  (testing "sample.cljc:201" (eval (quote (matcho.core/assert ^#:matcho{:strict true} ["a" "b" "c"] (rct-clr.sample-generated-test/bind-repl-vars! (mapv :name (scored-items))))))))
(defn- rct-clr-sample-rct-block-10 []
  ;; sample.cljc:212
  (testing "sample.cljc:212" (eval (quote (matcho.core/assert [0 1 1 2] (rct-clr.sample-generated-test/bind-repl-vars! (fibonacci 7))))))
  ;; sample.cljc:215
  (testing "sample.cljc:215" (eval (quote (matcho.core/assert ^#:matcho{:strict true} [0 1 1] (rct-clr.sample-generated-test/bind-repl-vars! (fibonacci 3)))))))
(defn- rct-clr-sample-rct-block-11 []
  ;; sample.cljc:226
  (testing "sample.cljc:226" (eval (quote (clojure.test/is (= #{:c :b} (rct-clr.sample-generated-test/bind-repl-vars! (common-tags #{:c :b :a} #{:c :b :d})))))))
  ;; sample.cljc:229
  (testing "sample.cljc:229" (eval (quote (clojure.test/is (= #{:b :a} (rct-clr.sample-generated-test/bind-repl-vars! (set/union #{:a} #{:b}))))))))
(defn- rct-clr-sample-rct-block-12 []
  ;; sample.cljc:244
  (testing "sample.cljc:244" (eval (quote (clojure.test/is (= "alice bob" (rct-clr.sample-generated-test/bind-repl-vars! (normalize-name "  Alice BOB  ")))))))
  ;; sample.cljc:247
  (testing "sample.cljc:247" (eval (quote (clojure.test/is (= {:name "alice bob", :slug "alice-bob"} (rct-clr.sample-generated-test/bind-repl-vars! (make-user "  Alice BOB  "))))))))
(defn- rct-clr-sample-rct-block-13 []
  ;; sample.cljc:259
  (testing "sample.cljc:259" (eval (quote (try (validate-positive! -1) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:data {:value -1}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-14 []
  ;; sample.cljc:273
  (testing "sample.cljc:273" (eval (quote (clojure.test/is (= {:host "localhost"} (rct-clr.sample-generated-test/bind-repl-vars! (parse-config {:host "localhost"})))))))
  ;; sample.cljc:277
  (testing "sample.cljc:277" (eval (quote (try (parse-config "oops") (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:data {:got System.String}} (rct-clr.sample-generated-test/error->map e)))))))
  ;; sample.cljc:281
  (testing "sample.cljc:281" (eval (quote (try (parse-config {:port 8080}) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:data {:missing :host}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-15 []
  ;; sample.cljc:297
  (testing "sample.cljc:297" (eval (quote (clojure.test/is (= {"a" 1, "b" {"c" 2}} (rct-clr.sample-generated-test/bind-repl-vars! (stringify-keys {:b {:c 2}, :a 1}))))))))
(defn- rct-clr-sample-rct-block-16 []
  ;; sample.cljc:308
  (testing "sample.cljc:308" (eval (quote (clojure.test/is (= true (rct-clr.sample-generated-test/bind-repl-vars! (truthy? 1)))))))
  ;; sample.cljc:311
  (testing "sample.cljc:311" (eval (quote (clojure.test/is (= false (rct-clr.sample-generated-test/bind-repl-vars! (truthy? nil)))))))
  ;; sample.cljc:314
  (testing "sample.cljc:314" (eval (quote (clojure.test/is (= false (rct-clr.sample-generated-test/bind-repl-vars! (truthy? false)))))))
  ;; sample.cljc:317
  (testing "sample.cljc:317" (eval (quote (clojure.test/is (= true (rct-clr.sample-generated-test/bind-repl-vars! (nil? nil))))))))
(defn- rct-clr-sample-rct-block-17 []
  ;; sample.cljc:328
  (testing "sample.cljc:328" (eval (quote (clojure.test/is (= {} (rct-clr.sample-generated-test/bind-repl-vars! (ex-data (make-error "boom")))))))))
(deftest rct-clr-sample-rct
  (binding [*ns* (the-ns 'rct-clr.sample)
            *1 nil, *2 nil, *3 nil, *e nil]
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
    (rct-clr-sample-rct-block-16)
    (rct-clr-sample-rct-block-17)))

;; rct-clr.sample-clr
(defn- rct-clr-sample-clr-rct-block-0 []
  ;; sample_clr.cljc:14
  (testing "sample_clr.cljc:14" (eval (quote (clojure.test/is (= "boom" (rct-clr.sample-generated-test/bind-repl-vars! (.Message (make-error "boom"))))))))
  ;; sample_clr.cljc:18
  (testing "sample_clr.cljc:18" (eval (quote (clojure.test/is (= "boom" (rct-clr.sample-generated-test/bind-repl-vars! (.Message (make-error "boom"))))))))
  ;; sample_clr.cljc:22
  (testing "sample_clr.cljc:22" (eval (quote (clojure.test/is (= :clr (rct-clr.sample-generated-test/bind-repl-vars! :clr))))))
  ;; sample_clr.cljc:26
  (testing "sample_clr.cljc:26" (eval (quote (clojure.test/is (= "error: boom" (rct-clr.sample-generated-test/bind-repl-vars! (str "error: " (.Message (make-error "boom"))))))))))
(deftest rct-clr-sample-clr-rct
  (binding [*ns* (the-ns 'rct-clr.sample-clr)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-sample-clr-rct-block-0)))

