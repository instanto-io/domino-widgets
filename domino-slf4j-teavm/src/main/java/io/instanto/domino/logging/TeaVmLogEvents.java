package io.instanto.domino.logging;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.MessageFormatter;

/** Preserves fluent markers and values when dispatching through Domino's classic overloads. */
public final class TeaVmLogEvents {
  private TeaVmLogEvents() {}

  public static void log(Logger logger, LoggingEvent event) {
    if (!logger.isEnabledForLevel(event.getLevel())) return;
    var formatted = MessageFormatter.arrayFormat(event.getMessage(), event.getArgumentArray());
    Throwable cause =
        event.getThrowable() != null ? event.getThrowable() : formatted.getThrowable();
    String message = withKeyValuePairs(formatted.getMessage(), event.getKeyValuePairs());
    Marker marker = mergeMarkers(event.getMarkers());
    if (marker == null) {
      switch (event.getLevel()) {
        case TRACE -> logger.trace(message, cause);
        case DEBUG -> logger.debug(message, cause);
        case INFO -> logger.info(message, cause);
        case WARN -> logger.warn(message, cause);
        case ERROR -> logger.error(message, cause);
      }
    } else {
      switch (event.getLevel()) {
        case TRACE -> logger.trace(marker, message, cause);
        case DEBUG -> logger.debug(marker, message, cause);
        case INFO -> logger.info(marker, message, cause);
        case WARN -> logger.warn(marker, message, cause);
        case ERROR -> logger.error(marker, message, cause);
      }
    }
  }

  private static String withKeyValuePairs(String message, List<KeyValuePair> pairs) {
    if (pairs == null || pairs.isEmpty()) return message;
    var values = new StringBuilder();
    for (var pair : pairs) {
      values.append(pair.key).append('=').append(pair.value).append(' ');
    }
    return values.append(message).toString();
  }

  private static Marker mergeMarkers(List<Marker> markers) {
    if (markers == null || markers.isEmpty()) return null;
    Marker first = markers.get(0);
    if (markers.size() == 1) return first;
    // Keep the first marker's routing priority without modifying an application marker.
    Marker merged = MarkerFactory.getDetachedMarker(first.getName());
    // A marker with the same name would hit the router's cycle guard.
    for (var references = first.iterator(); references.hasNext(); ) {
      merged.add(references.next());
    }
    for (int i = 1; i < markers.size(); i++) {
      merged.add(markers.get(i));
    }
    return merged;
  }
}
