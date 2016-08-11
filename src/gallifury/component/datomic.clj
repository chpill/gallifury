(ns gallifury.component.datomic
  (:require [com.stuartsierra.component :as component]
            datomic.api
            [io.rkn.conformity :as conformity]))

(defn- migrate [conn migration-file-path]
  (let [migration-data (conformity/read-resource "migrations/initial.edn")
        migration-names (keys migration-data)]
    (conformity/ensure-conforms conn
                                migration-data
                                migration-names)))


(defrecord Datomic [config conn]
  component/Lifecycle
  (start [this]
    (let [uri (:uri config)]
      (println "connecting to " uri)
      (datomic.api/create-database uri)
      (let [conn (datomic.api/connect uri)]
        (println "migrating " uri)
        (migrate conn "migrations/initial.edn")
        (println "datomic component ready")
        (assoc this :conn conn))))
  (stop [this]
    (when conn (datomic.api/release conn))
    (println "stopping " (:uri config))
    (assoc this :conn nil)))

;; example uri "datomic:mem://dev-db"
(defn datomic [config]
  (map->Datomic {:config config}))
