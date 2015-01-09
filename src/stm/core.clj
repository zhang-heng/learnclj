(ns stm.core
  (:import [java.util.concurrent.atomic AtomicInteger]
           [java.util.concurrent CountDownLatch]))

;;;管理协作式,同步修改可变状态
(def v-ref (ref []))
;;;管理非协作,同步修改可变状态
(def v-atom (atom []))
;;;管理异步修改可变状态
(def v-agent (agent []))
;;;管理线程变量
;;(def v-var (Var []))

(defn test-ref []
  (let [r (ref [])]
    (dosync (ref-set r [1 2 3 4]))
    (dosync (alter r (partial filter even?)))
    (dosync (alter r conj :a :b :c)))
  (let [r (ref {:a {:b 2}})]
    (dosync (alter r update-in [:a :b] (fn [n] 3)))
    (dosync (commute r update-in [:a :c] (fn [n] 3))))
  (let [r (ref #{} :validator (partial every? #(not (nil? %))))]
    (dosync (alter r conj nil))
    @r)
  (let [r (ref 0)]
    (dosync (ref-set r (+ (ensure r) 1)))))

;;;解决写偏序问题

(defn run-deduct []
  (let [account1 (ref 100)
        account2 (ref 100)
        deduct (fn [account n other]
                 (dosync
                  (when (<= 0 (+ (- @account n) @other))
                    (alter account - n))))]
    (dotimes [_ 300]
      (.start (Thread. #(deduct account1 200 account2))))
    (dotimes [_ 300]
      (.start (Thread. #(deduct account1 200 account2))))
    (dotimes [_ 300]
      (.start (Thread. #(deduct account2 200 account1))))
        (dotimes [_ 300]
      (.start (Thread. #(deduct account2 200 account1))))
    (Thread/sleep 100)
    (prn (str @account1 " " @account2))))

(defn run-deduct []
  (let [account1 (ref 100)
        account2 (ref 100)
        deduct (fn [account n other]
                 (dosync
                  ;(Thread/sleep 1)
                  (when (<= 0 (+ (- (ensure account) n) (ensure other)))
                    (alter account - n))))]
    (dotimes [_ 300]
      (.start (Thread. #(deduct account1 200 account2))))
    (dotimes [_ 300]
      (.start (Thread. #(deduct account2 200 account1))))
    (prn (str @account1 " " @account2))))


;;;atom 做缓存

;;创建缓存
(defn make-cache [] (atom {}))
;;放入缓存
(defn putm [cache key value] (swap! cache assoc key value))
;;取出
(defn getm [cache key] (key @cache))

(defn test-cache []
  (let [cache (make-cache)]
    (dotimes [n 100] (putm cache (keyword (str n)) n))
    (dotimes [n 100] (prn (getm cache (keyword (str n)))))))


;;java AtomicInteger 与性能 atom对比
(def a (AtomicInteger. 0))
(def b (atom 0))
;;为了性能，给java加入type hint
(defn java-inc [#^AtomicInteger counter] (.incrementAndGet counter))
(defn countdown-latch [#^CountDownLatch latch] (.countDown latch))
;;单线程执行缓存次数
(def max_count 1000000)
;;线程数
(def thread_count 100)
(defn benchmark [fun]
  (let [latch (CountDownLatch. thread_count) ;;关卡锁
        start (System/currentTimeMillis) ]   ;;启动时间
    (dotimes [_ thread_count]
      (.start (Thread. #(do (dotimes [_ max_count] (fun))
                            (countdown-latch latch)))))
    (.await latch)
    (- (System/currentTimeMillis) start)))
(defn atom-test []
  (let []
    (println "atom:" (benchmark #(swap! b inc)))
    (println "AtomicInteger:" (benchmark #(java-inc a)))
    (println @b)
    (println (.get a))))
;; atom: 16966
;; AtomicInteger: 10187
;; 100000000
;; 100000000
;;结果是AtomicInteger要强

(defn test-agent []
  (def ag (agent 0))
  (deref ag)
  (send ag + 1 1 1 1)
  (send-off ag inc)

  (do (send ag inc)
      (await ag)
      @ag))

;;在agent的处理过程中产生异常
;(send ag #(throw (Exception. "123")))


;; ;;在外部判断异常存在
;; (agent-errors ag)

;; (deref ag)

;; (clear-agent-errors ag)


;; shit
(defn agent-shit []
  (let [ag (agent 0)]
    (send ag (fn [_]
               (do (Thread/sleep 100)
                   (prn "饭前甜点")
                   (throw (Exception. "异常1")))))
    (when-not (agent-errors ag)
      (prn "安全进入")
      (send ag (fn [_]
                 (do (Thread/sleep 200)
                     (prn "被吃掉了")))))))
