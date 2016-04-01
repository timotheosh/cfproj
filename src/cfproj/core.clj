(ns cfproj.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])
  (:gen-class))

(def cli-options
  [["-d" "--directory DIR" "Directory to traverse"
    :parse-fn #(str %)]
   ["-h" "--help"]])

(defn walk [dirpath pattern]
  (doall (filter #(re-matches pattern (.getName %))
                 (file-seq (io/file dirpath)))))

()

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "List of json files:")
  (let [path (get-in (parse-opts args cli-options) [:options :directory])]
    (println (str "path: " path))
    (let [files (into [] (walk path #"cf-parameters-.*\.json"))]
      (println (str "FILES: " (get files 0)))
      (let [jdat (json/read (io/reader (get files 0)) :key-fn keyword)]
        (println jdat)
      ))))
