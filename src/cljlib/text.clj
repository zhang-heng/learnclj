(ns learnclj.cljlib.text)

(interleave "abc" "def")
(str "abc" "def")
(apply str (interleave "abc" "def"))
