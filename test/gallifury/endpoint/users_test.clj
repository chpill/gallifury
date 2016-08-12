(ns gallifury.endpoint.users-test
  (:require [clojure.test :refer :all]
            [shrubbery.core :as shrub]
            [gallifury.endpoint.users :refer :all]))

(def handler
  (users-endpoint {}))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
