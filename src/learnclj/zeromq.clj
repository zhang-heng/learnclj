(ns learnclj.zeromq
  (:import [org.jeromq ZMQ])
  (:require (cheshire [core :as c])))

(def ctx (ZMQ/context 1))

;;请求 响应 REQ/REP [Request-Reply]
(defn echo-server []
  (let [s (.socket ctx ZMQ/REP)]
    (.bind s "tcp://127.0.0.1:5555")
    (loop [msg (.recv s)]
      (.send s msg)
      (recur (.recv s)))))

(defn echo
  [msg]
  (let [s (.socket ctx ZMQ/REQ)]
    (.connect s "tcp://127.0.01:5555")
    (.send s msg)
    (println "Server replied:" (String. (.recv s)))
    (.close s)))

;;发布 订阅 PUB/SUB [Publish-Subscribe]


;;
