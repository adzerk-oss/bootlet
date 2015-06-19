(set-env!
 :source-paths #{"src"}
 :resource-paths #{"clj"}
 :dependencies '[[adzerk/bootlaces "0.1.11" :scope "test"]
                 [javax.servlet/javax.servlet-api "3.1.0" :scope "provided"]])

(require '[adzerk.bootlaces :refer :all])

(def +version+ "2.0.1")
(bootlaces! +version+)

(require '[adzerk.bootlet :as bootlet])

(deftask build
  "Build the library for distribution."
  []
  (comp
   (javac)
   (build-jar)))

(deftask try
  "Make a test bootlet war."
  []
  (comp
   (javac)
   (bootlet/pack-pod-runtime)
   (web :context-create 'adzerk.bootlet.test-app/start
        :serve          'adzerk.bootlet.test-app/app)
   (uber :as-jars true)
   (war)))

(task-options!
 pom {:project     'adzerk/bootlet
      :version     +version+
      :description "A shim to create a Boot-friendly servlet container interface"
      :url         "https://github.com/adzerk-oss/bootlet"
      :scm         {:url "https://github.com/adzerk-oss/bootlet"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})
