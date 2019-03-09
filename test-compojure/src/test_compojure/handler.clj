(ns test-compojure.handler
  (:require [compojure.core :refer :all]
            [ring.util.response :as r]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [compojure.route :as route]
            [config.core :refer [env]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hiccup.core :refer [html]]))
;
; For development run with: lein ring server
;                           lein ring server-headless
;

; https://github.com/ring-clojure/ring/wiki/Concepts

; Hiccup (HTML) https://github.com/weavejester/hiccup

(defroutes app-routes
  ; Standard GET with JSON response
  (GET "/" [] {:status 200 :headers {"Content-Type" "application/json"} :body {:foo "Hello World"}})
  ; Standard GET With HTML response using Hiccup.
  (GET "/config" [] (html [:h2 "Currently configured environment: " (:environment env)]))
  ; Standard GET with content-type wrapper
  (GET "/json" [] (r/content-type (r/response {:foo "Hello World"}) "application/json"))

  (GET "/ok" [] {:status 200 :headers {"Content-Type" "application/json"} :body {:status "OK"}})
  ; GET with parameter and header
  ; TBD
  ; POST with JSON request body
  ; TBD
  ; EDN response, see: https://www.reddit.com/r/Clojure/comments/arc83f/ringcompojure_not_properly_serializing_edn/
  (GET "/edn" [] {:headers {"Content-Type" "application/edn"} :body (pr-str {:foo :bar})})
  ; HTTP error 400 with JSON
  (GET "/error400" [] {:status 400 :headers {"Content-Type" "application/json"} :body {:status "Error 400" :message "So don't do it again"}})
  (GET "/error401" [] {:status 401 :headers {"Content-Type" "application/json"} :body {:status "Error 401" :message "So don't do it again"}})
  ; Not found response
  (route/not-found "Not Found"))

; CORS : https://stackoverflow.com/questions/51503910/how-to-use-cors-with-json-response-in-compojure

(def app
  (-> app-routes
      (wrap-defaults site-defaults)
      (wrap-json-response)
      (wrap-json-params)
      (wrap-cors :access-control-allow-methods [:get :post :put :delete] :access-control-allow-origin [#".*"])))