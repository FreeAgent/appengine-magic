(ns appengine-magic.services.namespace
  (:import [com.google.appengine.api.NamespaceManager])
  (:require [appengine-magic.services.user :as u]))


(defn get-namespace
  "get the current global namespace (for use by services which support namspacing"
  []
  (.get NamespaceManager))
      

(defn get-google-apps-namespace
  "get the Google Apps namespace"
  []
  (.getGoogleAppsNamespace NamespaceManager))
     

(defn set-namespace
  "set the current global namespace"
  [new-name]
  (.set NamespaceManager new-name))
     

(defn validate-namespace
  "validate a proposed namespace with regular expression [0‑9A‑Za‑z._‑]{0,100}"
  [proposed-name]
  (.validateNamespace NamespaceManager proposed-name))
     

(defn set-namespace-by-user-id
  "set the global namespace based on the user-id of the current user"
  []
  (let [id (u/get-user-id)]
    (set-namespace id)))


(defn set-namespace-by-domain
  "set the global namespace based on the Google Apps domain"
  []
  (set-namespace (get-google-apps-namespace))
  

(defn set-namespace-by-domain-and-user
  "set the global namespace using the Google Apps domain plus the user-id."
  []
  (let [id (u/get-user-id)]
    (set-namespace (str (get-google-apps-namespace) id))))


(defmacro run-in-namespace
     "set the namespace; call a function; then restore the old namespace"
     [temp-namespace, form]
     `(let [old-namespace# (get-namespace)]
       (try
         (set-namespace ~temp-namespace)
         ~form
         (finally (set-namespace old-namespace#)))))

