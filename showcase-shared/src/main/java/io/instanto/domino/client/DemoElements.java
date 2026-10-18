package io.instanto.domino.client;

import elemental2.dom.*;
import org.dominokit.domino.ui.button.Button;

/** Small shared pieces for the original interactive library examples. */
final class DemoElements {
  private DemoElements() {}

  static HTMLElement element(String tag, String css, String text) {
    HTMLElement element = (HTMLElement) DomGlobal.document.createElement(tag);
    element.className = css;
    element.textContent = text;
    return element;
  }

  static HTMLElement button(String id, String label, Runnable action) {
    var button = Button.create(label).setId(id).addClickListener(event -> action.run()).element();
    button.setAttribute("type", "button");
    return button;
  }

  static HTMLElement panel(String title, String description) {
    HTMLElement panel = element("section", "demo-panel", "");
    panel.appendChild(element("h2", "demo-panel-title", title));
    panel.appendChild(element("p", "demo-hint", description));
    return panel;
  }

  static HTMLElement root(String id) {
    HTMLElement root = element("section", "library-demo", "");
    root.id = id;
    return root;
  }
}
