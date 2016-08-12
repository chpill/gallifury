(ns gallifury.boundary.user-database-test
  (:require [clojure.test :refer :all]
            [gallifury.boundary.user-database :refer :all]
            [gallifury.component
             [datomic :refer [datomic]]
             [datomic-test :refer [test-uri with-started-component]]]))

(deftest user-database-boundary
  (let [datomic-cpt (datomic {:uri (str test-uri (gensym))})]
    (with-started-component datomic-cpt
      (fn [started-component]
        (testing "can create and find 2 users in the database"
          @(create-user! started-component
                         {:user/email "george.abidbol@example.com"
                          :user/name "george"})
          @(create-user! started-component
                         {:user/email "horatio.nelson@example.com"
                          :user/name "horatio"})
          (is (= (count (find-all-users started-component))
                 2)))

        (testing "trying to duplicate a user email fails"
          (try (do
                 @(create-user! started-component
                                {:user/email "george.abidbol@example.com"
                                 :user/name "george"})
                 @(create-user! started-component
                                {:user/email "george.abidbol@example.com"
                                 :user/name "not george"}))
               (catch Exception e
                 (is (= (ex-data (.getCause e))
                        {:db/error :db.error/unique-conflict})))))

        (testing "trying to duplicate username fails"
          (try (do
                 @(create-user! started-component
                                {:user/email "george.abidbol@example.com"
                                 :user/name "george"})
                 @(create-user! started-component
                                {:user/email "horatio.nelson@example.com"
                                 :user/name "george"}))
               (catch Exception e
                 (is (= (ex-data (.getCause e))
                        {:db/error :db.error/unique-conflict})))))))))
