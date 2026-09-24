# Domino Widgets

Domino Widgets adds TeaVM support to [DominoKit’s Domino UI](https://github.com/DominoKit/domino-ui).
Use its Java API to build forms, calendars, tables and dialogs in the browser.
This repository provides the TeaVM adaptation. For GWT, use DominoKit’s upstream distribution.

The widgets are the work of **DominoKit and its contributors**. This distribution
adds TeaVM build support while preserving
the original `org.dominokit.domino.ui` packages. Attribution and source provenance
are recorded in [NOTICE](NOTICE).

We changed the Maven groupId to `io.instanto` to distinguish this port from upstream
DominoKit releases and avoid confusion about its origin or ownership. This is an
independently maintained distribution, not an official DominoKit release.

**[Try the TeaVM showcase](https://instanto-io.github.io/domino-widgets/teavm/)**

Use `domino-widgets-teavm` for the widgets and `domino-widgets-assets` for the matching
styles, fonts and icons. The shared [TeaVM compatibility libraries](https://github.com/instanto-io/teavm-compat)
are brought in as dependencies.

## Add the dependencies

The current version is `0.1.0-SNAPSHOT`, published under `io.instanto` at
packages.instanto.io. Add this repository inside your POM's `<repositories>` element:

```xml
<repository>
  <id>forgejo-instanto</id>
  <url>https://packages.instanto.io/api/packages/instanto-io/maven</url>
  <releases><enabled>false</enabled></releases>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
</repository>
```

Configure Maven credentials for `forgejo-instanto` as shown in the
[Instanto parent instructions](https://github.com/instanto-io/instanto-poms#use-a-parent).
Keep credentials in your Maven settings, outside the project POM.

Import the BOM to keep the widget and asset versions together. This example selects
the TeaVM widget artifact.

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.instanto</groupId>
      <artifactId>domino-widgets-bom</artifactId>
      <version>0.1.0-SNAPSHOT</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
<dependencies>
  <dependency>
    <groupId>io.instanto</groupId>
    <artifactId>domino-widgets-teavm</artifactId>
  </dependency>
  <dependency>
    <groupId>io.instanto</groupId>
    <artifactId>domino-widgets-assets</artifactId>
  </dependency>
</dependencies>
```

The [small example application](examples/README.md) includes complete compiler
configuration. It builds with JDK 21 and Maven 3.9+, targeting
Java 17.

For TeaVM, follow the [example POM](examples/teavm/pom.xml), including its SLF4J
runtime configuration. Its dependencies supply the Elemental2 and JsInterop
compatibility layers; adding the original Elemental2 or `com.google.jsinterop:base`
artifacts alongside them creates duplicate packages.

## Load the styles

The assets JAR contains `META-INF/resources/domino-widgets/`. Copy or serve that
folder as `domino-widgets/` beside your application's HTML, then add:

```html
<link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css">
```

Keep the folder structure intact so the stylesheet can find its fonts and icons.
The examples [unpack the assets directly into the site during Maven packaging](examples/pom.xml).

## Start the TeaVM application

Use JDK 21, Maven 3.9+ and a browser. Copy the [example application](examples)
to get the complete Maven harness: dependency versions, TeaVM 0.15.0 compiler,
SLF4J runtime, asset extraction and host page. Build it with `mvn clean verify`;
the resulting application is in `teavm/target/site`.

The host page loads the stylesheet and starts the Java entry point after its
body exists. `app.js` is the filename configured in the example POM:

```html
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Hello Domino</title>
  <link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css">
</head>
<body>
  <script src="app.js"></script>
  <script>main();</script>
</body>
</html>
```

The entry point is ordinary Java; use its fully qualified name as TeaVM's
`mainClass`:

```java
package example.client;

public final class Launcher {
    public static void main(String[] args) {
        Screen.mount();
    }
}
```

## Create your first widget

Create widgets in Java and attach their elements to the page. For example:

```java
import elemental2.dom.DomGlobal;
import org.dominokit.domino.ui.datepicker.Calendar;
import org.dominokit.domino.ui.forms.TextBox;

public final class Screen {
    public static void mount() {
        TextBox name = TextBox.create("Your name");
        DomGlobal.document.body.appendChild(name.element());
    }
}
```

## Add an interaction

Keep the text box above and add a button inside `mount()`. Import
`org.dominokit.domino.ui.button.Button`; its listener reads the text box and
updates the button:

```java
Button greet = Button.create("Greet");
greet.addClickListener(event -> greet.setText("Hello " + name.getValue()));
DomGlobal.document.body.appendChild(greet.element());
```

## Add a richer widget

Add a calendar to the same screen, using the existing
`org.dominokit.domino.ui.datepicker.Calendar` import:

```java
Calendar calendar = Calendar.create();
DomGlobal.document.body.appendChild(calendar.element());
```

Run `jwebserver -b 127.0.0.1 -p 8080 -d teavm/target/site` from the example root,
then open [your application](http://127.0.0.1:8080/). Rebuild after changing Java.
The [complete example screen](examples/teavm/src/main/java/example/client/Screen.java)
shows the same construction and event pattern in a compilable application.

## Explore the widgets

The showcases are adapted from [DominoKit’s original demo](https://github.com/DominoKit/domino-ui-demo).
They retain 69 original pages and 190 sample methods, presented through a shared
TeaVM launcher. Use the grouped navigation or widget search to browse
the gallery. Each page links to its Java example and upstream counterpart. Browse
the [shared examples](showcase-shared/src/main/java/io/instanto/domino/client).

Check the
[coverage guide](docs/COMPATIBILITY.md) when choosing a feature: it describes the
interactions tested across browsers and the remaining limitations.

## Go further

- [Run the example applications](examples/README.md).
- [Browse the included showcase pages](docs/SHOWCASE.md).
- [Understand the port design](docs/DESIGN.md).
- [Build and test the library](docs/DEVELOPMENT.md).
- [Read the verification reports](reports/README.md).

## Upstream credits

The widget implementations and Java API are the work of
[DominoKit and the Domino UI contributors](https://github.com/DominoKit/domino-ui).
The retained showcase examples, descriptions and templates come from
[DominoKit's original demo](https://github.com/DominoKit/domino-ui-demo).
[TeaVM](https://github.com/konsoletyper/teavm), by Alexey Andreev and its
contributors, makes this adaptation possible. Instanto maintains the TeaVM
adaptation, compatibility integration and build tooling.

The Java libraries are distributed under [Apache-2.0](LICENSE) with the original
notices preserved. See [NOTICE](NOTICE) and the [asset inventory](docs/ASSETS.md)
for source, font and icon attribution; bundled assets retain their own licences.

## Support the projects

Like DominoKit? Please [support the upstream project](https://www.patreon.com/Dominokit).

Using the TeaVM build? Please [support TeaVM](https://github.com/sponsors/konsoletyper).

Want to see more TeaVM libraries maintained and supported? Please support us—our support link is coming soon.

## Shared build parent

For local builds, install the shared parent from a sibling `instanto-poms`
checkout with `mvn -f ../instanto-poms/pom.xml install`. Release instructions
are in `instanto-poms/RELEASING.md`.
