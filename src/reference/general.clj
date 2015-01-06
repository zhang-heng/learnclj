(ns reference.general)
;;;clojure的基本类型

nil       ;;意思为空,类似java的null

nil?      ;;空判断
(nil? 0) ;=> false

=         ;;函数,判断多个参数是否相等
(= 1)       ;=>true
(= 1 1.0)   ;=>false
(= () [])   ;=>true 空集
(= [] {})   ;=>false类型不一致
(= nil nil) ;=>true

not=      ;;判断多个参数不等
(not= :a :b) ;=>true

'a        ;;取引述符号'
'(+ 1 1 ) ;返回字面序列

compare   ;;对比两个参数的大小,使用返回值来表述
;;(compare a b)
;; < 0 -> a < b
;; > 0 -> a > b
;; = 0 -> a = b
(compare 1 2)         ;=>-1
(compare \b \a)       ;=> 1
(compare "foo" "bar") ;=> 4
(compare 'x 'y)       ;=>-1
(compare :x :x)       ;=> 0
(compare [1 2] [1 3]) ;=>-1

comparator ;;返回对比处理函数,类似扩展compare
(let [lt (comparator <)]
  (lt 1 2))
(let [seq-lt (comparator
              (fn [a b]
                (neg? (compare (vec a) (vec b)))))]
  (seq-lt '(1) '(2)))

identical? ;;判别是否是同一对象
(identical? 1 1)                       ;=>true
(identical? (Integer. 1) (Integer. 1)) ;=>false

hash ;;获取哈希
(hash [1])  ;=>-1381383523
