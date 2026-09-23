// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.formsamples.shared.model.CorporateAccount;
import org.dominokit.domino.ui.IsElement;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public final class AccountDetails implements IsElement<HTMLDivElement> {

  private TextBox accountNumberHeader;

  private TextBox ibanHeader;

  private TextBox currencyHeader;

  private HTMLDivElement element;

  public AccountDetails() {
    accountNumberHeader =
        TextBox.create("Account number")
            .appendChild(PrefixAddOn.of(Icons.wallet()))
            .setReadOnly(true);
    ibanHeader =
        TextBox.create("IBAN").appendChild(PrefixAddOn.of(Icons.code_array())).setReadOnly(true);
    currencyHeader =
        TextBox.create("Currency")
            .appendChild(PrefixAddOn.of(Icons.currency_usd()))
            .setReadOnly(true);
    element =
        Row.create()
            .styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(Column.span12().appendChild(accountNumberHeader))
            .appendChild(Column.span12().appendChild(ibanHeader))
            .appendChild(Column.span12().appendChild(currencyHeader))
            .element();
  }

  @Override
  public HTMLDivElement element() {
    return element;
  }

  public void setAccount(CorporateAccount account) {
    accountNumberHeader.setValue(account.getAccountNumber());
    ibanHeader.setValue(account.getIban());
    currencyHeader.setValue(account.getCurrency().getCurrencyCode());
  }
}
