(ns music-wall.endpoint.root-test
  (:require [clojure.test :refer :all]
            [music-wall.endpoint.root :as root]
            [ring.mock.request :as mock]))

(def handler
  (root/root-endpoint {}))

(deftest root-page-works
  (testing "route returns a successful response"
    (let [response (handler (mock/request :get "/"))]
      (is (= 200 (:status response)))))
  (testing "the page invokes the app"
    (let [response (handler (mock/request :get "/"))]
      (is (re-find #"tickety tock" (:body response))))))
