(ns learnclj.zeromq
  (:import [org.jeromq ZMQ])
  (:require (cheshire [core :as c])))
;;http://patternhatch.com/2013/06/12/messaging-using-clojure-and-zeromq/

;;使用ZeroMQ作为消息传递中间件,类似的还有RabbitMQ, ActiveMQ(Apollo) 也是极好的.
;;支持横向扩展,多对多模式,

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; zmq上下文,参数是线程数 建议1G/thread
(def ctx (ZMQ/context 1))


;;请求 响应 REQ/REP [Request-Reply]
(defn echo-server []
  (let [s (.socket ctx ZMQ/REP)]
    (.bind s "tcp://127.0.0.1:5555")
    (loop [msg (.recv s)]
      (.send s msg)
      (recur (.recv s)))))

(defn echo [msg]
  (let [s (.socket ctx ZMQ/REQ)]
    (.connect s "tcp://127.0.0.1:5555")
    (.send s msg)
    (prn "Server replied:" (String. (.recv s)))
    (.close s)))

;;(future-call echo-server)
;;(echo "Hello!")

;;发布 订阅 PUB/SUB [Publish-Subscribe]
(defn market-data-publisher []
  (let [s (.socket ctx ZMQ/PUB)
        market-data-event (fn []
                            {:symbol (rand-nth ["CAT" "UTX"])
                             :size (rand-int 1000)
                             :price (format "%.2f" (rand 50.0))})]
    (.bind s "tcp://127.0.0.1:6666")
    (while :true
      (.send s (c/generate-string (market-data-event))))))

(defn get-market-data [num-events]
  (let [s (doto (.socket ctx ZMQ/SUB)
            (.subscribe  "")
            (.connect "tcp://127.0.0.1:6666"))]
    (dotimes [_ num-events]
      (prn (c/parse-string (String. (.recv s)))))
    (.close s)))

;;(future-call market-data-publisher)
;;(future (get-market-data 100))


;;推 拉 PUSH/PULL [Pipeline]
