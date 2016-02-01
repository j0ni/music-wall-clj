(ns cljs.user
  (:require [figwheel.client :as figwheel]
            [music-wall.frontend.app :as app]))

(js/console.info "Starting in development mode")

(enable-console-print!)

(figwheel/start {:websocket-url "ws://localhost:3449/figwheel-ws"
                 :on-jsload app/mount-components})

(app/init!)
