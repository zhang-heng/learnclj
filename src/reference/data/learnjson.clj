(ns reference.data.learnjson
  (:require [clojure.data.json :as json]))

(json/read-str "{\"a\":1,\"b\":2}")
(json/write-str [1 2 3])
