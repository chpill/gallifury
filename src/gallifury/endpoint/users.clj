(ns gallifury.endpoint.users
  (:require [compojure.core :refer :all]))

(defn users-endpoint [{datomic :datomic}]
  ;; TODO
  ;; 1: implement using the user-database boundary protocol
  ;; 2: improve by passing the db value, conn and current time to the handler
  ;;    (this will probably change heavily the user database component)
  (routes
   (context "/users" []
     (GET "/" [] "Hello World"))))
