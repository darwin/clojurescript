(ns runtime-config-test.core
  (:require [cljs.runtime-config :as config]))

(goog-define foo "default value")

(println "RUNTIME CONFIG:" (config/get-runtime-config))
