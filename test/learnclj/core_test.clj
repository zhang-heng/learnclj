(ns learnclj.core-test
  (:require [clojure.test :refer :all]
            [learnclj.core :refer :all])
  (:use clojure.test
        clojure-hadoop.job
        learnclj.learnhadoop))

(deftest test-1
  (testing "fix me" (is (= "hello, kay" (hello "kay")))))

(deftest test-wordcount
  (is (run job)))
