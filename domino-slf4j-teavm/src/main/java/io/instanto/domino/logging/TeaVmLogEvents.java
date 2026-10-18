package io.instanto.domino.logging;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
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
    String message = formatted.getMessage();
    if (event.getKeyValuePairs() != null && !event.getKeyValuePairs().isEmpty()) {
      var values = new StringBuilder();
      for (var value : event.getKeyValuePairs()) {
        values.append(value.key).append('=').append(value.value).append(' ');
      }
      message = values.append(message).toString();
    }
    Marker marker = null;
    if (event.getMarkers() != null && !event.getMarkers().isEmpty()) {
      marker = event.getMarkers().get(0);
      if (event.getMarkers().size() > 1) {
        // Keep the first marker's routing priority and include the remaining references,
        // without modifying markers held by the application.
        Marker first = marker;
        marker = MarkerFactory.getDetachedMarker(first.getName());
        // Copy its references rather than adding first itself: the router's cycle guard
        // treats two markers with the same name as the same visited node.
        for (var references = first.iterator(); references.hasNext(); )
          marker.add(references.next());
        for (int i = 1; i < event.getMarkers().size(); i++) marker.add(event.getMarkers().get(i));
      }
    }
    switch (event.getLevel()) {
      case TRACE -> {
        if (marker == null) logger.trace(message, cause);
        else logger.trace(marker, message, cause);
      }
      case DEBUG -> {
        if (marker == null) logger.debug(message, cause);
        else logger.debug(marker, message, cause);
      }
      case INFO -> {
        if (marker == null) logger.info(message, cause);
        else logger.info(marker, message, cause);
      }
      case WARN -> {
        if (marker == null) logger.warn(message, cause);
        else logger.warn(marker, message, cause);
      }
      case ERROR -> {
        if (marker == null) logger.error(message, cause);
        else logger.error(marker, message, cause);
      }
    }
  }
}
