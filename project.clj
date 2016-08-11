(defproject gallifury "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.51"]
                 [com.stuartsierra/component "0.3.1"]
                 [compojure "1.5.1"]
                 [duct "0.8.0"]
                 [environ "1.0.3"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring-jetty-component "0.3.1"]
                 [ring-webjars "0.1.1"]
                 [org.slf4j/slf4j-nop "1.7.21"]
                 [org.webjars/normalize.css "3.0.2"]
                 [com.datomic/datomic-free "0.9.5390"]
                 [io.rkn/conformity "0.4.0"]]
  :plugins [[lein-environ "1.0.3"]
            [lein-cljsbuild "1.1.2"]]
  :main ^:skip-aot gallifury.main
  :target-path "target/%s/"
  :resource-paths ["resources" "target/cljsbuild"]
  :prep-tasks [["javac"] ["cljsbuild" "once"] ["compile"]]
  :cljsbuild
  {:builds
   {:main {:jar true
           :source-paths ["src"]
           :compiler {:output-to "target/cljsbuild/gallifury/public/js/main.js"
                      :optimizations :advanced}}}}
  :aliases {"setup"  ["run" "-m" "duct.util.repl/setup"]}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]
   :repl {:resource-paths ^:replace ["resources" "dev/resources" "target/figwheel"]
          :prep-tasks     ^:replace [["javac"] ["compile"]]}
   :uberjar {:aot :all}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[duct/generate "0.8.0"]
                                  [reloaded.repl "0.2.2"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [eftest "0.1.1"]
                                  [com.gearswithingears/shrubbery "0.3.1"]
                                  [kerodon "0.7.0"]
                                  [binaryage/devtools "0.6.1"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [duct/figwheel-component "0.3.2"]
                                  [figwheel "0.5.0-6"]]
                   :source-paths   ["dev/src"]
                   :resource-paths ["dev/resources"]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :env {:port "3000"}}
   :project/test  {}})
