(ns rct-clr.sample-clr
  "Blocks the JVM RCT runner cannot run, generated for the CLR only.
  A reader conditional in a test expression: rewrite-clj wraps #? as
  (read-string \"#?(...)\"). Clojure's eval rejects that, so the generator
  resolves it via `resolve-reader-conditionals`.
  A data seq in a => expectation: the runner evaluates it. Calling a map with
  one argument looks a key up, so the comparison gets nil.")

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  ;; CLR interop works without reader conditional in a CLR-only file
  (.Message (make-error "boom")) ;=> "boom"

  ;; reader conditional in test expression, interop method dispatch
  #?(:cljr (.Message (make-error "boom"))) ;=> "boom"

  ;; simple reader conditional in test expression
  #?(:clj :jvm :cljr :clr) ;=> #?(:clj :jvm :cljr :clr)

  ;; reader conditional nested inside a larger expression
  (str "error: " #?(:clj (.getMessage (make-error "boom")) :cljr (.Message (make-error "boom"))))
  ;=> "error: boom"
  )

(defn card-pair []
  (list {:suit :h} {:suit :c}))

(defn two-suits []
  (list :h :c))

(defn nested-pair []
  {:cards (list {:suit :h} {:suit :c})})

^:rct/test
(comment
  ;; a map in head position
  (card-pair) ;=> ({:suit :h} {:suit :c})

  ;; a keyword in head position, one argument
  (two-suits) ;=> (:h :c)

  ;; the same seq one level down, as a map value
  (nested-pair) ;=> {:cards ({:suit :h} {:suit :c})}
  )
