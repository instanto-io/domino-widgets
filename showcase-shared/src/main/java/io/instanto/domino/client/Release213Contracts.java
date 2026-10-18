package io.instanto.domino.client;

import elemental2.dom.*;
import org.dominokit.domino.ui.button.Fab;
import org.dominokit.domino.ui.forms.DateBox;
import org.dominokit.domino.ui.forms.TimeBox;
import org.dominokit.domino.ui.layout.PageHeader;
import org.dominokit.domino.ui.themes.*;
import org.dominokit.domino.ui.unitvalue.UnitValue;
import org.dominokit.domino.ui.utils.DynamicPadding;

/** Browser regression fixture for the 2.1.3 upstream intake. */
public final class Release213Contracts {
  public static HTMLElement render() {
    HTMLElement root = (HTMLElement) DomGlobal.document.createElement("section");
    root.id = "release-213";
    DominoThemeManager manager = DominoThemeManager.INSTANCE;
    int[] changes = {0};
    ThemeChangeListener listener = change -> changes[0]++;
    manager.addThemeChangeListener(listener);
    // Theme restoration during an active operation caused the 2.1.2 startup failure.
    IsDominoTheme restoringTheme =
        new IsDominoTheme() {
          public String getName() {
            return "release-213-restore";
          }

          public String getCategory() {
            return "release-213";
          }

          public void apply(Element target) {
            manager.applyUserThemes();
          }

          public void cleanup(Element target) {}

          public boolean isApplied(Element target) {
            return false;
          }
        };
    try {
      manager.apply(restoringTheme);
      BindingContracts.check(changes[0] > 1, "deferred theme restoration and listeners");
      root.setAttribute("data-theme-restored", "true");
    } finally {
      manager.removeThemeChangeListener(listener);
      manager.remove(restoringTheme.getName());
    }
    root.appendChild(PageHeader.create("Domino UI 2.1.3").element());
    UnitValue value = UnitValue.create("213", "widgets");
    value.element().id = "release-value";
    value.addCss(DynamicPadding.of("13px"));
    root.appendChild(value.element());
    root.appendChild(Fab.create().element());
    DateBox date = DateBox.create("Date").setPattern("yyyy-MM-dd").setTypingModeEnabled(true);
    date.element().id = "release-date";
    root.appendChild(date.element());
    TimeBox time = TimeBox.create("Time").setPattern("HH:mm").setTypingModeEnabled(true);
    time.element().id = "release-time";
    root.appendChild(time.element());
    root.setAttribute("data-ready", "true");
    return root;
  }
}
