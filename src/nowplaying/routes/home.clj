(ns nowplaying.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core :refer [defresource resource request-method-in]]
            [clojure.java.io :as io]
            [cheshire.core :refer [generate-string]]
            [nowplaying.models.feed :as feed]))


(defresource counterstream-json
  :allowed-methods [:get]
  :handle-ok (fn [_] (generate-string (feed/counterstream)))
  :available-media-types ["application/json"])

(defresource q2-json
  :allowed-methods [:get]
  :handle-ok (fn [_] (generate-string (feed/q2)))
  :available-media-types ["application/json"])

(defresource yle-json
  :allowed-methods [:get]
  :handle-ok (fn [_] (generate-string (feed/yle)))
  :available-media-types ["application/json"])

(defresource second-inversion-json
  :allowed-methods [:get]
  :handle-ok (fn [_] (generate-string (feed/second-inversion)))
  :available-media-types ["application/json"])

(defroutes home-routes
  (GET "/" request (io/resource "public/index.html"))
  (GET "/counterstream" request counterstream-json)
  (GET "/second-inversion" request second-inversion-json)
  (GET "/q2" request q2-json)
  (GET "/yle" request yle-json))
