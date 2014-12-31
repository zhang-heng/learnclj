(ns learnclj.core
  (:import [org.apache.commons.daemon Daemon DaemonContext])
  (:gen-class :implements [org.apache.commons.daemon.Daemon]))

;;最新版emacs-live C-c C-p (加载代码块) 与旧版本不一致
;;在加载代码的同事未调整运行时的命名空间,导致找不到运行代码块中的部分资源.
;;修改插件源码:./packs/stable/clojure-pack/lib/cider/cider-interaction.el
;;            line 1337  +(cider-repl-set-ns (cider-current-ns))

(defn -main []
  (prn "hello world"))
