(ns nowplaying.models.feed
  (:require [clojure.data.json :as json]
            [clojure.xml :as xml]))

(def counterstream-url "http://www.live365.com/pls/front?handler=playlist&cmd=view&viewType=xml&handle=amcenter&maxEntries=1")
(def earwaves-url "http://api.somafm.com/recent/earwaves.tre.xml")
(def q2-url "http://www.wqxr.org/api/whats_on/q2/2/")
(def second-inversion-url "http://filesource.abacast.com/king/TRE/inversion2.xml")
(def yle-url "http://yle.fi/radiomanint/LiveXML/r17/item(0).xml")

(defn- get-json
  [response]
  (json/read-str response))

(defn- wrap-feed-errors
  "wrap outside http calls so we can trap them"
  [f]
  (try
    (f)
    (catch Exception e (hash-map :title "" :composer ""))))

(defn- process-json-feed
  "given a url and a translate function, process a json feed"
  [url translation-fn]
  (wrap-feed-errors #(-> url slurp get-json translation-fn)))

(defn- process-xml-feed
  "given a url and a translate function, process an xml feed"
  [url translation-fn]
  (wrap-feed-errors #(-> url xml/parse translation-fn)))

(defn translate-q2
  "translate parsed JSON into title and composer for Q2 Music"
  [data]
  (let [entry ((data "current_playlist_item") "catalog_entry")
        title (entry "title")
        composer ((entry "composer") "name")]
    (hash-map :title title :composer composer)))

(defn q2 [] (process-json-feed q2-url translate-q2))

(defn translate-counterstream
  "translate parsed XML into title and composer for Counterstream Radio"
  [data]
  (let [entry (-> data :content (get 2))
        title (-> entry :content (get 0) :content first)
        composer (-> entry :content (get 1) :content first)]
    (hash-map :title title :composer composer)))

(defn counterstream [] (process-xml-feed counterstream-url translate-counterstream))

(defn translate-earwaves
  "translate parsed XML into title and composer for Earwaves"
  [data]
  (let [entry (data :content)
        title (-> entry (get 2) :content first)
        composer (-> entry (get 1) :content first)]
    (hash-map :title title :composer composer)))

(defn earwaves [] (process-xml-feed earwaves-url translate-earwaves))

(defn translate-yle
  "translate parsed XML into title and composer for YLE Klassinen"
  [data]
  (let [entry (-> data :content first)
        title (-> entry :attrs :TITLE)
        composer (-> entry :content first :content second :content second :content first)]
        (hash-map :title title :composer composer)))

(defn yle [] (process-xml-feed yle-url translate-yle))

(defn translate-second-inversion
  "translate parsed XML into title and composer for Second Inversion"
  [data]
  (let [entry (-> data :content first :content)
        title (-> entry (get 4) :content first)
        composer (-> entry (get 3) :content first)]
        (hash-map :title title :composer composer)))

(defn second-inversion [] (process-xml-feed second-inversion-url translate-second-inversion))
