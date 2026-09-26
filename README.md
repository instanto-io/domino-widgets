# Domino Widgets for TeaVM

Domino Widgets runs [DominoKit's Domino UI](https://github.com/DominoKit/domino-ui)
Java widgets on TeaVM. It preserves the upstream
`org.dominokit.domino.ui` API for forms, calendars, tables and dialogs.
This repository provides the TeaVM adaptation; GWT users can use DominoKit's
upstream distribution.

The `io.instanto` Maven group distinguishes this independently maintained port
from official DominoKit releases. See [widget coverage](docs/COMPATIBILITY.md)
for its current support and limitations.

**[Explore the TeaVM showcase](https://instanto-io.github.io/domino-widgets/teavm/).**

## Start a page

The [small example application](examples/README.md) contains a complete TeaVM
host page. Serve the widget asset directory as `domino-widgets/` beside that
page and load its stylesheet:

```html
<link rel="stylesheet" href="domino-widgets/css/domino-ui/domino-ui.css">
```

Keep the directory intact so the stylesheet can find its fonts and icons.
Start the Java entry point after the page body exists:

```html
<script src="app.js"></script>
<script>main();</script>
```

## Create a widget

The TeaVM entry point can call a screen class:

```java
package example.client;

public final class Launcher {
    public static void main(String[] args) {
        Screen.mount();
    }
}
```

Create widgets in Java and attach their elements to the page:

```java
import elemental2.dom.DomGlobal;
import org.dominokit.domino.ui.forms.TextBox;

public final class Screen {
    public static void mount() {
        TextBox name = TextBox.create("Your name");
        DomGlobal.document.body.appendChild(name.element());
    }
}
```

## Respond to an event

Inside `mount()`, add a button after the text box. Import
`org.dominokit.domino.ui.button.Button`:

```java
Button greet = Button.create("Greet");
greet.addClickListener(event -> greet.setText("Hello " + name.getValue()));
DomGlobal.document.body.appendChild(greet.element());
```

To add a calendar, import `org.dominokit.domino.ui.datepicker.Calendar`:

```java
Calendar calendar = Calendar.create();
DomGlobal.document.body.appendChild(calendar.element());
```

The [complete example screen](examples/teavm/src/main/java/example/client/Screen.java)
shows these patterns in a working application.

## Explore the widgets

The showcase adapts [DominoKit's demo](https://github.com/DominoKit/domino-ui-demo)
and includes its example methods and descriptions. Browse by widget or search
the gallery; each page links to its Java example. The
[showcase guide](docs/SHOWCASE.md) describes the pages and the
[coverage guide](docs/COMPATIBILITY.md) records verified interactions.

For the adaptation's architecture see [port design](docs/DESIGN.md). Maintainer
build instructions are in [development](docs/DEVELOPMENT.md).

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

Want to see this port and more TeaVM libraries maintained? Please [sponsor this port](https://github.com/sponsors/instanto-io).
