(defproject music-wall "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [com.stuartsierra/component "0.3.0"]
                 [compojure "1.4.0"]
                 [duct "0.5.7"]
                 [environ "1.0.1"]
                 [hanami "0.1.0"]
                 [meta-merge "0.1.1"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-jetty-component "0.3.0"]
                 [ring-webjars "0.1.1"]
                 [org.webjars/normalize.css "3.0.2"]
                 [org.slf4j/slf4j-nop "1.7.14"]
                 [duct/hikaricp-component "0.1.0" :exclusions [org.slf4j/slf4j-nop]]
                 [org.postgresql/postgresql "9.4-1203-jdbc4"]
                 [duct/ragtime-component "0.1.3"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [honeysql "0.6.2"]
                 [prismatic/schema "1.0.4"]
                 [com.taoensso/timbre "4.2.1"]
                 ;; hikaricp and jetty are super noisy - enable these
                 ;; if we need to look at those logs only
                 ;; [com.fzakaria/slf4j-timbre "0.3.0"]
                 ;; [org.slf4j/log4j-over-slf4j "1.7.14"]
                 ;; [org.slf4j/jul-to-slf4j "1.7.14"]
                 ;; [org.slf4j/jcl-over-slf4j "1.7.14"]
                 [reagent "0.6.0-alpha"]
                 [hiccup "1.0.5"]
                 [cljs-ajax "0.5.3"]
                 [ring-middleware-format "0.7.0"]]
  :plugins [[lein-environ "1.0.1"]
            [lein-gen "0.2.2"]
            [lein-cljsbuild "1.1.2"]]
  :generators [[duct/generators "0.5.7"]]
  :duct {:ns-prefix music-wall}
  :main ^:skip-aot music-wall.main
  :uberjar-name "music-wall-standalone.jar"
  :target-path "target/%s/"
  :resource-paths ["resources" "target/cljsbuild"]
  :prep-tasks [["javac"] ["cljsbuild" "once"] ["compile"]]
  :cljsbuild
  {:builds
   {:main {:jar true
           :source-paths ["src"]
           :compiler
           {:output-to "target/cljsbuild/music_wall/public/js/main.js"
            :optimizations :none}}}}
  :aliases {"gen"   ["generate"]
            "setup" ["do" ["generate" "locals"]]
            "deploy" ["do"
                      ["vcs" "assert-committed"]
                      ["vcs" "push" "heroku" "master"]]}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]
   :repl {:resource-paths ^:replace ["resources" "target/figwheel"]
          :prep-tasks     ^:replace [["javac"] ["compile"]]}
   :uberjar {:aot :all
             :cljsbuild
             {:builds {:main {:source-paths ["prod"]
                              :compiler {:optimizations :advanced
                                         :pretty-print false}}}}}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[reloaded.repl "0.2.1"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [eftest "0.1.0"]
                                  [kerodon "0.7.0"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [duct/figwheel-component "0.3.1"]
                                  [figwheel "0.5.0-1"]
                                  [bond "0.2.6"]
                                  [ring/ring-mock "0.3.0"]]
                   :source-paths ["dev"]
                   :repl-options {:init-ns user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :env {:port 3000}}
   :project/test  {}})
