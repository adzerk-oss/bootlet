package tailrecursion;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.File;
import boot.App;
import org.projectodd.shimdandy.ClojureRuntimeShim;

public class ClojureAdapterServletContextListener implements ServletContextListener {

  public static ClojureRuntimeShim servletPod;

  static {
    try {
      servletPod = App.newPod();
    } catch (Exception e) {
      e.printStackTrace();
    }
    servletPod.require("tailrecursion.clojure-adapter-servlet.impl");
  }

  public void contextInitialized(ServletContextEvent sce) {
    servletPod.invoke("tailrecursion.clojure-adapter-servlet.impl/context-initialized", sce);
  }

  public void contextDestroyed(ServletContextEvent sce) {
    servletPod.invoke("tailrecursion.clojure-adapter-servlet.impl/context-destroyed", sce);
  }
}
