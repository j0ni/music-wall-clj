(ns music-wall.endpoint.root-test
  (:require [clojure.test :refer :all]
            [music-wall.endpoint.root :as root]))

(def handler
  (root/root-endpoint {}))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
