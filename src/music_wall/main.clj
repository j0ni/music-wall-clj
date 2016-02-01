(ns music-wall.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [meta-merge.core :refer [meta-merge]]
            [music-wall.config :as config]
            [music-wall.system :refer [new-system]]
            [taoensso.timbre :refer [infof]]
            [duct.component.ragtime :as ragtime]))

(def prod-config
  {:app {:middleware     [[wrap-hide-errors :internal-error]]
         :internal-error (io/resource "errors/500.html")}})

(def config
  (meta-merge config/defaults
              config/environ
              prod-config))

(defn -main [& args]
  (let [system (new-system config)]
    (infof "Starting HTTP server on port %d" (-> system :http :port))
    (-> (component/start system)
        (:ragtime)
        (ragtime/migrate))))
