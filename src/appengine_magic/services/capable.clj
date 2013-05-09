(ns appengine-magic.services.capable
  (:import [com.google.appengine.api.capabilities CapabilitiesService 
            CapabilitiesServiceFactory CapabilityStatus Capability
            CapabilityState]))

(defonce ^{:dynamic true} *cap-service* (atom nil))

(defonce ENABLED CapabilityStatus/ENABLED) 

(defonce SCHEDULED CapabilityStatus/SCHEDULED_MAINTENANCE) 

; When maintenance is upcoming .getScheduledDate should give the scheduled date
;
; other possible status values...
; DISABLED
; UNKNOWN


(defn get-cap-service 
  ""
  []
    (when (nil? @*cap-service*)
      (reset! *cap-service* (CapabilitiesServiceFactory/getCapabilitiesService)))
       @*cap-service*)


(defn getServiceStatus
  "-returns true if the service is ENABLED.

   -returns nil if the status is DISABLED or UNKNOWN.

   -returns the Date object containing the date for maintenace,
   when the status is set to SCHEDULED_MAINTENANCE. 
   (Note that such a Date object evaluates to true when used as a boolean."
  [theService]
  (let [capability (.. (get-cap-service) (getStatus theService))
        status (.. capability (getStatus))]
    (cond 
      (= status ENABLED) true
      (= status SCHEDULED) (.. capability .getScheduledDate)
      :else nil)))

 
(defn getBlobstoreStatus [] (getServiceStatus Capability/BLOBSTORE))

(defn getDatastoreStatus [] (getServiceStatus Capability/DATASTORE))

(defn getDatastoreWriteStatus [] (getServiceStatus Capability/DATASTORE_WRITE))

(defn getImageServiceStatus [] (getServiceStatus Capability/IMAGES))

(defn getMailStatus [] (getServiceStatus Capability/MAIL))

(defn getMemcacheStatus [] (getServiceStatus Capability/MEMCACHE))

(defn getTaskQueueStatus [] (getServiceStatus Capability/TASKQUEUE))

(defn getUrlFetchStatus [] (getServiceStatus Capability/URL_FETCH))

(defn getXMPPStatus [] (getServiceStatus Capability/XMPP))

