(ns learnclj.cia-storm
  (:use [backtype.storm clojure config]))

(defspout sentence-spout ["sentence"]
  [conf context collector]
  (let [sentences ["a little brown dog"
                   "the man petted the dog"
                   "four score and seven years ago"
                   "an apple a day keeps the doctor away"]]
    (spout
     (nextTuple []
                (Thread/sleep 1000)
                (emit-spout! collector [(rand-nth sentences)])))))

(defbolt split-bolt ["word"] {:prepare true}
  [conf context collector]
  (bolt
   (execute [tuple]
            (let [words (.split (.getString tuple 0) " ")]
              (doseq [w words]
                (emit-bolt! collector [w])))
            (ack! collector tuple))))
