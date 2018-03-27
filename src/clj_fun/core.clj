(ns clj-fun.core
  (:gen-class)
  (:require [compojure.core :as c]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :as defaults]
            [ring.middleware.json :as json]))

(defn response
  [message]
  {:status 200
   :body message})

(c/defroutes handler
  (c/GET "/hello/:name" [name]
         (response {:message (str "Hello, " name "!")}))
  (c/POST "/hello" req
          (let [name (get-in req [:body :name] "UNKNOWN")]
            (println (pr-str req))
            (response {:message (str "Hello, " name "!")})))
  (route/not-found "Couldn't find your resource"))

(def app
  (-> handler
      (json/wrap-json-response)
      (json/wrap-json-body {:keywords? true})
      (defaults/wrap-defaults defaults/api-defaults)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (jetty/run-jetty app {:port 8080}))
