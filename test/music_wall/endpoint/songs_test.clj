(ns music-wall.endpoint.songs-test
  (:require [clojure.test :refer :all]
            [music-wall.endpoint.songs :as songs]))

(def handler
  (songs/songs-endpoint {}))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
