(ns rct-clr.sample-jvm
  "JVM-only counterpart to examples_clr/sample_clr.cljc.
  Tests JVM-specific interop that the RCT runner can handle directly.
  Not scanned by the generator (would emit JVM interop in CLR output).")

(defn make-error [msg]
  (ex-info msg {}))

^:rct/test
(comment
  ;; JVM interop — mirrors the CLR test in sample_clr.cljc
  (.getMessage (make-error "boom"))
  ;=> "boom"

  ;; JVM interop nested inside a larger expression — mirrors CLR test
  (str "error: " (.getMessage (make-error "boom")))
  ;=> "error: boom"
  )
