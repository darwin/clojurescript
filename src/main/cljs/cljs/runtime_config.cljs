(ns cljs.runtime-config
  (:require [cljs.reader :as reader]))

(goog-define
  ^{:dynamic true
    :doc     "TODO"}
  *serialized-runtime-config* "{}")

(defn- unserialize-edn [edn-string]
  (try
    (reader/read-string edn-string)
    (catch :default e
      (maybe-warn e))))

(defn parse-runtime-config []
  (unserialize-edn *serialized-runtime-config*))

(def memoized-parse-runtime-config (memoize parse-runtime-config))

(defn- get-runtime-config* [& args]
  (let [config (memoized-parse-runtime-config)]
    (apply get-in config args)))

(defn get-runtime-config
  ([] (get-runtime-config* []))
  ([ks] (get-runtime-config* ks))
  ([ks not-found] (get-runtime-config* ks not-found)))
