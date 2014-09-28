(ns nowplaying.test.handler
  (:use clojure.test
        ring.mock.request
        nowplaying.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (slurp (:body response)) "Q2 Music"))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
