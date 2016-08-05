(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.generate :as gen]
            [duct.util.repl :refer [setup test cljs-repl migrate rollback]]
            [duct.util.system :refer [load-system]]
            [reloaded.repl :refer [system init start stop go reset]]))

(defn new-system []
  (let [system (load-system
                (keep io/resource ["gallifury/system.edn"
                                   "dev.edn"
                                   "local.edn"]))]
    (println "starting server on port"(:port (:http system)))
    system))

(when (io/resource "local.clj")
  (load "local"))

(gen/set-ns-prefix 'gallifury)

(reloaded.repl/set-init! new-system)
