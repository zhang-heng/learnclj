(defproject learnclj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]

                 ;;json
                 [org.clojure/data.json "0.2.1"]

                 ;;become a java server
                 [commons-daemon/commons-daemon "1.0.15"]

                 ;;mongodb on clojure
                 [com.novemberain/monger "2.0.0"]

                 ;;zeroMQ on java
                 [com.rmoquin.bundle/jeromq "0.2.0"]
                 ;;json encoding on clojure
                 [cheshire "5.2.0"]

                 ;;RabbitMQ on clojure
                 [com.novemberain/langohr "3.0.1"]

                 ;;Neo4J REST API on clojure
                 [clojurewerkz/neocons "3.0.0"]

                 ;;ClojureScript
                 [org.clojure/clojurescript "0.0-2665"]

                 ;;hadoop
                 [clojure-hadoop "1.4.1"]]

  :profiles {:dev {:main learnclj.core}
             :jar {:main learnclj.core}
             :uberjar {:main learnclj.core
                       :aot :all}})
