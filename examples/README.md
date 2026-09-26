# Hello Domino on TeaVM

This application constructs a text box, button and calendar with the original
Domino UI Java API. The [TeaVM launcher](teavm/src/main/java/example/client/Launcher.java)
starts the [screen](teavm/src/main/java/example/client/Screen.java);
the [host page](teavm/src/site/index.html) loads the widget styles and
JavaScript entry point.

Keep the `domino-widgets/` CSS, fonts and icons folder next to the host page.
The [widget guide](../README.md) walks through the screen in small steps.
