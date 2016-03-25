(ns inline-truth.core)

; this is officially a side-effecting function
; expectation: str is not marked as @nosideeffects
(defn f! [s]
  (str s))

(def var01 (f! "var01 should be present"))

(def var02 (if js/goog.DEBUG (f! "var02 should be elided")))

(defn ^boolean local-wrapper-works []
  (truth_ js/goog.DEBUG))

(def var03 (if (local-wrapper-works) (f! "var03 should be elided")))