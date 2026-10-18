# Independent DominoKit libraries for TeaVM

The reactor now provides five additional libraries under `io.instanto`, all at
port version `0.1.0-SNAPSHOT`. Their public packages remain unchanged.

| Artifact | Upstream release | Runtime dependencies |
| --- | --- | --- |
| `domino-history-teavm` | Domino History 1.0.6 | Upstream `domino-history-shared:1.0.6`, shared Elemental2 compatibility runtime |
| `domino-md-teavm` | domino-md 1.0.0 | Shared Elemental2 compatibility runtime |
| `domino-jackson-teavm` | Domino Jackson 1.0.6 | Jackson annotations, shared Elemental2 compatibility runtime |
| `domino-rest-teavm` | Domino REST 2.0.2 | Upstream REST shared model, ported Jackson runtime, TeaVM JSO, SLF4J API |
| `domino-slf4j-teavm` | Domino SLF4J Logger 2.0.1 | Standard SLF4J API 2.0.16, shared Elemental2 compatibility runtime |

None of these libraries depends on Domino UI, its CSS, or its assets. The
`domino-widgets-bom` manages their port versions. After importing that BOM,
add the libraries you need:

```xml
<dependency>
  <groupId>io.instanto</groupId>
  <artifactId>domino-history-teavm</artifactId>
</dependency>
<dependency>
  <groupId>io.instanto</groupId>
  <artifactId>domino-md-teavm</artifactId>
</dependency>
```

Use these artifacts in place of `org.dominokit:domino-history-client` and
`org.dominokit:domino-md`, respectively, to avoid duplicate implementation classes.
The History shared model is intentionally reused from upstream. JVM applications
can continue using upstream `domino-history-jvm` with that same shared model.

## History

```java
import org.dominokit.domino.client.history.StateHistory;
import org.dominokit.domino.history.StateToken;
import org.dominokit.domino.history.TokenFilter;

StateHistory history = new StateHistory();
history.listen(TokenFilter.startsWithPathFilter("orders"), state -> {
    String token = state.token().value();
    // Display the requested order.
});
history.fireState(StateToken.of("orders/42").title("Order 42").data("order-state"));
```

Navigation guards should call `chain.next()` only when navigation is allowed.
In upstream 1.0.6, `TokenEvent.cancel()` stops the remaining interceptors but
still invokes the completion callback; it does **not** block navigation.
This port preserves that behavior.

## Markdown

```java
import elemental2.dom.DomGlobal;
import org.dominokit.markdown.extensions.discovery.ExtensionDiscovery;
import org.dominokit.markdown.parser.Parser;
import org.dominokit.markdown.renderer.elemental2.Elemental2Renderer;

var extensions = ExtensionDiscovery.load();
var parser = Parser.builder().extensions(extensions).build();
var renderer = Elemental2Renderer.builder()
    .extensions(extensions)
    .sanitizeUrls(true)
    .build();
DomGlobal.document.body.appendChild(renderer.render(parser.parse("Hello **TeaVM**")));
```

`HtmlRenderer`, `TextContentRenderer`, and `MarkdownRenderer` are also available.
The bundled extensions include tables, task lists, strikethrough, and autolinks.
When rendering untrusted Markdown into HTML strings, configure
`HtmlRenderer.builder().escapeHtml(true).sanitizeUrls(true)` explicitly.
The DOM renderer does not materialize raw HTML unless given a raw HTML handler.

## Reproducible intake and verification

`upstream/libraries-lock.json` records release commits and SHA-256 hashes of
unmodified source archives. Intake verifies the hashes before extracting the
library sources, upstream tests, and licenses. The binding generator applies the
shared JsInterop transformation to History, Markdown, and Jackson, including
History's browser state object. REST keeps its native TeaVM adapters as versioned sources.
Generated Java files are included in `target/compat/sources.sha256`; the artifact
check also rejects duplicate classes across the widget, compatibility, and new
library jars. Each new jar includes its upstream license and the source lock.

The History module runs 53 upstream model/filter/normalization tests. Markdown
runs 126 upstream JVM tests, including the 652 CommonMark conformance examples.
The upstream GWT test runner is excluded; the port's browser tests exercise the
compiled TeaVM application instead.

Build with JDK 21:

```sh
mvn clean install
mvn -f browser-tests/teavm/pom.xml -Dtest=EcosystemStepsTest test
mvn -f browser-tests/webkit/pom.xml -Dtest=EcosystemStepsTest test
```

`ecosystem-smoke` builds an application that deliberately has no widget
dependency. Its scenarios cover both browser Markdown renderers and extensions,
plain text and Markdown round trips, configured HTML/URL handling, History token
parameters, push/replace, state payloads, back/forward, filtered listeners,
listener removal, one-shot listeners, and a navigation guard.

Brix is deliberately deferred: its Dagger-based presenter framework overlaps Sarto facilities and is not required by these libraries.

## Jackson and REST

Use `io.instanto:domino-jackson-teavm` instead of `org.dominokit:domino-jackson`,
and `io.instanto:domino-rest-teavm` instead of `org.dominokit:domino-rest-client`.
The REST port already depends on the Jackson port and excludes the upstream
Jackson runtime to prevent duplicate classes. Applications using SLF4J can use
the `domino-slf4j-teavm` SLF4J 2 bridge (see `data-smoke/pom.xml`).

The annotation processors run on the build JVM and are reused directly:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
    <proc>full</proc>
    <annotationProcessorPaths>
      <path>
        <groupId>org.dominokit</groupId>
        <artifactId>domino-jackson-processor</artifactId>
        <version>1.0.6</version>
      </path>
      <path>
        <groupId>org.dominokit</groupId>
        <artifactId>domino-rest-processor</artifactId>
        <version>2.0.2</version>
      </path>
    </annotationProcessorPaths>
  </configuration>
</plugin>
```

The Jackson port keeps the upstream Java context for date formatting, collections,
and JSON escaping. It replaces `Date.toInstant()` and `Date.from()` with equivalent
millisecond conversions supported by TeaVM. `JsJacksonContext` also uses those
implementations. The GWT-specific `JsDateFormat`, `JsDateKeyParser`, and
`GwtJacksonJsonSerializerParameters` classes are omitted because the shared GWT
compatibility layer lacks their timezone APIs. Use `JacksonContextProvider` and
the `JsonSerializerParameters` interface rather than those GWT-specific helpers.

REST retains the earlier TeaVM XMLHttpRequest, timer, event-bus, and regex
adapters; their source provenance is recorded in `libraries-lock.json`. The only
upstream client-source change between 2.0.0 and 2.0.2 is a Javadoc edit; the new
shared model and processor are taken from the 2.0.2 release. The retained regex
adapter needed two fixes: initialize the first match before URL groups are read,
and let the Java matcher handle empty matches without skipping input or reading
past the end. Two regression tests cover these cases, alongside 66 upstream
URL and service-path tests.

`data-smoke` uses both real annotation processors to generate a Java record mapper
and typed JAX-RS client. It exercises quoted text, collections, UTC dates, path
and query parameters, JSON POST bodies, response interceptors, and HTTP 404
callbacks against the browser fixture server. The generated record mapper is also
checked on the JVM. Multipart, custom readers/writers, and resource locators are
retained upstream APIs but are not covered by this fixture.

## Logger

Use standard `org.slf4j` APIs in application and shared library code. Add the
browser backend to the TeaVM application's dependencies and the compiler plugin:

```xml
<dependency>
  <groupId>io.instanto</groupId>
  <artifactId>domino-slf4j-teavm</artifactId>
</dependency>
<!-- Inside org.teavm:teavm-maven-plugin -->
<dependencies>
  <dependency>
    <groupId>io.instanto</groupId>
    <artifactId>domino-slf4j-teavm</artifactId>
    <version>0.1.0-SNAPSHOT</version>
  </dependency>
</dependencies>
```

Remove `teavm-extras-slf4j` from both dependency lists. The new plugin rejects a
compiler classpath containing that competing bridge. Use this artifact in place
of upstream `org.dominokit:domino-slf4j-logger`, so the Domino implementation is
present only once. The BOM aligns `slf4j-api` to 2.0.16, including UI and REST.

The port contains no replacement `org.slf4j` class files and registers no JVM
SLF4J provider. Its TeaVM compiler plugin replaces only `LoggerFactory` provider
discovery with a direct, initialized Domino provider. Standard SLF4J 2 `Logger`,
`MDC`, `MarkerFactory`, formatting and fluent builders remain in use. JVM
applications continue selecting their own normal SLF4J backend; a regression test
checks this with `slf4j-simple`.

```java
Logger log = LoggerFactory.getLogger(MyApplication.class);
log.info("Loaded {} records", count);
log.atInfo().addKeyValue("count", count).log("Loaded records");

LoggingRouter.register("AUDIT", auditAdapter);
Marker audit = MarkerFactory.getMarker("AUDIT");
try (var scope = MDCUtils.withMdc("requestId", requestId)) {
    log.info(audit, "Saved {}", recordId);
}
```

`LoggingAdapter` receives the level, logger name, marker, formatted message,
throwable and an MDC snapshot. `CompositeLoggingAdapter` can fan out events.
Marker calls use `LoggingRouter`, including its optional default adapter;
ordinary calls retain upstream console behavior. Fluent events preserve arguments,
causes and marker routing; key/value pairs are rendered into the message. With
multiple fluent markers, the first marker has direct routing priority and the
others are included as references without modifying application-owned markers.

Set `domino.slf4j.logging.level` with `System.setProperty` before first logger use.
Supported thresholds are TRACE, DEBUG, INFO (default), WARN, ERROR and OFF.
The port fixes upstream string-identity comparisons, reversed INFO/DEBUG
thresholds, and marker overloads bypassing level filtering. These transformations
are applied to the pinned source and included in the generated-source hash gate.

MDC is local to a browser realm, with synchronous nested scopes. A `withMdc`
scope restores an enclosing `withMdc` scope even when its body throws. As in
upstream 2.0.1, mixing an existing flat `MDC.put` value with a scope on the same key
is not a restoration mechanism; use nested scopes consistently. Scopes do not
propagate automatically through promises, timers or Sarto Async callbacks.

`compat-reuse-smoke` has no widget dependency. Its ten browser scenarios cover
standard factory initialization, classic and fluent formatting, marker hierarchy,
custom/default adapters and unregistering, throwable overloads, MDC snapshots,
nested scope cleanup after exceptions, idempotent closing, six level thresholds
and lazy argument suppression. The showcase and REST smoke application also use
this backend.

## Local validation (2026-10-18)

The combined `mvn -Pproduction clean install` passes with all 258 JVM tests
(245 upstream library tests, three port/mapper regressions, nine build-tool tests,
and one JVM logging-backend isolation test), zero failures/errors/skips, twelve
static-analysis reports, and the expanded artifact/source-hash gate (4,264 unique
classes). Three existing global-configuration warnings in REST are explicitly
triaged to preserve the upstream shared-settings contract.

The complete 139-scenario browser suite passes against optimized, minified output
in both Chrome and WebKit, with no failures, errors, or skips. Ten scenarios cover
the standalone logger and six cover the showcase library playgrounds. Development
focused checks also pass. The native scrolling/layout suite passes five checks
with its intentional WebKit skip for Chromium-only touch injection. Firefox
remains unverified locally for this release because of the launcher/runtime
issues described in the release notes; CI runs the full Firefox suite.

The standalone consumer example builds successfully with TeaVM 0.16.0 and the new
logger. A separate negative compilation check confirms that adding the old
`teavm-extras-slf4j` compiler plugin fails with the intended migration message.
The logger JAR contains neither `org.slf4j` classes nor a JVM provider service
registration. Formatting and `git diff --check` pass.

The showcase exposes [Markdown](https://instanto-io.github.io/domino-widgets/teavm/?page=markdown)
and [logging](https://instanto-io.github.io/domino-widgets/teavm/?page=logging)
playgrounds, with Java examples linked from each page. Six additional browser
scenarios cover editing, renderer switching, samples, safe output handling,
level filtering, marker routes, and nested MDC restoration.

Logs are retained under `target/upgrade-validation/`. Publication is gated by the
main-branch verification workflow in both build modes and all three browser engines.
GitHub Pages receives the verified production artifact. A separate workflow
publishes Maven snapshots and verifies a consumer build.
