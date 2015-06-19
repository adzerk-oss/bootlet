package tailrecursion;

import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import boot.App;
import org.projectodd.shimdandy.ClojureRuntimeShim;

public class ClojureAdapterServlet extends GenericServlet {

  public static ClojureRuntimeShim servletPod;

  static {
    if (ClojureAdapterServletContextListener.servletPod == null) {
      try {
        servletPod = App.newPod();
        servletPod.require("tailrecursion.clojure-adapter-servlet.impl");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      servletPod = ClojureAdapterServletContextListener.servletPod;
    }
  }


  @Override
  public void init(ServletConfig conf) throws NullPointerException, ServletException {
    servletPod.invoke("tailrecursion.clojure-adapter-servlet.impl/init", conf);
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) throws IOException, ServletException {
    servletPod.invoke("tailrecursion.clojure-adapter-servlet.impl/service", req, res);
  }

  @Override
  public void destroy() {
    servletPod.invoke("tailrecursion.clojure-adapter-servlet.impl/destroy");
  }
}
