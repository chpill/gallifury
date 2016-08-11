(ns gallifury.component.datomic-test
  (:require [clojure.test :refer :all]
            datomic.api
            [gallifury.component.datomic :refer :all]))

(def test-uri "datomic:mem://gallifury-datomic-component-test")

;; (def ^:dynamic *conn* nil)
;; (defn fresh-database []
;;   (let [db-name (gensym)
;;         db-uri (str "datomic:mem://" db-name)]
;;     (datomic/create-database db-uri)
;;     (datomic/connect db-uri)))

;; (defn database-fixture [f]
;;   (binding [*conn* (fresh-database)]
;;     (f)))

(deftest datomic-component-lifecycles
  (let [datomic-cpt (datomic {:uri test-uri})]

    (testing "can start"
      (let [started-cpt (.start datomic-cpt)]
        (is (= (set (keys started-cpt))
               #{:conn :config}))
        (is (some? (datomic.api/db (:conn started-cpt))))))

    (testing "migrates the db schema on startup"
      (let [started-cpt (.start datomic-cpt)
            db (datomic.api/db (:conn started-cpt))]
        (is (some? (datomic.api/entity db [:db/ident :user/email])))))))

