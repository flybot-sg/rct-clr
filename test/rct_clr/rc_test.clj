(ns rct-clr.rc-test
  (:require [clojure.test :refer [deftest testing]]
            [com.mjdowney.rich-comment-tests.test-runner :as test-runner]))

(deftest ^:rct rich-comment-tests
  (testing "Rich comment tests."
    (test-runner/run-tests-in-file-tree! :dirs #{"src"})))
