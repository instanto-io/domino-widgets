package io.instanto.compat;

import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import java.util.List;
import org.dominokit.domino.client.history.StateHistory;
import org.dominokit.domino.history.*;
import org.dominokit.markdown.Extension;
import org.dominokit.markdown.extensions.discovery.ExtensionDiscovery;
import org.dominokit.markdown.node.Node;
import org.dominokit.markdown.parser.Parser;
import org.dominokit.markdown.renderer.elemental2.Elemental2Renderer;
import org.dominokit.markdown.renderer.html.HtmlRenderer;
import org.dominokit.markdown.renderer.markdown.MarkdownRenderer;
import org.dominokit.markdown.renderer.text.TextContentRenderer;

/** Exercises the independent libraries without Domino UI on the classpath. */
public final class EcosystemSmoke {
  private static int notifications;
  private static int removedNotifications;
  private static int onceNotifications;

  public static void main(String[] args) {
    markdown();
    history();
    DomGlobal.document.body.setAttribute("data-ecosystem", "ready");
  }

  private static void markdown() {
    List<Extension> extensions = ExtensionDiscovery.load();
    Parser parser = Parser.builder().extensions(extensions).build();
    Node document =
        parser.parse(
            "# TeaVM Markdown\n\nHello **world**, ~~old~~.\n\n"
                + "| Name | Version |\n| --- | --- |\n| Markdown | 1.0.0 |\n\n"
                + "- [x] Ported\n- [ ] Pending\n\nhttps://example.com\n");
    Element dom = section("markdown-dom");
    dom.appendChild(Elemental2Renderer.builder().extensions(extensions).build().render(document));
    section("markdown-html").innerHTML =
        HtmlRenderer.builder().extensions(extensions).build().render(document);
    section("markdown-text").textContent =
        TextContentRenderer.builder().extensions(extensions).build().render(document);
    String canonical = MarkdownRenderer.builder().extensions(extensions).build().render(document);
    section("markdown-roundtrip").innerHTML =
        HtmlRenderer.builder().extensions(extensions).build().render(parser.parse(canonical));
    Node unsafe = parser.parse("<script>alert('raw')</script>\n\n[unsafe](javascript:alert(1))");
    section("markdown-safe").innerHTML =
        HtmlRenderer.builder().escapeHtml(true).sanitizeUrls(true).build().render(unsafe);
    section("markdown-safe-dom")
        .appendChild(Elemental2Renderer.builder().sanitizeUrls(true).build().render(unsafe));
  }

  private static void history() {
    Element result = section("history-result");
    StateHistory history = new StateHistory();
    history.replaceState(StateToken.of("ecosystem/start").title("Start").data("initial"));
    history.listen(
        TokenFilter.startsWithPathFilter("ecosystem"),
        state -> {
          notifications++;
          result.setAttribute("data-count", String.valueOf(notifications));
          result.setAttribute("data-token", state.token().value());
          result.setAttribute("data-title", state.title());
          result.setAttribute("data-state", state.data().orElse(""));
        });
    DominoHistory.StateListener removed = state -> removedNotifications++;
    history.listen(removed);
    history.removeListener(removed);
    history.listen(state -> onceNotifications++, true);
    history.addInterceptor(
        (event, chain) -> {
          // In upstream 1.0.6 cancel() completes navigation; withholding next() blocks it.
          if (!event.getParsedToken().value().contains("blocked")) chain.next();
        });
    button(
        "history-push",
        () ->
            history.fireState(
                StateToken.of("ecosystem/item/:id?mode=edit#detail")
                    .title("Item 42")
                    .data("payload-42"),
                new TokenParameter("id", "42")));
    button(
        "history-replace",
        () -> {
          history.replaceState(
              StateToken.of("ecosystem/replaced").title("Replaced").data("replacement"));
          history.fireCurrentStateHistory();
        });
    button(
        "history-block",
        () -> {
          history.fireState("ecosystem/blocked");
          result.setAttribute("data-blocked-token", history.currentToken().value());
        });
    button("history-back", history::back);
    button("history-forward", history::forward);
    button(
        "history-inspect",
        () -> {
          result.setAttribute("data-removed-count", String.valueOf(removedNotifications));
          result.setAttribute("data-once-count", String.valueOf(onceNotifications));
          result.setAttribute("data-path", history.currentToken().value());
        });
    result.setAttribute("data-count", "0");
  }

  private static Element section(String id) {
    Element element = DomGlobal.document.createElement("section");
    element.id = id;
    DomGlobal.document.body.appendChild(element);
    return element;
  }

  private static void button(String id, Runnable action) {
    Element button = DomGlobal.document.createElement("button");
    button.id = id;
    button.textContent = id;
    button.addEventListener("click", event -> action.run());
    DomGlobal.document.body.appendChild(button);
  }
}
