// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import org.dominokit.domino.formsamples.shared.model.CorporateAccount;
import org.dominokit.domino.formsamples.shared.model.CorporateProfile;
import org.dominokit.domino.ui.IsElement;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.icons.MdiIcon;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.popover.Popover;
import org.dominokit.domino.ui.popover.Tooltip;
import org.dominokit.domino.ui.style.Style;
import org.dominokit.domino.ui.utils.PostfixAddOn;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class CorporateAccountsSelect implements IsElement<HTMLElement> {

  private Select<CorporateAccount> accountSelect;

  private AccountDetails accountDetails;

  public CorporateAccountsSelect(String title, CorporateProfile corporateProfile) {
    accountDetails = new AccountDetails();
    MdiIcon correspondentChargesAccountIcon =
        Icons.information_outline()
            .styler(sampleStyle -> sampleStyle.setCssProperty("cursor", "pointer"));
    accountSelect =
        Select.<CorporateAccount>create(title)
            .appendChild(PrefixAddOn.of(Icons.wallet()))
            .appendChild(PostfixAddOn.of(correspondentChargesAccountIcon));
    Tooltip.create(correspondentChargesAccountIcon.element(), "Show details");
    Style.of(
            Popover.create(correspondentChargesAccountIcon.element())
                .apply(
                    p -> {
                      p.getHeaderElement().setTextContent("Account details");
                      p.getBody().appendChild(accountDetails.element());
                    })
                .setPosition(new AccountDetailsPopupPosition(accountSelect)))
        .setWidth("330px");
    for (CorporateAccount corporateAccount : corporateProfile.getCorporateAccounts()) {
      accountSelect.appendChild(
          SelectOption.create(
              String.valueOf(corporateAccount.getAccountAlias()),
              corporateAccount,
              corporateAccount.getAccountAlias()));
    }
    FormExampleSupport.onSelect(
        accountSelect, option -> accountDetails.setAccount(option.getValue()));
  }

  public static CorporateAccountsSelect create(String title, CorporateProfile corporateProfile) {
    return new CorporateAccountsSelect(title, corporateProfile);
  }

  public Select<CorporateAccount> getAccountSelect() {
    return accountSelect;
  }

  @Override
  public HTMLElement element() {
    return accountSelect.element();
  }

  public CorporateAccountsSelect show() {
    accountSelect.show();
    return this;
  }

  public CorporateAccountsSelect hide() {
    accountSelect.hide();
    return this;
  }

  public CorporateAccountsSelect toggleDisplay() {
    accountSelect.toggleDisplay();
    return this;
  }

  public CorporateAccountsSelect toggleDisplay(boolean state) {
    accountSelect.toggleDisplay(state);
    return this;
  }

  public boolean isExpanded() {
    return !isCollapsed();
  }

  public boolean isCollapsed() {
    return accountSelect.isHidden();
  }
}
