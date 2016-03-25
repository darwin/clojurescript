(ns cljs.optimization-tests
  (:refer-clojure :exclude [compile])
  (:use cljs.build.api)
  (:use clojure.test)
  (:require [clojure.java.io :as io]))

(deftest cljs-inline-truth
  (let [out-file (io/file "out/optimizations/inline_truth/build.js")
        reporter (fn [checker var]
                   (or (checker var)
                       (do
                         (println (str "'" var "' check failed"))
                         false)))
        vars-present? (fn [output & vars]
                        (let [present? #(re-find (re-pattern (str % " should be present")) output)]
                          (every? (partial reporter present?) vars)))
        vars-elided? (fn [output & vars]
                       (let [elided? #(not (re-find (re-pattern (str % " should be elided")) output))]
                         (every? (partial reporter elided?) vars)))]
    (.delete out-file)
    (build (inputs "src/test/optimizations")
           {:optimizations   :advanced
            :closure-defines {"goog.DEBUG" false}
            :pseudo-names    true
            :pretty-print    true
            :verbose         true
            :output-to       (str out-file)})
    (let [output (slurp out-file)]
      (is (vars-present? output "var01"))
      (is (vars-elided? output "var02" "var03")))))
