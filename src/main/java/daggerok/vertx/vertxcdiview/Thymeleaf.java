package daggerok.vertx.vertxcdiview;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

@ApplicationScoped
public class Thymeleaf {

  @Inject
  private Vertx vertx;

  @Inject
  private Context context;

  private TemplateEngine engine;

  @PostConstruct
  public void init() {
    engine = io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine.create(vertx);
  }

  @Produces
  @Singleton
  private TemplateEngine engine() {
    return engine;
  }

  public Consumer<RoutingContext> render(String maybeView, Map<String, Object> mabyMap) {
    Function<RoutingContext, Map<String, Object>> within = ctx ->
      Stream.concat(Map.of("ctx", ctx, "context", context).entrySet().stream(),
                    Optional.ofNullable(mabyMap).orElse(Map.of()).entrySet().stream())
            .collect(Collectors.toMap(Map.Entry::getKey,
                                      Map.Entry::getValue));
    String view = Optional.ofNullable(maybeView)
                          .map(String::trim)
                          .filter(not(String::isBlank))
                          .orElse("index.html");
    return ctx -> engine.render(within.apply(ctx), "templates/" + view, res -> {
      if (res.failed()) ctx.fail(res.cause());
      else ctx.response().end(res.result());
    });
  }

  public Consumer<RoutingContext> render(Map<String, Object> maybeMap) {
    return render(null, maybeMap);
  }

  public Consumer<RoutingContext> render(String maybeView) {
    return render(maybeView, null);
  }

  public Consumer<RoutingContext> render() {
    return render(null, null);
  }
}
