(ns learnclj.cljlib.core)
;;clojure库的基本操作

;;函数声明
(defn greeting
  "doc...."
  ([user]
   (str "hello, " user))
  ([] (str "hello, kay")))
(greeting "kay")
(greeting)

(defn greeting-all [& users]
  (str "hwello, " (reduce (fn [col user] (str col ", " user)) users)))
(greeting-all "a")
(greeting-all "a" "b")
(greeting-all "a" "b" "c")

;;声明对象/绑定
(def visitors (atom #{}))
;;不会被重复加载覆盖的声明
(defonce visitors2 (atom #{}))

;;匿名函数
(def unname-fun (fn [arg] (str "unname-fun, " arg)))
(unname-fun "kay")

;;操作原子引用
(swap! visitors conj "a")

;;解引用
(deref visitors)
@visitors

;;解构
(defn greeting-2 [{fname :first-name}]
  (str "hello, " fname))
(greeting-2 {:first-name "kay", :last-name "zhang"})

(let [[a b c :as numbers] [1 2 3 4 5]]
  (str a b c " " (apply str numbers)))

;;命名空间
(learnclj.core/hello "you")

;;调用java方法
(.toUpperCase "abc")
(Character/toUpperCase \s)

;;传递参数
(defn fun [arg-1 arg-2]
  (str arg-1 arg-2))
(apply fun ["hello" " kay"])

;;流程控制
(if true "true" "false");true
(if 0 "true" "false");true
(if () "true" "false");true
(if nil "true" "false");false

(true? nil);false
(nil? nil);true

(when true
  (do (prn 1)
      (prn 2)
      (prn 3)
      :true))

;;循环,使用不多,序列库包含了功能性函数
(loop [result []
       x 6]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))

;;调用java
(import '(java.util Random))

(def rnd (new java.util.Random))
(. rnd nextInt 10)

(. Math PI)

;;元数据
(defn meta-fun
  {:tag String
   :added "1.0"
   :static true}
  [arg]
  (str arg))
(meta #'meta-fun)
