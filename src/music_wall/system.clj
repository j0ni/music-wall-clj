(ns music-wall.system
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]
            [duct.component.hikaricp :refer [hikaricp]]
            [duct.component.ragtime :refer [ragtime]]
            [duct.middleware.not-found :refer [wrap-not-found]]
            [duct.middleware.route-aliases :refer [wrap-route-aliases]]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.webjars :refer [wrap-webjars]]
            [music-wall.endpoint.songs :refer [songs-endpoint]]
            [music-wall.endpoint.root :refer [root-endpoint]]
            [ring.middleware.format :refer [wrap-restful-format]]))

(def base-config
  {:app {:middleware [[wrap-restful-format]
                      [wrap-not-found :not-found]
                      [wrap-webjars]
                      [wrap-defaults :defaults]
                      [wrap-route-aliases :aliases]]
         :not-found (io/resource "music_wall/errors/404.html")
         :defaults (meta-merge site-defaults {:static {:resources "music_wall/public"}})
         :aliases {}}
   :ragtime {:resource-path "music_wall/migrations"}})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
          :app (handler-component (:app config))
          :http (jetty-server (:http config))
          :db (hikaricp (:db config))
          :ragtime (ragtime (:ragtime config))
          :root (endpoint-component root-endpoint)
          :songs (endpoint-component songs-endpoint))
        (component/system-using
          {:http [:app]
           :app [:songs :root]
           :songs [:db]
           :ragtime [:db]}))))
