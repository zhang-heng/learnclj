(ns cljlib.collect)

;;;;列表 区别于向量 在队首操作
;; clojure.lang.PersistentList
(class '(1 2 3))

;;;;向量 区别与队列 在队尾操作
;; clojure.lang.PersistentVector
(class [1 2 3])

;;;;# {} 集合 没有重复值
;; clojure.lang.PersistentHashSet
(class #{1 2 3})
(sorted-set 1 3 2)

;;;;映射表(键值对/map)
;; clojure.lang.PersistentArrayMap
(class {:a 1 :b 2})
(sorted-map :a 1 :c 3 :b 2)

(def my-map {"a" 1 "b" 2})
(my-map "a")     ;->1
(my-map "c")     ;->nil
(my-map "c" 3)   ;->3
(get my-map "a") ;->1

(def my-may2 {:a 1 :b 2})
(my-may2 :a)
(:a my-may2)

;;;;记录表 映射表的模板化描述
(defrecord book [title author])
(->book  "title" "author")

;;;;集合操作
(def col '(1 2 3 4 5))
(def vect [1 2 3 4 5])
(def hset #{1 2 3})
(def my-map {:a 1 :b 2})

;;;创建
(list 1 2 3 4)
(vector 1 2 3 4)
(hash-set 1 2 3 4)
(set '(1 2 3))
(hash-map 1 2 3 4)

(range 1 25 2)                ;范围生成
(repeat 10 2)                 ;(repeat 1) 无限序列
(take 10 (iterate inc 0))     ;迭代函数生成
(take 10 (cycle '(1 0)))      ;序列循环生成
(interpose "," ["a" "b" "c"]) ;间隔插入
;;;合并

;;在序列首增加元素
(cons 0 col)
(cons 0 vect)
(cons 0 hset) ;会将集合转化为序列
(cons [1 1] my-map)
;;添加元素
(conj col :a :b) ;列表在队首
(into col [:a :b])

(conj vect :a :b);向量在队尾
(into vect [:a :b])

(concat [1 2] [3 4]) ;将两个序列连接

;;按需提取
(first col)
(second col)
(last col)
(seq (rest col)) ;除第一个
(next col)
(filter even? col)
(take-while #(<= % 3) col)
(drop-while #(<= % 3) col)
(split-at 3 '(2 3 4 5))
(split-with #(< % 3) '(2 3 4 5))
(for [a '(1 2)
      b '(10 11)]
  (str a b));惰性序列
