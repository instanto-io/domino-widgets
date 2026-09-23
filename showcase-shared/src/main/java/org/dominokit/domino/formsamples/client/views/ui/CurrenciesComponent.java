// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui;

import static io.instanto.domino.client.FormExampleSupport.numbersOnly;
import static java.util.Objects.nonNull;
import static org.dominokit.domino.ui.utils.Domino.*;

import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.shared.model.CurrencyData;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.utils.PrefixAddOn;
import org.gwtproject.i18n.client.NumberFormat;

public class CurrenciesComponent {

  private Select<CurrencyData> currencySelect;

  private TextBox amountField;

  private String oldCurrencyCode;

  public CurrenciesComponent() {
    currencySelect =
        Select.<CurrencyData>create("Currency")
            .appendChild(PrefixAddOn.of(i().css("fas", "fa-money-bill-alt", "fa-lg")));
    amountField =
        numbersOnly(
            TextBox.create("Amount")
                .setHelperText("Numbers only")
                .appendChild(PrefixAddOn.of(Icons.wallet_plus())));
    amountField
        .getInputElement()
        .addEventListener("change", evt -> formatAmount(currencySelect.getSelectedOption()));
    FormExampleSupport.onSelect(currencySelect, this::formatAmount);
  }

  public static CurrenciesComponent create() {
    return new CurrenciesComponent();
  }

  private void formatAmount(SelectOption<CurrencyData> option) {
    if (nonNull(option) && !amountField.isEmpty()) {
      String value = amountField.getValue();
      String currencyCode = option.getKey();
      double amount = parseAmount(oldCurrencyCode, value);
      String formattedAmount = NumberFormat.getCurrencyFormat(currencyCode).format(amount);
      amountField.setValue(formattedAmount);
      this.oldCurrencyCode = option.getKey();
    }
  }

  private double parseAmount(String currencyCode, String amount) {
    try {
      return NumberFormat.getCurrencyFormat(currencyCode).parse(amount);
    } catch (Exception ex) {
      return Double.parseDouble(amount);
    }
  }

  public CurrenciesComponent setCurrencies(List<CurrencyData> currencies) {
    currencySelect.removeAllOptions();
    for (CurrencyData cd : currencies) {
      currencySelect.appendChild(
          SelectOption.create(
              cd.getCurrencyCode(), cd, cd.getCurrencyCode() + " - " + cd.getDisplayName()));
    }
    return this;
  }

  public Select<CurrencyData> getCurrencySelect() {
    return currencySelect;
  }

  public TextBox getAmountField() {
    return amountField;
  }
}
