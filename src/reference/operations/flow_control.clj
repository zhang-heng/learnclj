(ns reference.operations.flow-control)
;;流程控制函数大都由内建函数和宏实现

;;常规控制
'if 'if-not 'if-let 'when 'when-not 'when-let 'when-first 'cond 'condp 'case 'do 'eval 'loop..recur 'trampoline 'while
;;异常处理
'try 'catch 'finally 'throw 'assert
;;延迟
'delay 'delay? 'deref 'force
;;函数
'repeatedly 'iterate
;;序列
'dotimes 'doseq 'for
;;惰性
'lazy-seq 'lazy-cat 'doall 'dorun
