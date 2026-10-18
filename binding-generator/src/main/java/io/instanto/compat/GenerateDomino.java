package io.instanto.compat;

import java.nio.file.*;
import java.util.*;

/** Selects only Domino widget sources for the shared JsInterop transformer. */
public final class GenerateDomino {
  private static void write(Path path, String source) throws Exception {
    Files.createDirectories(path.getParent());
    Files.writeString(
        path,
        GenerateBindings.transform(adaptBrowserRuntime(path, adaptDetailsRenderer(path, source))));
  }

  /** Adapt the GWT runtime hooks added in Domino UI 2.1.3. */
  private static String adaptBrowserRuntime(Path path, String source) {
    return switch (path.getFileName().toString()) {
      case "Tooltip.java" ->
          source
              .replace(
                  "import org.gwtproject.timer.client.Timer;",
                  "import org.dominokit.domino.ui.utils.DominoTimer;")
              .replaceAll("\\bTimer\\b", "DominoTimer");
      // TeaVM is ahead-of-time compiled; GWT's development-mode preloading does not apply.
      case "DynamicCss.java" ->
          source
              .replace("import com.google.gwt.core.client.GWT;", "")
              .replace(
                  "shouldPreloadStaticUtilities(GWT.isScript(), GWT.isProdMode())",
                  "shouldPreloadStaticUtilities(true, true)");
      // Avoid allocating a split array for every unrelated rule on utility-heavy pages.
      // A matching comma-separated selector must contain the complete requested selector.
      case "CssomDynamicCssStyleSheet.java" ->
          source.replace(
              "for (String candidate : selectorText.split(\",\")) {",
              "if (!selectorText.contains(selector)) { return false; }\n"
                  + "    for (String candidate : selectorText.split(\",\")) {");
      default -> source;
    };
  }

  /** Keep the legacy details renderer attached to the record's actual table row. */
  private static String adaptDetailsRenderer(Path path, String source) {
    String expected;
    String replacement;
    switch (path.getFileName().toString()) {
      case "RecordDetailsCellInfo.java" -> {
        expected = "private final TableRow<T> targetRow;";
        replacement =
            expected
                + "\n\n  /** Returns the record row expanded by this details panel. */\n"
                + "  public TableRow<T> getTargetRow() { return targetRow; }";
      }
      case "RecordDetailsPlugin.java" -> {
        expected = "new RowCellInfo<>(cell.getTableRow(), cell.element())";
        replacement = "new RowCellInfo<>(cell.getCellInfo().getTargetRow(), cell.element())";
      }
      default -> {
        return source;
      }
    }
    if (!source.contains(expected)) {
      throw new IllegalStateException("Review the details-renderer adaptation for " + path);
    }
    return source.replace(expected, replacement);
  }

  public static void main(String[] args) throws Exception {
    Path root = Path.of(args[0]);
    Path out = root.resolve("target/compat");
    if (Files.exists(out))
      try (var files = Files.walk(out)) {
        for (Path p : files.sorted(Comparator.reverseOrder()).toList()) Files.delete(p);
      }
    Path sourceRoot = root.resolve("target/intake/java");
    try (var files = Files.walk(sourceRoot)) {
      for (Path p :
          files
              .filter(
                  p ->
                      p.toString().endsWith(".java")
                          && !sourceRoot.relativize(p).toString().startsWith("org/gwtproject/"))
              .sorted()
              .toList())
        write(
            out.resolve(
                    sourceRoot.relativize(p).toString().startsWith("org/gwtproject/")
                        ? "services"
                        : "domino")
                .resolve(sourceRoot.relativize(p)),
            Files.readString(p));
    }
    for (String library : List.of("history", "md", "jackson", "logger")) {
      Path input = root.resolve("target/intake/" + library + "/java");
      try (var files = Files.walk(input)) {
        for (Path p : files.filter(p -> p.toString().endsWith(".java")).sorted().toList()) {
          String source = Files.readString(p);
          if (library.equals("jackson")) {
            String name = p.getFileName().toString();
            if (name.equals("DefaultDateFormat.java")) {
              source =
                  source
                      .replace(
                          "new Date(date.getTime()).toInstant()",
                          "java.time.Instant.ofEpochMilli(date.getTime())")
                      .replace("date.toInstant()", "java.time.Instant.ofEpochMilli(date.getTime())")
                      .replace(
                          "Date.from(Instant.from(format.parse(date)))",
                          "new Date(Instant.from(format.parse(date)).toEpochMilli())");
            }
            if (name.equals("DefaultDateKeyParser.java")) {
              source =
                  source.replace(
                      "Date.from(Instant.from(ISO_8601_FORMAT.parse(keyValue)))",
                      "new Date(Instant.from(ISO_8601_FORMAT.parse(keyValue)).toEpochMilli())");
            }
            if (Set.of(
                    "JsDateFormat.java",
                    "JsDateKeyParser.java",
                    "GwtJacksonJsonSerializerParameters.java")
                .contains(name)) continue;
            if (name.equals("JsJacksonContext.java")) {
              // TeaVM supports the upstream Java runtime; GWT-only timezone APIs are unnecessary.
              source =
                  source
                      .replace("JsDateFormat", "DefaultDateFormat")
                      .replace("JsDoubleArrayReader", "DefaultDoubleArrayReader")
                      .replace("JsIntegerArrayReader", "DefaultIntegerArrayReader")
                      .replace("JsShortArrayReader", "DefaultShortArrayReader")
                      .replace("JsStringArrayReader", "DefaultStringArrayReader")
                      .replace("JsMapLike", "DefaultMapLike")
                      .replace("JsIntegerStack", "DefaultIntegerStack")
                      .replace("JSON::stringify", "new ServerValueStringifier()")
                      .replace(
                          "GwtJacksonJsonSerializerParameters",
                          "ServerJacksonJsonSerializerParameters")
                      .replace(
                          "GwtJacksonJsonDeserializerParameters",
                          "ServerJacksonJsonDeserializerParameters");
            }
          }
          if (library.equals("logger")
              && p.getFileName().toString().equals("DominoLoggingAdapter.java")) {
            // GWT's string identity assumptions are invalid for ordinary Java/TeaVM strings.
            for (String level : List.of("TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF")) {
              source =
                  source
                      .replace("level != \"" + level + "\"", "!level.equals(\"" + level + "\")")
                      .replace("level == \"" + level + "\"", "level.equals(\"" + level + "\")");
            }
            source =
                source
                    .replace(
                        "INFO_ENABLED = level.equals(\"TRACE\") || level.equals(\"INFO\");",
                        "INFO_ENABLED = level.equals(\"TRACE\") || level.equals(\"DEBUG\") || level.equals(\"INFO\");")
                    .replace(
                        "DEBUG_ENABLED = level.equals(\"TRACE\") || level.equals(\"INFO\") || level.equals(\"DEBUG\");",
                        "DEBUG_ENABLED = level.equals(\"TRACE\") || level.equals(\"DEBUG\");");
            var markerMethods =
                java.util.regex.Pattern.compile(
                    "public void (trace|debug|info|warn|error)\\(Marker marker, ([^)]*)\\) \\{");
            source =
                markerMethods
                    .matcher(source)
                    .replaceAll(
                        match ->
                            match.group()
                                + " if (!isEnabledForLevel(Level."
                                + match.group(1).toUpperCase(Locale.ROOT)
                                + ")) return;");
            source =
                source.replace(
                    "implements Logger {",
                    "implements Logger, org.slf4j.spi.LoggingEventAware {\n"
                        + "  @Override public void log(org.slf4j.event.LoggingEvent event) {\n"
                        + "    io.instanto.domino.logging.TeaVmLogEvents.log(this, event);\n  }\n");
          }
          Path destination = out.resolve(library).resolve(input.relativize(p));
          Files.createDirectories(destination.getParent());
          Files.writeString(destination, GenerateBindings.transform(source));
        }
      }
    }
    StringBuilder manifest = new StringBuilder();
    var digest = java.security.MessageDigest.getInstance("SHA-256");
    try (var files = Files.walk(out)) {
      for (Path p : files.filter(p -> p.toString().endsWith(".java")).sorted().toList()) {
        manifest
            .append(java.util.HexFormat.of().formatHex(digest.digest(Files.readAllBytes(p))))
            .append("  ")
            .append(out.relativize(p))
            .append("\n");
      }
    }
    Files.writeString(out.resolve("sources.sha256"), manifest);
    System.out.println("Generated TeaVM bindings and Domino sources in " + out);
  }
}
