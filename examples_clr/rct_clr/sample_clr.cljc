(ns rct-clr.sample-clr
  "Reader conditional in test expression — CLR-only (not RCT-runnable).
  The RCT runner can't eval reader conditionals in test expressions because
  rewrite-clj wraps #? as (read-string \"#?(...)\") and Clojure's eval rejects it.
  The generator resolves these for CLR via `resolve-reader-conditionals`."
  (:require [clojure.string :as str]))

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  ;; CLR interop works without reader conditional in a CLR-only file
  (.Message (make-error "boom"))
  ;=> "boom"

  ;; reader conditional in test expression — interop method dispatch
  #?(:cljr (.Message (make-error "boom")))
  ;=> "boom"

  ;; simple reader conditional in test expression
  #?(:clj :jvm :cljr :clr)
  ;=> #?(:clj :jvm :cljr :clr)
  )
