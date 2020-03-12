(ns example
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [hiccup.core :refer :all]

    ;; whatever things you need
    [cheshire.core]
    [selmer.parser]
    [expound.alpha]
    [ring.middleware.defaults]
    [clojure.core.async]))

(defroutes app
  (GET "/" [] (html [:h1 "Hi User!"]))
  (route/not-found (html [:h1 "Sorry, page not found"])))
