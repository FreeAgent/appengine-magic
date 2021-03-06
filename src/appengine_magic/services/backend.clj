(ns appengine-magic.services.backend
  (:import [com.google.appengine.api.backends 
            BackendService BackendServiceFactory]))

(defonce ^{:dynamic true} *backend-service* (atom nil))


(defn get-backed-service 
  "get a handle on the App Engine service for backend instances"
  []
    (when (nil? @*backend-service*)
      (reset! *backend-service* (BackendServiceFactory/getBackendService)))
      @*backend-service*)


(defn get-backend-address
  "get the address of a backend, given its name (& optionally the instance no."
  [backend]
  (.. (get-backed-service) (getBackendAddress backend))
  [backend, instance]
  (.. (get-backed-service) (getBackendAddress backend instance)))


(defn get-current-backend
  "Get the name of the backend handling the current request."
  []
  (.. (get-backed-service) (getCurrentBackend)))


(defn get-current-instance
  "returns an integer: the backend instance handling the current request"
  []
  (.. (get-backed-service) (getCurrentInstance)))

