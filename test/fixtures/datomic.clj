(ns fixtures.datomic
  (:require [gallifury.component.datomic :refer [datomic]]
            datomic.api))

;; Database fixtures

(defn test-uri []
  (str "datomic:mem://gallifury-datomic-component-test"
       (gensym)))

(defn with-started-db
  "Startups a datomic component and run the given function. Stops the component
  afterward"
  [f]
  (let [datomic-cpt (datomic {:uri (test-uri)})
        started-component (.start datomic-cpt)]
    (f started-component)
    (.stop started-component)))

(defn with-populated-db
  "Startups a datomic component, populate it according to the populate-choice
  keyword and run the given function. Stops the component afterward"
  [populate-key f]
  (with-started-db
    (fn [started-component]
      ;; We should transact something along those lines here
      ;; @(datomic.api/transact (:conn started-component)
      ;;                        [[...]])
      (f started-component)
      (.stop started-component))))
