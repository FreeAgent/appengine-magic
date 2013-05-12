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


(defn get-service-status
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

 
(defn get-blobstore-status [] (get-service-status Capability/BLOBSTORE))

(defn get-datastore-status [] (get-service-status Capability/DATASTORE))

(defn get-datastore-write-status [] (get-service-status Capability/DATASTORE_WRITE))

(defn get-image-service-Status [] (get-service-status Capability/IMAGES))

(defn get-mail-status [] (get-service-status Capability/MAIL))

(defn get-memcache-status [] (get-service-status Capability/MEMCACHE))

(defn get-task-queue-status [] (get-service-status Capability/TASKQUEUE))

(defn get-url-fetch-status [] (get-service-status Capability/URL_FETCH))

(defn get-xmpp-status [] (get-service-status Capability/XMPP))

