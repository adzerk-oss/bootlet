(ns adzerk.bootlet
  {:boot/export-tasks true}
  (:require [clojure.java.io :as io]
            [boot.core       :as core]
            [boot.util       :as util]
            [boot.file       :as file]
            [boot.pod        :as pod]))

(def ^:private deps
  [['org.clojure/clojure  (clojure-version)]
   ['boot/pod             core/*boot-version*]
   ['adzerk.bootlet       "2.0.1"]
   ['alandipert/boot-base "2.0.0-SNAPSHOT"]
   ;; ['boot/base *app-version*]
   ])

(core/deftask pack-pod-runtime
  "Add boot's App and pod files to the fileset so that an alternative
  application entrypoint can provide the pods API at runtime."
  []
  (let [tgt (core/tmp-dir!)
        env {:dependencies deps}
        jars (future (pod/resolve-dependency-jars env))]
    (core/with-pre-wrap fs
      (util/info "Packing boot.pod runtime (pod=%s, app=%s)...\n" core/*boot-version* "2.0.0")
      (when-not (seq (.listFiles tgt))
        (doseq [jar @jars]
          (file/copy-with-lastmod jar (io/file tgt (.getName jar)))))
      (-> fs (core/add-resource tgt) core/commit!))))
