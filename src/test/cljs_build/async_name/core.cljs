(ns async-name.core
  (:require [async-name.async.subns]
            [goog.async.nextTick]))

(defn async-thing []
  (let [async (fn [])]
    (async)))

(async-thing)

(let [x async-name.async.subns/some-var])

(goog.async.nextTick identity)
