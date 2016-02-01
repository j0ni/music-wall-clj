(ns music-wall.frontend.songs
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET]]))

(def songs (atom []))

(defn handler [response]
  (reset! songs response)
  (.log js/console (str response)))

(defn error-handler [{:keys [status status-text] :as response}]
  (.log js/console (str "something bad happened: " status ", " status-text))
  (.log js/console response))

(defn load-songs []
  (GET "/songs" {:handler handler
                 :error-handler error-handler
                 :response-format :json
                 :keywords? true}))

(defn song-lister [songs]
  [:tbody
   (for [song songs]
     ^{:key (:id song)}
     [:tr
      [:td (:title song)]
      [:td (:author song)]])])

(defn song-list-component []
  (load-songs)
  (fn []
    [:table {:class "u-full-width"}
     [:thead
      [:tr
       [:th "Song Title"]
       [:th "Author"]]]
     [song-lister @songs]]))
