(ns gallifury.endpoint.users-test
  (:require [clojure.test :refer :all]
            fixtures.datomic
            [gallifury.endpoint.users :as gallifury.endpoint]))

(deftest smoke-test
  (fixtures.datomic/with-started-db
    (fn [started-db]
      (let [handler (gallifury.endpoint.users/users-endpoint {:datomic started-db})]
        (testing "with blank db"
          (testing "'GET /users' returns an empty vector"
            (let [res (handler {:request-method :get
                                :uri "/users"})]
              (is (= (:status res) 200))
              (is (= (:body res) []))))
          (testing "'POST /users creates a user"
            (let [res (handler {:request-method :post
                                :uri "/users"})]
              (is (= (:status res) 200))))
          (testing "'GET /users' now returns the created user"
            (let [res (handler {:request-method :get
                                :uri "/users"})]
              (is (= (:status res) 200))
              (is (= (count (:body res))
                     1))
              (is (= (set  (keys (first (:body res))))
                     #{:user/email :user/name :user/created-at})))))))))
