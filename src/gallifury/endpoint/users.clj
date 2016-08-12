(ns gallifury.endpoint.users
  (:require [compojure.core :refer :all]
            [gallifury.boundary.user-database :as users-db]))

(defn users-endpoint [{datomic :datomic}]
  ;; TODO
  ;; 1: implement using the user-database boundary protocol
  ;; 2: improve by passing the db value, conn and current time to the handler
  ;;    (this will probably change heavily the user database component)
  (routes
   (context "/users" []
     (GET "/" []
       (fn [req]
         (let [users (users-db/find-all-users datomic)]
           {:status 200
            :body users})))
     ;; TODO FIXME read value from request data.
     ;; There are no easy way to write transit from the command line though...
     (POST "/" []
       (fn [req]
         @(users-db/create-user!
          datomic
          {:user/name "bob"
           :user/email "bob@example.com"}))))))
