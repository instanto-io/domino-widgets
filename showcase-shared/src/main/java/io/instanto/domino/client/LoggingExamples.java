package io.instanto.domino.client;

import static io.instanto.domino.client.DemoElements.*;

import elemental2.dom.*;
import java.util.LinkedHashMap;
import java.util.Map;
import org.dominokit.domino.logger.*;
import org.dominokit.domino.ui.forms.TextBox;
import org.slf4j.*;
import org.slf4j.event.Level;

/** A visible log sink backed by the port's standard SLF4J entry points. */
public final class LoggingExamples {
  private final HTMLElement root = root("logging-example");
  private final HTMLElement events = element("div", "demo-events", "");
  private final HTMLElement status = element("p", "demo-status", "");
  private final HTMLElement count = element("p", "demo-hint", "No events yet.");
  private final TextBox message = TextBox.create("Message");
  private final TextBox request = TextBox.create("Request ID (MDC)");
  private final Map<Level, HTMLElement> levels = new LinkedHashMap<>();
  private final Map<String, HTMLElement> channels = new LinkedHashMap<>();
  private final Logger logger = LoggerFactory.getLogger("showcase.logging");
  private Level level = Level.INFO;
  private String channel = "APP";
  private int sequence;

  public static HTMLElement render() {
    return new LoggingExamples().build();
  }

  private HTMLElement build() {
    message.setValue("Order 42 saved");
    request.setValue("demo-042");
    HTMLElement grid = element("div", "demo-grid demo-log-grid", "");
    HTMLElement controls =
        panel("Send an event", "Choose a level and a destination, then inspect the event below.");
    message.getInputElement().element().id = "logging-message";
    message.getInputElement().element().setAttribute("aria-label", "Message");
    request.getInputElement().element().id = "logging-request";
    request.getInputElement().element().setAttribute("aria-label", "Request ID");
    controls.appendChild(message.element());
    controls.appendChild(request.element());
    controls.appendChild(element("h3", "demo-field-title", "Level"));
    HTMLElement levelOptions = element("div", "demo-options", "");
    for (Level value :
        new Level[] {Level.TRACE, Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR}) {
      HTMLElement choice =
          button(
              "logging-level-" + value.name().toLowerCase(),
              value.name(),
              () -> {
                level = value;
                selection();
              });
      levels.put(value, choice);
      levelOptions.appendChild(choice);
    }
    controls.appendChild(levelOptions);
    controls.appendChild(
        element(
            "p",
            "demo-hint",
            "The backend threshold is INFO. TRACE and DEBUG are filtered before reaching an adapter."));
    controls.appendChild(element("h3", "demo-field-title", "Marker route"));
    HTMLElement routes = element("div", "demo-options", "");
    for (String name : new String[] {"APP", "AUDIT"}) {
      HTMLElement choice =
          button(
              "logging-route-" + name.toLowerCase(),
              name.equals("APP") ? "Application" : "Audit",
              () -> {
                channel = name;
                selection();
              });
      channels.put(name, choice);
      routes.appendChild(choice);
      LoggingAdapter sink =
          (eventLevel, loggerName, marker, text, cause, mdc) ->
              append(name, eventLevel, loggerName, text, mdc);
      LoggingRouter.register(name, new CompositeLoggingAdapter(sink, new ConsoleLoggingAdapter()));
    }
    controls.appendChild(routes);
    HTMLElement actions = element("div", "demo-toolbar", "");
    actions.appendChild(button("logging-send", "Send event", this::send));
    actions.appendChild(button("logging-nested", "Try nested MDC", this::nested));
    controls.appendChild(actions);
    status.id = "logging-status";
    status.setAttribute("role", "status");
    controls.appendChild(status);
    grid.appendChild(controls);
    HTMLElement output =
        panel(
            "Event stream",
            "The same events also appear in your browser console. Latest 50 shown here.");
    events.id = "logging-events";
    count.id = "logging-count";
    output.appendChild(count);
    output.appendChild(
        button(
            "logging-clear",
            "Clear output",
            () -> {
              events.textContent = "";
              count.textContent = "No events yet. Send a message to begin.";
              status.textContent = "Output cleared.";
            }));
    output.appendChild(events);
    grid.appendChild(output);
    root.appendChild(grid);
    root.appendChild(
        element(
            "p",
            "demo-footnote",
            "Domino Logger 2.0.1 · Standard SLF4J 2 calls · Marker-based adapters · Synchronous MDC scopes"));
    selection();
    send();
    root.setAttribute("data-ready", "true");
    return root;
  }

  private void selection() {
    levels.forEach(
        (value, button) -> button.setAttribute("aria-pressed", String.valueOf(value == level)));
    channels.forEach(
        (value, button) ->
            button.setAttribute("aria-pressed", String.valueOf(value.equals(channel))));
  }

  private void send() {
    try (var scope = MDCUtils.withMdc("requestId", request.getValue())) {
      logger
          .atLevel(level)
          .addMarker(MarkerFactory.getMarker(channel))
          .addArgument(message.getValue())
          .log("{}");
    } catch (Exception error) {
      status.textContent = "Could not close the logging scope: " + error.getMessage();
      return;
    }
    status.textContent =
        logger.isEnabledForLevel(level)
            ? level
                + " sent to "
                + (channel.equals("APP") ? "Application" : "Audit")
                + ". MDC cleared after logging."
            : level + " is below INFO; no event was sent.";
    root.setAttribute("data-mdc-cleared", String.valueOf(MDC.get("requestId") == null));
  }

  private void nested() {
    Marker marker = MarkerFactory.getMarker(channel);
    String id = request.getValue();
    try (var outer = MDCUtils.withMdc("requestId", id)) {
      logger.info(marker, "Outer scope starts");
      try (var inner = MDCUtils.withMdc("requestId", id + "/child")) {
        logger.info(marker, "Inner scope overrides the request ID");
      }
      logger.info(marker, "Outer request ID restored");
    } catch (Exception error) {
      status.textContent = "Could not close the logging scope: " + error.getMessage();
      return;
    }
    status.textContent = "Three INFO events: outer → inner → outer. MDC cleared after logging.";
    root.setAttribute("data-mdc-cleared", String.valueOf(MDC.get("requestId") == null));
  }

  private void append(
      String route, Level eventLevel, String loggerName, String text, Map<String, String> mdc) {
    HTMLElement row = element("article", "demo-log-event", "");
    row.setAttribute("data-level", eventLevel.name());
    row.setAttribute("data-route", route);
    String id = mdc == null ? "—" : mdc.getOrDefault("requestId", "—");
    row.setAttribute("data-request", id);
    HTMLElement meta = element("div", "demo-log-meta", "");
    meta.appendChild(element("span", "demo-level", eventLevel.name()));
    meta.appendChild(element("span", "", route.equals("APP") ? "Application" : "Audit"));
    meta.appendChild(element("span", "demo-log-sequence", "#" + ++sequence));
    row.appendChild(meta);
    row.appendChild(element("pre", "demo-log-message", text));
    row.appendChild(element("p", "demo-log-context", "requestId=" + id + " · " + loggerName));
    events.appendChild(row);
    while (events.childElementCount > 50) events.removeChild(events.firstChild);
    count.textContent =
        events.childElementCount + (events.childElementCount == 1 ? " event" : " events");
  }
}
