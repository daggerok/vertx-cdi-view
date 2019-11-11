package daggerok.vertx.vertxcdiview;

import io.vertx.ext.web.RoutingContext;
import org.jboss.weld.vertx.web.WebRoute;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class GreetingResource {

  @Inject
  private GreetingService greetingService;

  @WebRoute("/hello")
  void hello(@Observes RoutingContext ctx) {
    var maybeName = ctx.request().params().get("name");
    var name = Optional.ofNullable(maybeName).orElse("");
    var greeting = greetingService.hello(name);
    ctx.response().setStatusCode(200).end(greeting);
  }
}
