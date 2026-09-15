(ns ^:clr-only rct-clr.sample-generated-test
  "Auto-generated from ^:rct/test blocks. Do not edit manually."
  (:require [clojure.test :refer [deftest testing]]
            [matcho.core]
            [rct-clr.sample]
            [rct-clr.sample-clj]
            [rct-clr.sample-clr]))

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

;; rct-clr.sample
(defn- rct-clr-sample-rct-block-0 []
  ;; sample.cljc:17
  (testing "sample.cljc:17" (rct-clr.sample-generated-test/run-form! "sample.cljc:17" (quote (clojure.test/is (= 3 (rct-clr.sample-generated-test/bind-repl-vars! (add 1 2)))))))
  ;; sample.cljc:19
  (testing "sample.cljc:19" (rct-clr.sample-generated-test/run-form! "sample.cljc:19" (quote (clojure.test/is (= 0 (rct-clr.sample-generated-test/bind-repl-vars! (add -1 1)))))))
  ;; sample.cljc:22
  (rct-clr.sample-generated-test/run-form! "sample.cljc:22" (quote (rct-clr.sample-generated-test/bind-repl-vars! (def base-val 10))))
  ;; sample.cljc:25
  (testing "sample.cljc:25" (rct-clr.sample-generated-test/run-form! "sample.cljc:25" (quote (clojure.test/is (= 15 (rct-clr.sample-generated-test/bind-repl-vars! (add base-val 5)))))))
  ;; sample.cljc:28
  (rct-clr.sample-generated-test/run-form! "sample.cljc:28" (quote (rct-clr.sample-generated-test/bind-repl-vars! (def doubled (* base-val 2)))))
  ;; sample.cljc:30
  (testing "sample.cljc:30" (rct-clr.sample-generated-test/run-form! "sample.cljc:30" (quote (clojure.test/is (= 21 (rct-clr.sample-generated-test/bind-repl-vars! (add doubled 1))))))))
(defn- rct-clr-sample-rct-block-1 []
  ;; sample.cljc:44
  (testing "sample.cljc:44" (rct-clr.sample-generated-test/run-form! "sample.cljc:44" (quote (clojure.test/is (= (quote (:a)) (rct-clr.sample-generated-test/bind-repl-vars! (one-tag)))))))
  ;; sample.cljc:47
  (testing "sample.cljc:47" (rct-clr.sample-generated-test/run-form! "sample.cljc:47" (quote (clojure.test/is (= [1] (rct-clr.sample-generated-test/bind-repl-vars! (map inc (range 1))))))))
  ;; sample.cljc:50
  (testing "sample.cljc:50" (rct-clr.sample-generated-test/run-form! "sample.cljc:50" (quote (clojure.test/is (= (quote foo) (rct-clr.sample-generated-test/bind-repl-vars! (a-symbol)))))))
  ;; sample.cljc:53
  (testing "sample.cljc:53" (rct-clr.sample-generated-test/run-form! "sample.cljc:53" (quote (clojure.test/is (= (inc 0) (rct-clr.sample-generated-test/bind-repl-vars! (count (one-tag)))))))))
(defn- rct-clr-sample-rct-block-2 []
  ;; sample.cljc:63
  (testing "sample.cljc:63" (rct-clr.sample-generated-test/run-form! "sample.cljc:63" (quote (clojure.test/is (= [1 2 3] (rct-clr.sample-generated-test/bind-repl-vars! (stack-push [1 2] 3)))))))
  ;; sample.cljc:66
  (testing "sample.cljc:66" (rct-clr.sample-generated-test/run-form! "sample.cljc:66" (quote (clojure.test/is (= 3 (rct-clr.sample-generated-test/bind-repl-vars! (count *1)))))))
  ;; sample.cljc:69
  (testing "sample.cljc:69" (rct-clr.sample-generated-test/run-form! "sample.cljc:69" (quote (clojure.test/is (= 3 (rct-clr.sample-generated-test/bind-repl-vars! (peek *2))))))))
(defn- rct-clr-sample-rct-block-3 []
  ;; sample.cljc:79
  (testing "sample.cljc:79" (rct-clr.sample-generated-test/run-form! "sample.cljc:79" (quote (clojure.test/is (= "Hello, World!" (rct-clr.sample-generated-test/bind-repl-vars! (greet "World")))))))
  ;; sample.cljc:81
  (testing "sample.cljc:81" (rct-clr.sample-generated-test/run-form! "sample.cljc:81" (quote (clojure.test/is (= "HELLO, TEST!" (rct-clr.sample-generated-test/bind-repl-vars! (str/upper-case (greet "test"))))))))
  ;; sample.cljc:83
  (testing "sample.cljc:83" (rct-clr.sample-generated-test/run-form! "sample.cljc:83" (quote (clojure.test/is (= "a, b, c" (rct-clr.sample-generated-test/bind-repl-vars! (str/join ", " ["a" "b" "c"])))))))
  ;; sample.cljc:85
  (testing "sample.cljc:85" (rct-clr.sample-generated-test/run-form! "sample.cljc:85" (quote (clojure.test/is (= true (rct-clr.sample-generated-test/bind-repl-vars! (str/blank? "")))))))
  ;; sample.cljc:87
  (testing "sample.cljc:87" (rct-clr.sample-generated-test/run-form! "sample.cljc:87" (quote (clojure.test/is (= false (rct-clr.sample-generated-test/bind-repl-vars! (str/blank? "x"))))))))
(defn- rct-clr-sample-rct-block-4 []
  ;; sample.cljc:98
  (testing "sample.cljc:98" (rct-clr.sample-generated-test/run-form! "sample.cljc:98" (quote (clojure.test/is (= :clr (rct-clr.sample-generated-test/bind-repl-vars! (platform))))))))
(defn- rct-clr-sample-rct-block-5 []
  ;; sample.cljc:108
  (testing "sample.cljc:108" (rct-clr.sample-generated-test/run-form! "sample.cljc:108" (quote (clojure.test/is (= :rct-clr.sample/sample (rct-clr.sample-generated-test/bind-repl-vars! (my-type))))))))
(defn- rct-clr-sample-rct-block-6 []
  ;; sample.cljc:120
  (testing "sample.cljc:120" (rct-clr.sample-generated-test/run-form! "sample.cljc:120" (quote (clojure.test/is (= {:clojure.string/join :string-alias, :clojure.set/union :set-alias, :clojure.walk/walk :walk-alias} (rct-clr.sample-generated-test/bind-repl-vars! (alias-kws)))))))
  ;; sample.cljc:127
  (testing "sample.cljc:127" (rct-clr.sample-generated-test/run-form! "sample.cljc:127" (quote (clojure.test/is (= "clojure.string" (rct-clr.sample-generated-test/bind-repl-vars! (namespace :clojure.string/join))))))))
(defn- rct-clr-sample-rct-block-7 []
  ;; sample.cljc:141
  (testing "sample.cljc:141" (rct-clr.sample-generated-test/run-form! "sample.cljc:141" (quote (clojure.test/is (= {:id 1, :name "Alice", :settings {:theme "dark", :lang "en", :notifications true}, :tags #{:active :verified}} (rct-clr.sample-generated-test/bind-repl-vars! (user-profile 1 "Alice")))))))
  ;; sample.cljc:149
  (testing "sample.cljc:149" (rct-clr.sample-generated-test/run-form! "sample.cljc:149" (quote (clojure.test/is (= "dark" (rct-clr.sample-generated-test/bind-repl-vars! (get-in (user-profile 1 "Alice") [:settings :theme]))))))))
(defn- rct-clr-sample-rct-block-8 []
  ;; sample.cljc:164
  (testing "sample.cljc:164" (rct-clr.sample-generated-test/run-form! "sample.cljc:164" (quote (matcho.core/assert {:status 200, :body {:users []}} (rct-clr.sample-generated-test/bind-repl-vars! (api-response {:users []}))))))
  ;; sample.cljc:167
  (testing "sample.cljc:167" (rct-clr.sample-generated-test/run-form! "sample.cljc:167" (quote (matcho.core/assert {:body {:count 5}, :timing {:start 0}} (rct-clr.sample-generated-test/bind-repl-vars! (api-response {:count 5})))))))
(defn- rct-clr-sample-rct-block-9 []
  ;; sample.cljc:183
  (testing "sample.cljc:183" (rct-clr.sample-generated-test/run-form! "sample.cljc:183" (quote (matcho.core/assert [{:name "a"}] (rct-clr.sample-generated-test/bind-repl-vars! (scored-items))))))
  ;; sample.cljc:186
  (testing "sample.cljc:186" (rct-clr.sample-generated-test/run-form! "sample.cljc:186" (quote (matcho.core/assert ^#:matcho{:strict true} ["a" "b" "c"] (rct-clr.sample-generated-test/bind-repl-vars! (mapv :name (scored-items))))))))
(defn- rct-clr-sample-rct-block-10 []
  ;; sample.cljc:196
  (testing "sample.cljc:196" (rct-clr.sample-generated-test/run-form! "sample.cljc:196" (quote (matcho.core/assert [0 1 1 2] (rct-clr.sample-generated-test/bind-repl-vars! (fibonacci 7))))))
  ;; sample.cljc:198
  (testing "sample.cljc:198" (rct-clr.sample-generated-test/run-form! "sample.cljc:198" (quote (matcho.core/assert ^#:matcho{:strict true} [0 1 1] (rct-clr.sample-generated-test/bind-repl-vars! (fibonacci 3)))))))
(defn- rct-clr-sample-rct-block-11 []
  ;; sample.cljc:208
  (testing "sample.cljc:208" (rct-clr.sample-generated-test/run-form! "sample.cljc:208" (quote (clojure.test/is (= #{:c :b} (rct-clr.sample-generated-test/bind-repl-vars! (common-tags #{:c :b :a} #{:c :b :d})))))))
  ;; sample.cljc:210
  (testing "sample.cljc:210" (rct-clr.sample-generated-test/run-form! "sample.cljc:210" (quote (clojure.test/is (= #{:b :a} (rct-clr.sample-generated-test/bind-repl-vars! (set/union #{:a} #{:b}))))))))
(defn- rct-clr-sample-rct-block-12 []
  ;; sample.cljc:224
  (testing "sample.cljc:224" (rct-clr.sample-generated-test/run-form! "sample.cljc:224" (quote (clojure.test/is (= "alice bob" (rct-clr.sample-generated-test/bind-repl-vars! (normalize-name "  Alice BOB  ")))))))
  ;; sample.cljc:226
  (testing "sample.cljc:226" (rct-clr.sample-generated-test/run-form! "sample.cljc:226" (quote (clojure.test/is (= {:name "alice bob", :slug "alice-bob"} (rct-clr.sample-generated-test/bind-repl-vars! (make-user "  Alice BOB  "))))))))
(defn- rct-clr-sample-rct-block-13 []
  ;; sample.cljc:237
  (testing "sample.cljc:237" (rct-clr.sample-generated-test/run-form! "sample.cljc:237" (quote (try (validate-positive! -1) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:data {:value -1}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-14 []
  ;; sample.cljc:250
  (testing "sample.cljc:250" (rct-clr.sample-generated-test/run-form! "sample.cljc:250" (quote (clojure.test/is (= {:host "localhost"} (rct-clr.sample-generated-test/bind-repl-vars! (parse-config {:host "localhost"})))))))
  ;; sample.cljc:253
  (testing "sample.cljc:253" (rct-clr.sample-generated-test/run-form! "sample.cljc:253" (quote (try (parse-config "oops") (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:data {:got System.String}} (rct-clr.sample-generated-test/error->map e)))))))
  ;; sample.cljc:257
  (testing "sample.cljc:257" (rct-clr.sample-generated-test/run-form! "sample.cljc:257" (quote (try (parse-config {:port 8080}) (clojure.test/is false "Expected exception") (catch System.Exception e (set! *e e) (matcho.core/assert #:error{:data {:missing :host}} (rct-clr.sample-generated-test/error->map e))))))))
(defn- rct-clr-sample-rct-block-15 []
  ;; sample.cljc:272
  (testing "sample.cljc:272" (rct-clr.sample-generated-test/run-form! "sample.cljc:272" (quote (clojure.test/is (= {"a" 1, "b" {"c" 2}} (rct-clr.sample-generated-test/bind-repl-vars! (stringify-keys {:a 1, :b {:c 2}}))))))))
(defn- rct-clr-sample-rct-block-16 []
  ;; sample.cljc:282
  (testing "sample.cljc:282" (rct-clr.sample-generated-test/run-form! "sample.cljc:282" (quote (clojure.test/is (= true (rct-clr.sample-generated-test/bind-repl-vars! (truthy? 1)))))))
  ;; sample.cljc:284
  (testing "sample.cljc:284" (rct-clr.sample-generated-test/run-form! "sample.cljc:284" (quote (clojure.test/is (= false (rct-clr.sample-generated-test/bind-repl-vars! (truthy? nil)))))))
  ;; sample.cljc:286
  (testing "sample.cljc:286" (rct-clr.sample-generated-test/run-form! "sample.cljc:286" (quote (clojure.test/is (= false (rct-clr.sample-generated-test/bind-repl-vars! (truthy? false)))))))
  ;; sample.cljc:288
  (testing "sample.cljc:288" (rct-clr.sample-generated-test/run-form! "sample.cljc:288" (quote (clojure.test/is (= true (rct-clr.sample-generated-test/bind-repl-vars! (nil? nil))))))))
(defn- rct-clr-sample-rct-block-17 []
  ;; sample.cljc:298
  (testing "sample.cljc:298" (rct-clr.sample-generated-test/run-form! "sample.cljc:298" (quote (clojure.test/is (= {} (rct-clr.sample-generated-test/bind-repl-vars! (ex-data (make-error "boom")))))))))
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

;; rct-clr.sample-clj
(defn- rct-clr-sample-clj-rct-block-0 []
  ;; sample_clj.clj:10
  (testing "sample_clj.clj:10" (rct-clr.sample-generated-test/run-form! "sample_clj.clj:10" (quote (clojure.test/is (= 6 (rct-clr.sample-generated-test/bind-repl-vars! (total [1 2 3]))))))))
(deftest rct-clr-sample-clj-rct
  (binding [*ns* (the-ns 'rct-clr.sample-clj)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-sample-clj-rct-block-0)))

;; rct-clr.sample-clr
(defn- rct-clr-sample-clr-rct-block-0 []
  ;; sample_clr.cljc:13
  (testing "sample_clr.cljc:13" (rct-clr.sample-generated-test/run-form! "sample_clr.cljc:13" (quote (clojure.test/is (= "boom" (rct-clr.sample-generated-test/bind-repl-vars! (.Message (make-error "boom"))))))))
  ;; sample_clr.cljc:16
  (testing "sample_clr.cljc:16" (rct-clr.sample-generated-test/run-form! "sample_clr.cljc:16" (quote (clojure.test/is (= "boom" (rct-clr.sample-generated-test/bind-repl-vars! (.Message (make-error "boom"))))))))
  ;; sample_clr.cljc:19
  (testing "sample_clr.cljc:19" (rct-clr.sample-generated-test/run-form! "sample_clr.cljc:19" (quote (clojure.test/is (= :clr (rct-clr.sample-generated-test/bind-repl-vars! :clr))))))
  ;; sample_clr.cljc:22
  (testing "sample_clr.cljc:22" (rct-clr.sample-generated-test/run-form! "sample_clr.cljc:22" (quote (clojure.test/is (= "error: boom" (rct-clr.sample-generated-test/bind-repl-vars! (str "error: " (.Message (make-error "boom"))))))))))
(deftest rct-clr-sample-clr-rct
  (binding [*ns* (the-ns 'rct-clr.sample-clr)
            *1 nil, *2 nil, *3 nil, *e nil]
    (rct-clr-sample-clr-rct-block-0)))

