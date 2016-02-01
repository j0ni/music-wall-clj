(ns music-wall.endpoint.songs
  (:require [compojure.core :refer :all]
            [honeysql.core :as sql]
            [honeysql.helpers :refer [select from insert-into values columns]]
            [taoensso.timbre :refer [infof]]
            [clojure.java.jdbc :as jdbc]
            [schema.core :as s])
  (:import [java.util UUID]))

(def Song {(s/optional-key :id) UUID
           :title s/Str
           :author s/Str})

(s/defn ^:always-validate songs :- [Song]
  [conn]
  (let [query (-> (select :id :title :author)
                  (from :songs)
                  (sql/format))]
    (jdbc/query conn query)))

(s/defn ^:always-validate create-song :- [Song]
  [conn song :- Song]
  (let [query (-> (insert-into :songs)
                  (values [(assoc song :id (UUID/randomUUID))])
                  (sql/format))]
    (jdbc/execute! conn query)))

(defn songs-endpoint
  [config]
  (let [conn (-> config :db :spec)]
    (routes
      (context "/songs" []
        (GET "/" [] (songs conn))))))
