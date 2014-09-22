(ns nowplaying.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [nowplaying.routes.home :refer [home-routes]]))

(defn init []
  (println "nowplaying is starting"))

(defn destroy []
  (println "nowplaying is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") 8080))]
  (run-jetty app {:port port})))
