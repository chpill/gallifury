(ns gallifury.boundary.user-database
  (:require gallifury.component.datomic
            datomic.api)
  (:import gallifury.component.datomic.Datomic
           java.util.Date))

;; This will probably not be the way we use the db on http request...
;; It would be better to inject the `conn` and `db` value in each request
;; manipulating the db value in far more indiomatic than passing the connection
;; around (as would be the case using the functions of this protocol).

(defprotocol UserDatabase
  (create-user! [datomic-cpt user])
  (find-all-users [datomic-cpt]))

(extend-protocol UserDatabase
  gallifury.component.datomic.Datomic
  ;; The user better follow the namespaced keywords of the schema...
  ;; TODO FIXME try the namespaced destructuring of clojure 1.9
  (create-user! [datomic-cpt user]
    (datomic.api/transact (:conn datomic-cpt)
                          [(merge {:db/id (datomic.api/tempid :db.part/user)
                                   ;; This feels very ugly...
                                   ;; The time should be provided as argument to this function
                                   ;; provided at a higher level from the
                                   ;; environment (request map for http request)
                                   :user/created-at (new java.util.Date)}
                                  user)]))

  (find-all-users [datomic-cpt]
    (datomic.api/q
     ;; We use the pull syntax here because it forces a shape on our data, so
     ;; that it is readily serializable by transit
     '[:find [(pull ?e [:user/email :user/name :user/created-at]) ...]
       :where [?e :user/email ?email]]
     (datomic.api/db (:conn datomic-cpt)))))
