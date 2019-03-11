(ns test-client.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(defn get-url [url]
  (go (let [response (<! (http/get url {:with-credentials? false}))]
        (prn (str "Status: " (:status response)))
        (prn (:body response)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce app-state
  (reagent/atom {}))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn page [ratom]
  [:div
   "Welcome to reagent-figwheel."])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")
    ))

(defn reload []
  (reagent/render [page app-state]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (get-url "http://localhost:3000/error401")
  ;(get-url "http://127.0.0.1:8080/CrunchifyTutorials/api/crunchifyServiceError")
  (dev-setup)
  (reload))
