(ns gallifury.endpoint.example-test
  (:require [clojure.test :refer :all]
            [gallifury.endpoint.example :as example]
            [kerodon
             [core :as kerodon]
             [test :as kerodon-test]]))

(def handler
  (example/example-endpoint {}))

(deftest smoke-test
  (testing "example page exists"
    (-> (kerodon/session handler)
        (kerodon/visit "/example")
        (kerodon-test/has
         (kerodon-test/status? 200) "page exists"))))
