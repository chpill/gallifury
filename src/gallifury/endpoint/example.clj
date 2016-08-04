(ns gallifury.endpoint.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]))

(defn example-endpoint [config]
  (context "/example" []
    (GET "/" []
      (io/resource "gallifury/endpoint/example/example.html"))))
