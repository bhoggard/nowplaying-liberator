(ns nowplaying.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core :refer [defresource resource request-method-in]]
            [clojure.java.io :as io]
            [cheshire.core :refer [generate-string]]))


(def counterstream-data { :title "The People United Will Never Be Defeated!"
                          :composer "Frederic Rzewski" })

(defresource counterstream-json
  :allowed-methods [:get]
  :handle-ok (fn [_] (generate-string counterstream-data))
  :available-media-types ["application/json"])

(defroutes home-routes
  (GET "/" request (io/resource "public/index.html"))
  (GET "/counterstream" request counterstream-json))
