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
  ;; sample.cljc:44
  (testing "sample.cljc:44" (eval (quote (clojure.test/is (= "Hello, World!" (greet "World"))))))
  ;; sample.cljc:47
  (testing "sample.cljc:47" (eval (quote (clojure.test/is (= "HELLO, TEST!" (str/upper-case (greet "test")))))))
  ;; sample.cljc:50
  (testing "sample.cljc:50" (eval (quote (clojure.test/is (= "a, b, c" (str/join ", " ["a" "b" "c"]))))))
  ;; sample.cljc:53
  (testing "sample.cljc:53" (eval (quote (clojure.test/is (= true (str/blank? ""))))))
  ;; sample.cljc:56
  (testing "sample.cljc:56" (eval (quote (clojure.test/is (= false (str/blank? "x")))))))
(defn- rct-clr-sample-rct-block-2 []
  ;; sample.cljc:68
  (testing "sample.cljc:68" (eval (quote (clojure.test/is (= :clr (platform)))))))
(defn- rct-clr-sample-rct-block-3 []
  ;; sample.cljc:79
  (testing "sample.cljc:79" (eval (quote (clojure.test/is (= :rct-clr.sample/sample (my-type)))))))
(defn- rct-clr-sample-rct-block-4 []
  ;; sample.cljc:92
  (testing "sample.cljc:92" (eval (quote (clojure.test/is (= {:clojure.string/join :string-alias, :clojure.set/union :set-alias, :clojure.walk/walk :walk-alias} (alias-kws)))))))
(defn- rct-clr-sample-rct-block-5 []
  ;; sample.cljc:109
  (testing "sample.cljc:109" (eval (quote (clojure.test/is (= {:id 1, :name "Alice", :settings {:theme "dark", :lang "en", :notifications true}, :tags #{:active :verified}} (user-profile 1 "Alice"))))))
  ;; sample.cljc:116
  (testing "sample.cljc:116" (eval (quote (clojure.test/is (= "dark" (get-in (user-profile 1 "Alice") [:settings :theme])))))))
(defn- rct-clr-sample-rct-block-6 []
  ;; sample.cljc:132
  (testing "sample.cljc:132" (eval (quote (matcho.core/assert {:status 200, :body {:users []}} (api-response {:users []})))))
  ;; sample.cljc:136
  (testing "sample.cljc:136" (eval (quote (matcho.core/assert {:body {:count 5}, :timing {:start 0}} (api-response {:count 5}))))))
(defn- rct-clr-sample-rct-block-7 []
  ;; sample.cljc:151
  (testing "sample.cljc:151" (eval (quote (matcho.core/assert [{:name "a"}] (scored-items)))))
  ;; sample.cljc:155
  (testing "sample.cljc:155" (eval (quote (matcho.core/assert ^#:matcho{:strict true} ["a" "b" "c"] (mapv :name (scored-items)))))))
(defn- rct-clr-sample-rct-block-8 []
  ;; sample.cljc:166
  (testing "sample.cljc:166" (eval (quote (matcho.core/assert [0 1 1 2] (fibonacci 7)))))
  ;; sample.cljc:169
  (testing "sample.cljc:169" (eval (quote (matcho.core/assert ^#:matcho{:strict true} [0 1 1] (fibonacci 3))))))
(defn- rct-clr-sample-rct-block-9 []
  ;; sample.cljc:180
  (testing "sample.cljc:180" (eval (quote (clojure.test/is (= #{:c :b} (common-tags #{:c :b :a} #{:c :b :d}))))))
  ;; sample.cljc:183
  (testing "sample.cljc:183" (eval (quote (clojure.test/is (= #{:b :a} (set/union #{:a} #{:b})))))))
(defn- rct-clr-sample-rct-block-10 []
  ;; sample.cljc:198
  (testing "sample.cljc:198" (eval (quote (clojure.test/is (= "alice bob" (normalize-name "  Alice BOB  "))))))
  ;; sample.cljc:201
  (testing "sample.cljc:201" (eval (quote (clojure.test/is (= {:name "alice bob", :slug "alice-bob"} (make-user "  Alice BOB  ")))))))
(defn- rct-clr-sample-rct-block-11 []
  ;; sample.cljc:213
  (testing "sample.cljc:213" (eval (quote (try (validate-positive! -1) (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:data {:value -1}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-12 []
  ;; sample.cljc:227
  (testing "sample.cljc:227" (eval (quote (clojure.test/is (= {:host "localhost"} (parse-config {:host "localhost"}))))))
  ;; sample.cljc:231
  (testing "sample.cljc:231" (eval (quote (try (parse-config "oops") (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:data {:got System.String}} (rct-clr.sample-generated-test/error->map e)))))))
  ;; sample.cljc:235
  (testing "sample.cljc:235" (eval (quote (try (parse-config {:port 8080}) (clojure.test/is false "Expected exception") (catch System.Exception e (matcho.core/assert #:error{:data {:missing :host}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-13 []
  ;; sample.cljc:251
  (testing "sample.cljc:251" (eval (quote (clojure.test/is (= {"a" 1, "b" {"c" 2}} (stringify-keys {:b {:c 2}, :a 1})))))))
(defn- rct-clr-sample-rct-block-14 []
  ;; sample.cljc:262
  (testing "sample.cljc:262" (eval (quote (clojure.test/is (= true (truthy? 1))))))
  ;; sample.cljc:265
  (testing "sample.cljc:265" (eval (quote (clojure.test/is (= false (truthy? nil))))))
  ;; sample.cljc:268
  (testing "sample.cljc:268" (eval (quote (clojure.test/is (= false (truthy? false))))))
  ;; sample.cljc:271
  (testing "sample.cljc:271" (eval (quote (clojure.test/is (= true (nil? nil)))))))
(defn- rct-clr-sample-rct-block-15 []
  ;; sample.cljc:282
  (testing "sample.cljc:282" (eval (quote (clojure.test/is (= {} (ex-data (make-error "boom"))))))))
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
    (rct-clr-sample-rct-block-15)))

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

