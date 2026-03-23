(ns user
  (:require [com.mjdowney.rich-comment-tests :as rct]
            [kaocha.repl :as k]))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn rct-ns
  []
  (rct/run-ns-tests! *ns*))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn kr-test [test-to-run]
  (k/run test-to-run))
