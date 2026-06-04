(ns dotnet
  "Dotnet related tasks to be called by `nostrand`.
  Nostrand uses the `magic` compiler.

  ## Motivation

  This namespace provides convenient functions to:
  - compile the prod namespaces to .net assemblies
  - run the tests in the CLR"
  (:require [nostrand.tasks :as tasks]))

(def prod-namespaces
  '[rct-clr.sample
    rct-clr.sample-clr])

(def test-namespaces
  ;; rct-clr.rc-test excluded: RCT is JVM-only, so generate RCT tests as deftest
  ;; rct-clr.rct-generated-test excluded: requires rct-clr.gen which has JVM-only deps
  '[rct-clr.sample-generated-test])

(defn build
  "Compiles the project to dlls.
  nos dotnet/build"
  []
  (tasks/compile-project :namespaces prod-namespaces :aliases [:test] :clean? true))

(defn run-tests
  "Run all the tests on the CLR.
  nos dotnet/run-tests"
  []
  (tasks/run-clojure-tests
   :namespaces (concat prod-namespaces test-namespaces)
   :aliases    [:clr :test]
   ;; run-clojure-tests sweeps ALL loaded namespaces (clojure.*, magic.*,
   ;; nostrand.*, matcho.core, ...), not just :namespaces. The :re filter
   ;; scopes the run to this project so dependency suites can't sneak in.
   ;; re-matches semantics: must match the full ns name, hence trailing .*
   :re         #"rct-clr\..*"))
