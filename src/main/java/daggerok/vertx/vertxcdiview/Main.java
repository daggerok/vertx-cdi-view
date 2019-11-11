package daggerok.vertx.vertxcdiview;

import io.vertx.core.Vertx;
import org.jboss.weld.vertx.web.WeldWebVerticle;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.util.Optional;

public class Main {

  public static void main(String[] args) {
    var port = Optional.ofNullable(System.getProperty("http.port", null))
                       .map(Integer::valueOf)
                       .orElse(Optional.ofNullable(System.getenv("HTTP_PORT"))
                                       .map(Integer::valueOf)
                                       .orElse(8080));
    var vertx = Vertx.vertx();
    var weldVerticle = new WeldWebVerticle();
    vertx.deployVerticle(weldVerticle, ready -> {
      if (ready.failed()) return;
      System.out.printf("Configuring routes on port %d...%n", port);
      vertx.createHttpServer()
           // .requestHandler(weldVerticle.createRouter()::accept)
           .requestHandler(weldVerticle.createRouter())
           .listen(port);
    });
  }

  private void on(@Observes @Initialized(ApplicationScoped.class) Object initialized) {
    System.out.println(initialized);
  }
}
