package daggerok.vertx.vertxcdiview;

import javax.enterprise.context.ApplicationScoped;
import java.util.Objects;
import java.util.Optional;

import static java.util.function.Predicate.not;

@ApplicationScoped
public class GreetingService {

  public String hello(String maybeName) {
    Objects.requireNonNull(maybeName, "name may not be null.");
    var name = Optional.of(maybeName)
                       .filter(not(String::isBlank))
                       .orElse("Buddy");
    return String.format("Hello, %s!", name);
  }
}
