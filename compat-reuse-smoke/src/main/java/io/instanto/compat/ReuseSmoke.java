package io.instanto.compat;

import elemental2.dom.DomGlobal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.dominokit.domino.logger.*;
import org.slf4j.*;
import org.slf4j.event.Level;

/** Exercises a second published library without any widget dependencies. */
public final class ReuseSmoke {
  public static void main(String[] args) throws Exception {
    String query = DomGlobal.location.search;
    String level = query.startsWith("?page=") ? query.substring(6) : "INFO";
    System.setProperty("domino.slf4j.logging.level", new String(level.toCharArray()));
    Logger logger = LoggerFactory.getLogger("compat-reuse");
    require(logger instanceof DominoLoggingAdapter, "Domino provider selected");
    require(
        LoggerFactory.getLogger(ReuseSmoke.class).getName().equals(ReuseSmoke.class.getName()),
        "class logger");
    require(
        LoggerFactory.getILoggerFactory().getLogger("factory").getName().equals("factory"),
        "factory access");
    checkLevels(logger, level);
    ready("levels");
    if (!level.equals("INFO")) return;
    logger.info("compat-reuse-{}", "success");
    logger.info("array {} and {}", new int[] {1, 2}, "value");
    logger.atInfo().addArgument("fluent").log("compat-{}-success");
    ready("api");
    checkRouting(logger);
    ready("routing");
    checkMdc(logger);
    ready("mdc");
    DomGlobal.document.body.setAttribute("data-reuse", "ready");
  }

  private static void checkLevels(Logger logger, String configured) {
    List<Event> events = new ArrayList<>();
    Marker marker = MarkerFactory.getMarker("LEVELS");
    LoggingRouter.register("LEVELS", capture(events));
    int threshold =
        configured.equals("OFF") ? Integer.MAX_VALUE : Level.valueOf(configured).toInt();
    int expected = 0;
    for (Level level : Level.values()) {
      boolean enabled = level.toInt() >= threshold;
      require(logger.isEnabledForLevel(level) == enabled, "level flag " + level);
      switch (level) {
        case TRACE -> logger.trace(marker, "level {}", level);
        case DEBUG -> logger.debug(marker, "level {}", level);
        case INFO -> logger.info(marker, "level {}", level);
        case WARN -> logger.warn(marker, "level {}", level);
        case ERROR -> logger.error(marker, "level {}", level);
      }
      if (enabled) expected++;
      require(events.size() == expected, "marker level filtering " + level);
    }
    int[] evaluated = {0};
    logger.atTrace().addArgument(() -> ++evaluated[0]).log("lazy {} ");
    require(evaluated[0] == (configured.equals("TRACE") ? 1 : 0), "disabled fluent supplier");
    LoggingRouter.clear();
  }

  private static void checkRouting(Logger logger) {
    List<Event> events = new ArrayList<>();
    Marker audit = MarkerFactory.getMarker("AUDIT");
    Marker child = MarkerFactory.getDetachedMarker("CHILD");
    child.add(audit);
    LoggingRouter.register("AUDIT", capture(events));
    Throwable problem = new IllegalStateException("expected-cause");
    logger.info(audit, "order {}", 42);
    require(events.get(0).message.contains("order 42"), "placeholder formatting");
    logger.warn(child, "failed {}", "request", problem);
    require(events.get(1).throwable == problem, "trailing throwable");
    require(events.get(1).marker == child, "hierarchical marker");
    logger.error(audit, "explicit cause", problem);
    require(events.get(2).throwable == problem, "explicit throwable");
    logger.atInfo().addMarker(audit).addKeyValue("order", 42).addArgument("fluent").log("{} route");
    require(events.size() == 4, "fluent marker routing");
    require(
        events.get(3).message.contains("fluent route")
            && events.get(3).message.contains("order=42"),
        "fluent values");
    logger
        .atWarn()
        .addMarker(child)
        .addMarker(MarkerFactory.getMarker("EXTRA"))
        .setCause(problem)
        .log("multiple markers");
    require(events.size() == 5 && events.get(4).throwable == problem, "fluent hierarchy and cause");
    require(!child.contains("EXTRA"), "application markers remain unchanged");
    List<Event> fallback = new ArrayList<>();
    LoggingRouter.setDefaultAdapter(capture(fallback));
    logger.info(MarkerFactory.getMarker("OTHER"), "fallback");
    require(fallback.size() == 1, "default marker adapter");
    LoggingRouter.unregister("AUDIT");
    logger.info(audit, "unregistered");
    require(fallback.size() == 2, "unregister adapter");
    LoggingRouter.setDefaultAdapter(null);
    LoggingRouter.clear();
  }

  private static void checkMdc(Logger logger) throws Exception {
    MDC.clear();
    MDC.put("request", "original");
    Map<String, String> snapshot = MDC.getCopyOfContextMap();
    snapshot.put("request", "changed");
    require("original".equals(MDC.get("request")), "defensive MDC snapshot");
    MDC.remove("request");
    List<Event> events = new ArrayList<>();
    Marker marker = MarkerFactory.getMarker("MDC");
    LoggingRouter.register("MDC", capture(events));
    try (var outer = MDCUtils.withMdc("request", "outer")) {
      try {
        try (var inner = MDCUtils.withMdc(Map.of("request", "inner", "user", "alice"))) {
          logger.info(marker, "scoped");
          require(MDCUtils.getMdcStack("request").size() == 2, "nested MDC stack");
          throw new IllegalStateException("scope failure");
        }
      } catch (IllegalStateException expected) {
        require("outer".equals(MDC.get("request")), "restore after exception");
        require(MDC.get("user") == null, "remove scoped user");
      }
      require("inner".equals(events.get(0).mdc.get("request")), "event snapshot survives scope");
    }
    require(MDC.getCopyOfContextMap() == null, "outer scope cleared");
    var scope = MDCUtils.withMdc("request", "once");
    scope.close();
    scope.close();
    require(MDC.get("request") == null, "idempotent scope close");
    MDC.setContextMap(Map.of("restored", "yes"));
    require("yes".equals(MDC.get("restored")), "set context");
    MDC.clear();
    LoggingRouter.clear();
  }

  private static LoggingAdapter capture(List<Event> events) {
    return (level, name, marker, message, throwable, mdc) ->
        events.add(new Event(level, name, marker, message, throwable, mdc));
  }

  private record Event(
      Level level,
      String name,
      Marker marker,
      String message,
      Throwable throwable,
      Map<String, String> mdc) {}

  private static void require(boolean condition, String message) {
    if (!condition) throw new AssertionError(message);
  }

  private static void ready(String feature) {
    DomGlobal.document.body.setAttribute("data-logger-" + feature, "ready");
  }
}
