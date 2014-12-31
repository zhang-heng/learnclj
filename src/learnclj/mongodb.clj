(ns learnclj.mongodb
  (:require [monger.core :as mg]
            [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]
           [org.bson.types ObjectId]))

(defn connect-db []
  (let [conn (mg/connect {:host "127.0.0.1" :port 27017})
        db (mg/get-db conn "monger-test")
        collection "numbers"
        col (map #(array-map :number %) (range 1 10))]
    (time (mc/insert-batch db collection col))
    (time (prn (mc/find db collection {:number 1})))
    (mg/disconnect conn)))
