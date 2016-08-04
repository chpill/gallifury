(ns gallifury.main
    (:gen-class)
    (:require [com.stuartsierra.component :as component]
              [duct.util.runtime :refer [add-shutdown-hook]]
              [duct.util.system :refer [load-system]]
              [environ.core :refer [env]]
              [clojure.java.io :as io]))

(defn -main [& args]
  (let [bindings {'http-port (Integer/parseInt (:port env "3000"))}
        system   (load-system [(io/resource "gallifury/system.edn")] bindings)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (add-shutdown-hook ::stop-system #(component/stop system))
    (component/start system)))
