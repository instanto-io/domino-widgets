// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static elemental2.dom.DomGlobal.window;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.DOMRect;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.formsamples.shared.model.CorporateAccount;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.menu.direction.DropDirection;
import org.dominokit.domino.ui.menu.direction.DropDirectionContext;

public class AccountDetailsPopupPosition implements DropDirection {

  private final Select<CorporateAccount> accountSelect;

  public AccountDetailsPopupPosition(Select<CorporateAccount> accountSelect) {
    this.accountSelect = accountSelect;
  }

  @Override
  public DropDirection position(DropDirectionContext context) {
    HTMLElement tooltip = (HTMLElement) context.getSource();
    DOMRect targetRect = accountSelect.element().getBoundingClientRect();
    DOMRect tooltipRect = tooltip.getBoundingClientRect();
    tooltip.style.setProperty(
        "top", ((targetRect.top + window.scrollY) - tooltipRect.height) + "px");
    tooltip.style.setProperty(
        "left",
        targetRect.left + window.scrollX + ((targetRect.width - tooltipRect.width) / 2) + "px");
    return this;
  }
}
