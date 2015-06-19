(set-env!
 :source-paths #{"src"}
 :resource-paths #{"clj"}
 :dependencies '[[adzerk/bootlaces "0.1.11" :scope "test"]
                 [javax.servlet/javax.servlet-api "3.1.0" :scope "provided"]])

(require '[adzerk.bootlaces :refer :all])

(def +version+ "1.0.0")
(bootlaces! +version+)

(require '[clojure.java.io :as io]
         '[boot.core       :as core]
         '[boot.util       :as util]
         '[boot.file       :as file]
         '[boot.pod        :as pod])

(deftask pack-boot []
  (let [tgt (core/tmp-dir!)]
    (core/with-pre-wrap fs
      (core/empty-dir! tgt)
      (let [env {:dependencies [['org.clojure/clojure (clojure-version)]
                                ['boot/pod *boot-version*]
                                ['boot/base *app-version*]]}]
        (util/info "Packing boot jars...\n")
        (doseq [jar (pod/resolve-dependency-jars env)]
          (file/copy-with-lastmod jar (io/file tgt (.getName jar))))
        (-> fs (core/add-resource tgt) core/commit!)))))

(deftask build
  []
  (comp
   (javac)
   (pack-boot)
   (web :context-create 'test-app/start :serve 'test-app/app)
   (uber :as-jars true)
   (war)))

(task-options!
 pom {:project     'adzerk/bootlet
      :version     +version+
      :description "A shim to create a Boot-friendly servlet container interface"
      :url         "https://github.com/adzerk/bootlet"
      :scm         {:url "https://github.com/adzerk/bootlet"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})
