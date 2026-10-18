package io.instanto.domino.client;

import static io.instanto.domino.client.DemoElements.*;

import elemental2.dom.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.dominokit.domino.ui.forms.TextAreaBox;
import org.dominokit.markdown.Extension;
import org.dominokit.markdown.extensions.discovery.ExtensionDiscovery;
import org.dominokit.markdown.parser.Parser;
import org.dominokit.markdown.renderer.elemental2.Elemental2Renderer;
import org.dominokit.markdown.renderer.html.HtmlRenderer;
import org.dominokit.markdown.renderer.markdown.MarkdownRenderer;
import org.dominokit.markdown.renderer.text.TextContentRenderer;

/** Editable examples using the actual Domino Markdown parser and all four renderers. */
public final class MarkdownExamples {
  private static final String STARTER =
      """
      # Hello, Markdown

      Turn **plain text** into something worth reading.

      - Write in the editor
      - Watch the preview update
      - Compare the output formats

      > Small pieces, composed in Java.

      [Explore DominoKit](https://dominokit.com)
      """;
  private static final String EXTENSIONS =
      """
      # A little more than Markdown

      Use **tables**, task lists and ~~old ideas~~ new possibilities.

      | Library | Version | Ready |
      | --- | --- | --- |
      | Domino UI | 2.1.3 | Yes |
      | Markdown | 1.0.0 | Yes |
      | Logger | 2.0.1 | Yes |

      ## Release checklist

      - [x] Parse Markdown in Java
      - [x] Render it in the browser
      - [ ] Write your next document

      Links work too: https://dominokit.com
      """;
  private static final String DOCUMENT =
      """
      # Notes from the workshop

      ## The idea

      Keep your content in Markdown and choose its output at the edge of your application.

      1. Parse a document once.
      2. Render it as DOM, HTML or plain text.
      3. Export it back to Markdown.

      ```java
      var document = parser.parse(source);
      var content = renderer.render(document);
      ```

      ---

      **Tip:** switch to HTML to inspect the generated markup.
      """;

  private final HTMLElement root = root("markdown-example");
  private final TextAreaBox editor = TextAreaBox.create("Markdown source").setRows(19);
  private final HTMLElement preview = element("div", "markdown-preview", "");
  private final HTMLElement code = element("pre", "demo-code", "");
  private final HTMLElement status = element("p", "demo-status", "");
  private final HTMLElement outputHint = element("p", "demo-hint", "");
  private final Map<String, HTMLElement> modes = new LinkedHashMap<>();
  private final List<Extension> extensions = ExtensionDiscovery.load();
  private final Parser parser = Parser.builder().extensions(extensions).build();
  private String mode = "dom";

  public static HTMLElement render() {
    return new MarkdownExamples().build();
  }

  private HTMLElement build() {
    HTMLElement samples = element("div", "demo-toolbar", "");
    samples.setAttribute("aria-label", "Markdown samples");
    samples.appendChild(element("span", "demo-toolbar-label", "Try a sample"));
    samples.appendChild(button("markdown-starter", "Getting started", () -> load(STARTER)));
    samples.appendChild(button("markdown-extensions", "Tables & tasks", () -> load(EXTENSIONS)));
    samples.appendChild(button("markdown-document", "A longer document", () -> load(DOCUMENT)));
    root.appendChild(samples);
    HTMLElement grid = element("div", "demo-grid", "");
    HTMLElement input = panel("Write", "Edit the source. Your preview updates as you type.");
    editor.getInputElement().element().id = "markdown-source";
    editor.getInputElement().element().setAttribute("aria-label", "Markdown source");
    editor.getInputElement().element().setAttribute("spellcheck", "false");
    editor.getInputElement().element().addEventListener("input", event -> update());
    input.appendChild(editor.element());
    status.id = "markdown-status";
    status.setAttribute("role", "status");
    input.appendChild(status);
    grid.appendChild(input);
    HTMLElement output = panel("Explore the output", "One document, four ways to use it.");
    HTMLElement choices = element("div", "demo-options", "");
    choices.setAttribute("aria-label", "Output format");
    addMode(choices, "dom", "DOM preview");
    addMode(choices, "html", "HTML");
    addMode(choices, "text", "Plain text");
    addMode(choices, "markdown", "Markdown");
    output.appendChild(choices);
    output.appendChild(outputHint);
    preview.id = "markdown-preview";
    code.id = "markdown-output";
    code.setAttribute("tabindex", "0");
    output.appendChild(preview);
    output.appendChild(code);
    grid.appendChild(output);
    root.appendChild(grid);
    root.appendChild(
        element(
            "p",
            "demo-footnote",
            "Rendered locally in your browser with domino-md 1.0.0. Raw HTML is not executed and unsafe link schemes are filtered."));
    load(EXTENSIONS);
    root.setAttribute("data-ready", "true");
    return root;
  }

  private void addMode(HTMLElement choices, String value, String label) {
    HTMLElement choice =
        button(
            "markdown-mode-" + value,
            label,
            () -> {
              mode = value;
              update();
            });
    modes.put(value, choice);
    choices.appendChild(choice);
  }

  private void load(String sample) {
    editor.setValue(sample);
    update();
  }

  private void update() {
    String source = editor.getInputElement().element().value;
    var document = parser.parse(source);
    for (var choice : modes.entrySet())
      choice.getValue().setAttribute("aria-pressed", String.valueOf(choice.getKey().equals(mode)));
    preview.textContent = "";
    code.textContent = "";
    if (mode.equals("dom")) {
      preview.removeAttribute("hidden");
      code.setAttribute("hidden", "");
      preview.appendChild(
          Elemental2Renderer.builder()
              .extensions(extensions)
              .sanitizeUrls(true)
              .build()
              .render(document));
      outputHint.textContent = "Native DOM elements, ready to compose with Domino UI.";
    } else {
      preview.setAttribute("hidden", "");
      code.removeAttribute("hidden");
      code.textContent =
          switch (mode) {
            case "html" ->
                HtmlRenderer.builder()
                    .extensions(extensions)
                    .escapeHtml(true)
                    .sanitizeUrls(true)
                    .build()
                    .render(document);
            case "text" ->
                TextContentRenderer.builder().extensions(extensions).build().render(document);
            default -> MarkdownRenderer.builder().extensions(extensions).build().render(document);
          };
      outputHint.textContent =
          switch (mode) {
            case "html" -> "Generated HTML, shown as source.";
            case "text" -> "Readable content without markup.";
            default -> "Markdown regenerated from the parsed document.";
          };
    }
    status.textContent = source.length() + " characters · Live preview";
    root.setAttribute("data-mode", mode);
  }
}
