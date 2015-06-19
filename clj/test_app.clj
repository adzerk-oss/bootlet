(ns test-app
  (:require [boot.pod :as pod]))

(def pod (atom nil))

(defn start []
  (println "Starting a pod!")
  (reset! pod (pod/make-pod)))

(defn app [req]
  {:status 200
   :body (str "Pod = " @pod)})
