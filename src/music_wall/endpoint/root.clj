(ns music-wall.endpoint.root
  (:require [compojure.core :refer :all]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]))

(def loading-page
  (html
    [:html
     [:head
      [:meta {:charset "utf-8"}]
      [:meta {:name "viewport"
              :content "width=device-width, initial-scale=1"}]
      [:link {:href "//fonts.googleapis.com/css?family=Raleway:400,300,600"
              :rel "stylesheet"
              :type "text/css"}]
      [:link {:rel "icon" :type "image/png" :href "favicon.png"}]]
     [:body
      [:div#app {:class "container"}
       [:p "tickety tock"]]
      (include-js
        "/js/goog/base.js"
        "/js/main.js")
      (include-css
        "/css/normalize.css"
        "/css/skeleton.css")
      [:script {:type "text/javascript"}
       "goog.require('music_wall.frontend.songs');"]]]))

(defn root-endpoint [config]
  (routes
   (GET "/" [] loading-page)))
