(ns appengine-magic.services.capable
  (:import [com.google.appengine.api.capabilities CapabilitiesService 
            CapabilitiesServiceFactory CapabilityStatus Capability
            CapabilityState]))

(defonce ^{:dynamic true} *cap-service* (atom nil))

; TBD: doc-strings
; TBD: modify isAvailable to return a Date if the status is Scheduled?
; TBD: then change the function-names below to getXYZStatus

(defonce CAP_ENABLED CapabilityStatus/ENABLED) 

(defonce CAP_SCHEDULED CapabilityStatus/SCHEDULED_MAINTENANCE) 

; When maintenance is upcoming .getScheduledDate should give the scheduled date
;
; other possible status values...
; DISABLED
; UNKNOWN


(defn get-cap-service []
    (when (nil? @*cap-service*)
      (reset! *cap-service* (CapabilitiesServiceFactory/getCapabilitiesService)))
       @*cap-service*)


(defn isServiceAvailable [theService]
  (let [status (.. (get-cap-service) (getStatus theService) (getStatus))]
    (if (= status CAP_ENABLED) true false)))


(defn isBlobstoreAvailable [] (isServiceAvailable Capability/BLOBSTORE))

(defn isDatastoreAvailable [] (isServiceAvailable Capability/DATASTORE))

(defn isDatastoreWriteAvailable [] (isServiceAvailable Capability/DATASTORE_WRITE))

(defn isImageServiceAvailable [] (isServiceAvailable Capability/IMAGES))

(defn isMailAvailable [] (isServiceAvailable Capability/MAIL))

(defn isMemcacheAvailable [] (isServiceAvailable Capability/MEMCACHE))

(defn isTaskQueueAvailable [] (isServiceAvailable Capability/TASKQUEUE))

(defn isUrlFetchAvailable [] (isServiceAvailable Capability/URL_FETCH))

(defn isXMPPAvailable [] (isServiceAvailable Capability/XMPP))


(defn getDowntimeDate [theService]
   (let [capability (.. (get-cap-service) (getStatus theService))
         status (.. capability (getStatus))]
     (if (= status CAP_SCHEDULED) 
       (.. capability .getScheduledDate)
       nil)))

