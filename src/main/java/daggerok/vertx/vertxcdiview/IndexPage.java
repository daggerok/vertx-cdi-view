package daggerok.vertx.vertxcdiview;

import io.vertx.ext.web.RoutingContext;
import org.jboss.weld.vertx.web.WebRoute;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class IndexPage {

  @WebRoute(value = "/*", order = Integer.MAX_VALUE) // fallback
  void hello(@Observes RoutingContext ctx, Thymeleaf thymeleaf) {
    thymeleaf.render("index.html")
             .accept(ctx);
  }
}
