package io.instanto.domino.client;

import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLOptionElement;
import elemental2.dom.HTMLSelectElement;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;
import org.dominokit.domino.ui.icons.MdiIcon;
import org.dominokit.domino.ui.icons.lib.MdiByTagFactory;
import org.dominokit.domino.ui.icons.lib.MdiTags;

/** The pinned icon catalogue, with one category rendered at a time to keep the page responsive. */
final class IconBrowser {
  private static final Set<String> JAVA_KEYWORDS =
      Set.of(
          "abstract",
          "assert",
          "boolean",
          "break",
          "byte",
          "case",
          "catch",
          "char",
          "class",
          "const",
          "continue",
          "default",
          "do",
          "double",
          "else",
          "enum",
          "extends",
          "final",
          "finally",
          "float",
          "for",
          "goto",
          "if",
          "implements",
          "import",
          "instanceof",
          "int",
          "interface",
          "long",
          "native",
          "new",
          "package",
          "private",
          "protected",
          "public",
          "return",
          "short",
          "static",
          "strictfp",
          "super",
          "switch",
          "synchronized",
          "this",
          "throw",
          "throws",
          "transient",
          "try",
          "void",
          "volatile",
          "while",
          "true",
          "false",
          "null");

  private final HTMLDivElement root = (HTMLDivElement) DomGlobal.document.createElement("div");
  private final HTMLSelectElement category =
      (HTMLSelectElement) DomGlobal.document.createElement("select");
  private final HTMLInputElement search =
      (HTMLInputElement) DomGlobal.document.createElement("input");
  private final HTMLInputElement selected =
      (HTMLInputElement) DomGlobal.document.createElement("input");
  private final HTMLDivElement results = (HTMLDivElement) DomGlobal.document.createElement("div");
  private final HTMLElement count = (HTMLElement) DomGlobal.document.createElement("p");
  private int limit = 80;

  static HTMLElement create() {
    return new IconBrowser().mount();
  }

  private HTMLElement mount() {
    root.className = "showcase-icon-browser";
    HTMLElement heading = (HTMLElement) DomGlobal.document.createElement("h2");
    heading.textContent = "Material Design Icons";
    root.appendChild(heading);
    HTMLElement description = (HTMLElement) DomGlobal.document.createElement("p");
    description.textContent =
        "Choose a category, search its icons, then select one to see its Java call.";
    root.appendChild(description);

    category.setAttribute("aria-label", "Icon category");
    for (String tag : MdiTags.TAGS) {
      HTMLOptionElement option = (HTMLOptionElement) DomGlobal.document.createElement("option");
      option.value = tag;
      option.textContent = tag.isEmpty() ? "Untagged" : tag;
      category.appendChild(option);
    }
    category.value = MdiTags.ACCOUNT_USER;
    category.addEventListener("change", event -> resetResults());
    root.appendChild(category);

    search.type = "search";
    search.placeholder = "Find an icon in this category";
    search.setAttribute("aria-label", "Search icons");
    search.addEventListener("input", event -> resetResults());
    root.appendChild(search);

    selected.type = "text";
    selected.readOnly = true;
    selected.placeholder = "Select an icon to see its Java call";
    selected.setAttribute("aria-label", "Selected icon Java call");
    selected.addEventListener("click", event -> selected.select());
    root.appendChild(selected);

    count.setAttribute("aria-live", "polite");
    root.appendChild(count);
    results.className = "showcase-icon-results";
    root.appendChild(results);
    renderResults();
    return root;
  }

  private void resetResults() {
    limit = 80;
    renderResults();
  }

  private void renderResults() {
    results.textContent = "";
    String token = search.value.trim().toLowerCase(Locale.ROOT);
    List<Supplier<MdiIcon>> icons = MdiByTagFactory.get(category.value);
    int matches = 0;
    for (Supplier<MdiIcon> supplier : icons) {
      MdiIcon icon = supplier.get();
      if (!matches(icon, token)) continue;
      matches++;
      if (matches > limit) continue;
      HTMLButtonElement item = (HTMLButtonElement) DomGlobal.document.createElement("button");
      item.type = "button";
      item.className = "showcase-icon-item";
      item.appendChild(icon.element());
      HTMLElement label = (HTMLElement) DomGlobal.document.createElement("span");
      label.textContent = icon.getName();
      item.appendChild(label);
      item.addEventListener("click", event -> selected.value = javaCall(icon.getName()));
      results.appendChild(item);
    }
    count.textContent = "Showing " + Math.min(matches, limit) + " of " + matches + " icons";
    if (matches > limit) {
      HTMLButtonElement more = (HTMLButtonElement) DomGlobal.document.createElement("button");
      more.type = "button";
      more.textContent = "Show more icons";
      more.addEventListener(
          "click",
          event -> {
            limit += 80;
            renderResults();
          });
      results.appendChild(more);
    }
  }

  private static boolean matches(MdiIcon icon, String token) {
    if (token.isEmpty() || icon.getName().toLowerCase(Locale.ROOT).contains(token)) return true;
    return icon.getMetaInfo().getAliases().stream()
            .anyMatch(alias -> alias.toLowerCase(Locale.ROOT).contains(token))
        || icon.getMetaInfo().getTags().stream()
            .anyMatch(tag -> tag.toLowerCase(Locale.ROOT).contains(token));
  }

  private static String javaCall(String name) {
    String method = name.replace("mdi-", "").replace('-', '_');
    if (JAVA_KEYWORDS.contains(method)) method += "_";
    return "Icons." + method + "()";
  }
}
