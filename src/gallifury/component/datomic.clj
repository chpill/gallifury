(ns gallifury.component.datomic
  (:require [com.stuartsierra.component :as component]
            [datomic.api]))

(defrecord Datomic [config conn]
  component/Lifecycle
  (start [this]
    (let [uri (:uri config)
          _ (datomic.api/create-database uri)
          conn (datomic.api/connect uri)]
      (println "starting" uri)
      (assoc this :conn conn)))
  (stop [this]
    (when conn (datomic.api/release conn))
    (assoc this :conn nil)))

;; example uri "datomic:mem://dev-db"
(defn datomic [config]
  (map->Datomic {:config config}))

