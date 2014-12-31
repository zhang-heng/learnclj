(defproject learnclj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 ;;become a java server
                 [commons-daemon/commons-daemon "1.0.15"]
                 ;;mongodb connect lib
                 [com.novemberain/monger "2.0.0"]
                 ;;zeroMQ clojure lib
                 [com.rmoquin.bundle/jeromq "0.2.0"]
                 [cheshire "5.2.0"]]
  :profiles {:dev {:main learnclj.core}
             :jar {:main learnclj.core}
             :uberjar {:main learnclj.core
                       :aot :all}})
