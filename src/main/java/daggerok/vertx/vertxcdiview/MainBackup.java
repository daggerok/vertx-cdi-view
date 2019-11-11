// package daggerok.vertx.vertxcdiview;
//
// import io.vertx.core.AbstractVerticle;
// import io.vertx.core.Promise;
// import io.vertx.core.Vertx;
// import org.jboss.weld.vertx.WeldVerticle;
//
// import javax.enterprise.context.ApplicationScoped;
// import javax.enterprise.context.Initialized;
// import javax.enterprise.event.Observes;
// import javax.inject.Inject;
// import java.util.Optional;
//
// @ApplicationScoped
// public class MainBackup extends AbstractVerticle {
//
//   @Inject
//   GreetingService greetingService;
//
//   @Override
//   public void start(Promise<Void> startPromise) throws Exception {
//     var port = Optional.ofNullable(System.getProperty("http.port", null))
//                        .map(Integer::valueOf)
//                        .orElse(Optional.ofNullable(System.getenv("HTTP_PORT"))
//                                        .map(Integer::valueOf)
//                                        .orElse(8080));
//     vertx.createHttpServer()
//          .requestHandler(req -> req.response()
//                                    .putHeader("content-type", "text/plain")
//                                    .end(String.format("Hello %s from Vert.x!",
//                                                       greetingService.hello(Optional.ofNullable(req.params()
//                                                                                                    .get("name"))
//                                                                                     .orElse("Anonymous")))))
//          .listen(port, http -> {
//            if (http.failed()) startPromise.fail(http.cause());
//            else startPromise.complete();
//            System.out.printf("system is running on port %d%n", port);
//          });
//   }
//
//   public static void main(String[] args) {
//     var vertx = Vertx.vertx();
//     var weldVerticle = new WeldVerticle();
//     vertx.deployVerticle(weldVerticle, ready -> {
//       if (ready.failed()) return;
//       System.out.println("Deploying main verticle...");
//       var mainVerticle = weldVerticle.container().select(MainBackup.class).get();
//       vertx.deployVerticle(mainVerticle);
//     });
//   }
//
//   private void on(@Observes @Initialized(ApplicationScoped.class) Object initialized) {
//     System.out.println(initialized);
//   }
// }
