(ns appengine-magic.services.backend
  (:import [com.google.appengine.api.backends 
            BackendService BackendServiceFactory]))

(defonce ^{:dynamic true} *backend-service* (atom nil))

; TBD: doc-strings

(defn get-backed-service []
    (when (nil? @*backend-service*)
      (reset! *backend-service* (BackendServiceFactory/getBackendService)))
      @*backend-service*)


(defn getBackendAddress [backend]
  (.. (get-backed-service) (getBackendAddress backend)))

(defn getBackendAddress [backend, instance]
  (.. (get-backed-service) (getBackendAddress backend instance)))

(defn getCurrentBackend()
  (.. (get-backed-service) (getCurrentBackend)))

(defn getCurrentInstance()
  (.. (get-backed-service) (getCurrentInstance)))
