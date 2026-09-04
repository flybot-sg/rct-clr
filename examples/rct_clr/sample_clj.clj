(ns rct-clr.sample-clj
  "A .clj sample, scanned like a .cljc.
  Portable code only, since the generated assertions run on the CLR.")

(defn total [xs]
  (reduce + 0 xs))

^:rct/test
(comment
  (total [1 2 3]) ;=> 6

  (total []) ;=> 0
  )
