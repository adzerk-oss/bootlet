# boot adapter servlet
a shim to create a boot-friendly servlet container interface.

Work in progress!  Based on [tailrecursion/clojure-adapter-servlet](https://github.com/tailrecursion/clojure-adapter-servlet)

<img src="http://animatedgif.net/underconstruction/anim0205-1_e0.gif"/>

    boot build
    java -jar jetty-runner-9.2.9.v20150224.jar target/project.war

## notes

* need to load App at the topmost classloader available in the servlet
* factor boot.App/main into two methods, one of which doesn't know about CLI (for use in containers etc)
