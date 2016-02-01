(ns music-wall.frontend.app
  (:require [ajax.core :refer [GET]]
            [reagent.core :as reagent]
            [music-wall.frontend.songs :as songs]))

(defn home-page []
  [:div.container
   [:h1 "Welcome to Music Wall"]
   [:div#songs
    [songs/song-list-component]]])

;; -------------------------
;; Initialize app

(defn mount-components []
  (reagent/render [#'home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-components))
