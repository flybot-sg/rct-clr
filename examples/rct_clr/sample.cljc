(ns rct-clr.sample
  "Comprehensive sample exercising all RCT assertion types and edge cases for
  CLR golden file generation. No JVM-only dependencies — the generated output
  is runnable on CLR with only clojure.test and matcho.core."
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [clojure.walk :as walk]))

;; --- Basic exact match (=>) and side-effects ---

(defn add [a b]
  (+ a b))

^:rct/test
(comment
  ;; => exact match
  (add 1 2)
  ;=> 3

  (add -1 1)
  ;=> 0

  ;; side-effect (nil expectation type) with literal
  (def base-val 10)

  ;; reference a prior def
  (add base-val 5)
  ;=> 15

  ;; side-effect with computed value
  (def doubled (* base-val 2))

  (add doubled 1)
  ;=> 21
  )

;; --- String operations and alias-qualified calls ---

(defn greet [name]
  (str "Hello, " name "!"))

^:rct/test
(comment
  (greet "World")
  ;=> "Hello, World!"

  (str/upper-case (greet "test"))
  ;=> "HELLO, TEST!"

  (str/join ", " ["a" "b" "c"])
  ;=> "a, b, c"

  (str/blank? "")
  ;=> true

  (str/blank? "x")
  ;=> false
  )

;; --- Platform via reader conditional ---

(defn platform []
  #?(:clj :jvm :cljr :clr))

^:rct/test
(comment
  ;; reader conditional in expectation
  (platform)
  ;=> #?(:clj :jvm :cljr :clr)
  )

;; --- Namespace-qualified keywords ---

(defn my-type []
  ::sample)

^:rct/test
(comment
  (my-type)
  ;=> ::sample
  )

;; --- Alias-qualified keywords (multiple aliases) ---

(defn alias-kws []
  {::str/join  :string-alias
   ::set/union :set-alias
   ::walk/walk :walk-alias})

^:rct/test
(comment
  (alias-kws)
  ;=> {::str/join :string-alias
  ;;   ::set/union :set-alias
  ;;   ::walk/walk :walk-alias}
  )

;; --- Nested data structures ---

(defn user-profile [id name]
  {:id id
   :name name
   :settings {:theme "dark" :lang "en" :notifications true}
   :tags #{:active :verified}})

^:rct/test
(comment
  ;; exact match on nested structure
  (user-profile 1 "Alice")
  ;=> {:id 1
  ;;   :name "Alice"
  ;;   :settings {:theme "dark" :lang "en" :notifications true}
  ;;   :tags #{:active :verified}}

  ;; nested access
  (get-in (user-profile 1 "Alice") [:settings :theme])
  ;=> "dark"
  )

;; --- Pattern matching (=>>) with nested maps ---

(defn api-response [data]
  {:status 200
   :body data
   :headers {"content-type" "application/json"
             "x-request-id" "abc-123"}
   :timing {:start 0 :end 42}})

^:rct/test
(comment
  ;; =>> ignores extra keys at top level
  (api-response {:users []})
  ;=>> {:status 200 :body {:users []}}

  ;; =>> with nested pattern
  (api-response {:count 5})
  ;=>> {:body {:count 5}
  ;;    :timing {:start 0}}
  )

;; --- =>> with collections ---

(defn scored-items []
  [{:name "a" :score 10}
   {:name "b" :score 20}
   {:name "c" :score 30}])

^:rct/test
(comment
  ;; =>> with ellipsis on vector of maps
  (scored-items)
  ;=>> [{:name "a"} ...]

  ;; =>> with ^:matcho/strict
  (mapv :name (scored-items))
  ;=>> ^:matcho/strict ["a" "b" "c"]
  )

;; --- =>> with ellipsis on simple vectors ---

(defn fibonacci [n]
  (vec (take n (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))))

^:rct/test
(comment
  (fibonacci 7)
  ;=>> [0 1 1 2 ...]

  (fibonacci 3)
  ;=>> ^:matcho/strict [0 1 1]
  )

;; --- Set operations (cross-platform library) ---

(defn common-tags [a b]
  (set/intersection a b))

^:rct/test
(comment
  (common-tags #{:a :b :c} #{:b :c :d})
  ;=> #{:b :c}

  (set/union #{:a} #{:b})
  ;=> #{:a :b}
  )

;; --- Function composition ---

(defn normalize-name [s]
  (-> s str/trim str/lower-case))

(defn make-user [raw-name]
  {:name (normalize-name raw-name)
   :slug (str/replace (normalize-name raw-name) #"\s+" "-")})

^:rct/test
(comment
  (normalize-name "  Alice BOB  ")
  ;=> "alice bob"

  (make-user "  Alice BOB  ")
  ;=> {:name "alice bob" :slug "alice-bob"}
  )

;; --- Exception handling (throws=>>) ---

(defn validate-positive! [x]
  (when-not (pos? x)
    (throw (ex-info "must be positive" {:value x}))))

^:rct/test
(comment
  (validate-positive! -1)
  ;throws=>> {:error/data {:value -1}}
  )

(defn parse-config [m]
  (when-not (map? m)
    (throw (ex-info "expected a map" {:got (type m)})))
  (when-not (contains? m :host)
    (throw (ex-info "missing required key" {:missing :host})))
  m)

^:rct/test
(comment
  ;; valid input passes through
  (parse-config {:host "localhost"})
  ;=> {:host "localhost"}

  ;; not a map
  (parse-config "oops")
  ;throws=>> {:error/data {:got #?(:clj java.lang.String :cljr System.String)}}

  ;; missing key
  (parse-config {:port 8080})
  ;throws=>> {:error/data {:missing :host}}
  )

;; --- walk (cross-platform library, complex transformation) ---

(defn stringify-keys [m]
  (walk/postwalk
   (fn [x]
     (if (keyword? x)
       (name x)
       x))
   m))

^:rct/test
(comment
  (stringify-keys {:a 1 :b {:c 2}})
  ;=> {"a" 1 "b" {"c" 2}}
  )

;; --- Boolean and nil edge cases ---

(defn truthy? [x]
  (if x true false))

^:rct/test
(comment
  (truthy? 1)
  ;=> true

  (truthy? nil)
  ;=> false

  (truthy? false)
  ;=> false

  (nil? nil)
  ;=> true
  )

;; --- Reader conditional in test expression ---

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  (ex-data (make-error "boom"))
  ;=> {}
  )
