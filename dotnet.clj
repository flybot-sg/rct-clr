(ns dotnet
  "Dotnet related tasks to be called by `nostrand`.
  Nostrand uses the `magic` compiler.

  ## Motivation

  This namespace provides convenient functions to:
  - compile the prod namespaces to .net assemblies
  - run the tests in the CLR
  - pack and push NuGet Packages to a host repo"

  (:require [clojure.test :refer [run-all-tests]]
            [magic.flags :as mflags]))

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
  (binding [*compile-path*                  "build"
            *unchecked-math*                *warn-on-reflection*
            mflags/*strongly-typed-invokes* true
            mflags/*direct-linking*         true
            mflags/*elide-meta*             false]
    (println "Compile into DLL To : " *compile-path*)
    (doseq [ns prod-namespaces]
      (println (str "Compiling " ns))
      (compile ns))))

(defn run-tests
  "Run all the tests on the CLR.
  nos dotnet/run-tests"
  []
  (binding [*unchecked-math*                *warn-on-reflection*
            mflags/*strongly-typed-invokes* true
            mflags/*direct-linking*         true
            mflags/*elide-meta*             false]
    (doseq [ns (concat prod-namespaces test-namespaces)]
      (require ns))
    (let [{:keys [fail error]} (run-all-tests)]
      (when (or (pos? fail) (pos? error))
        (Environment/Exit 1)))))
