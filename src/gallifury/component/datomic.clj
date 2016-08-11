(ns gallifury.component.datomic
  (:require [com.stuartsierra.component :as component]
            datomic.api
            [io.rkn.conformity :as conformity]))

(defrecord Datomic [config conn]
  component/Lifecycle
  (start [this]
    (let [uri (:uri config)]
      (println "connecting to " uri)
      (datomic.api/create-database uri)
      (let [conn (datomic.api/connect uri)]
        (println "reading DB migration data")
        (let [migration (conformity/read-resource "migrations/initial.edn")]
          (println "migrating " uri)
          (conformity/ensure-conforms conn migration [:gallifury/user])
          (println "datomic component ready")
          (assoc this :conn conn)))))
  (stop [this]
    (when conn (datomic.api/release conn))
    (assoc this :conn nil)))

;; example uri "datomic:mem://dev-db"
(defn datomic [config]
  (map->Datomic {:config config}))

