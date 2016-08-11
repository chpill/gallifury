(ns gallifury.component.datomic-test
  (:require [clojure.test :refer :all]
            datomic.api
            [gallifury.component.datomic :refer :all]))

(def test-uri "datomic:mem://gallifury-datomic-component-test")

(defn with-started-component
  "Executes f, providing it the started component. Stops the component afterward."
  [component f]
  (let [started-component (.start component)]
    (f started-component)
    (.stop started-component)))

(deftest datomic-component-lifecycles
  (let [datomic-cpt (datomic {:uri test-uri})]

    (testing "can start"
      (with-started-component datomic-cpt
        (fn [started-cpt]
          (is (= (set (keys started-cpt))
                 #{:conn :config}))
          (is (some? (datomic.api/db (:conn started-cpt)))))))

    (testing "migrates the db schema on startup"
      (with-started-component datomic-cpt
        (fn [started-cpt]
          (let [started-cpt (.start datomic-cpt)
                db (datomic.api/db (:conn started-cpt))]
            (is (some? (datomic.api/entity db [:db/ident :user/email])))))))))

