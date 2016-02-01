(ns cljs.app
  (:require [music-wall.frontend.app]))

;; ignore println statements in prod
(set! *print-fn* (fn [& _]))

(app/init!)
