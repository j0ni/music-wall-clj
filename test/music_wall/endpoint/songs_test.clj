(ns music-wall.endpoint.songs-test
  (:require [clojure.test :refer :all]
            [music-wall.endpoint.songs :as songs]
            [ring.mock.request :as mock]
            [bond.james :as bond]))

(def handler
  (songs/songs-endpoint {:db {:spec :fake-conn}}))

(deftest song-list-route-works
  (testing "returns a successful response"
    (with-redefs-fn {#'songs/songs (fn [_] '({:id 123 :title "Supermen" :author "David Bowie"}))}
      #(bond/with-spy [songs/songs]
         (let [response (handler (-> (mock/request :get "/songs")
                                     (mock/content-type "application/json")))]
           (is (= 200 (:status response)))
           (is (= 1 (-> songs/songs bond/calls count)))
           (is (= :fake-conn (-> songs/songs bond/calls first :args first))))))))
