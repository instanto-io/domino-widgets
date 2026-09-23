// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.validatePercent;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.CurrenciesComponent;
import org.dominokit.domino.formsamples.shared.model.CreditAmount;
import org.dominokit.domino.formsamples.shared.model.CurrencyData;
import org.dominokit.domino.formsamples.shared.model.LcAmount;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.CheckBox;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;
import org.gwtproject.i18n.client.NumberFormat;

public class CreditAmountAndToleranceSection implements ImportSection {

  private TextBox amountFieldTextBox;

  private Select<CurrencyData> currencySelect;

  private TextBox toleranceTextBox;

  private CheckBox maximumCheckBox;

  private Card card;

  private HTMLDivElement element = div().element();

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  public CreditAmountAndToleranceSection(List<CurrencyData> currencies) {
    element.appendChild(BlockHeader.create("Credit Amount And Tolerance *").element());
    card = Card.create();
    CurrenciesComponent currenciesComponent =
        CurrenciesComponent.create().setCurrencies(currencies);
    revalidate();
    amountFieldTextBox =
        currenciesComponent
            .getAmountField()
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true);
    amountFieldTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    currencySelect =
        FormExampleSupport.onSelect(
            currenciesComponent
                .getCurrencySelect()
                .groupBy(fieldsGrouping)
                .setRequired(true)
                .setAutoValidation(true),
            option -> revalidate());
    toleranceTextBox =
        FormExampleSupport.numbersOnly(TextBox.create("Tolerance"))
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .addValidator(field -> this.validateTolerance());
    toleranceTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    maximumCheckBox = CheckBox.create("Maximum");
    maximumCheckBox.addChangeListener(
        (oldValue, value) -> {
          if (value) {
            toleranceTextBox.hide();
            toleranceTextBox.setRequired(false);
          } else {
            toleranceTextBox.show();
            toleranceTextBox.setRequired(true);
            toleranceTextBox.setAutoValidation(true);
            toleranceTextBox.validate();
          }
          revalidate();
        });
    element.appendChild(
        card.styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(
                Row.create()
                    .appendChild(Column.span3().appendChild(currencySelect))
                    .appendChild(Column.span3().appendChild(amountFieldTextBox))
                    .appendChild(Column.span3().appendChild(maximumCheckBox))
                    .appendChild(
                        Column.span3()
                            .appendChild(
                                toleranceTextBox.appendChild(
                                    PrefixAddOn.of(i().css("fas", "fa-percent", "fa-sm"))))))
            .element());
  }

  private void revalidate() {
    if (isInvalidatedCard(card) && fieldsGrouping.validate().isValid()) {
      markCardValidation(card, true, false);
    }
  }

  private ValidationResult validateTolerance() {
    if (maximumCheckBox.getValue()) return ValidationResult.valid();
    return validatePercent(toleranceTextBox);
  }

  @Override
  public boolean validate() {
    boolean valid = fieldsGrouping.validate().isValid();
    markCardValidation(card, valid);
    return valid;
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    CreditAmount creditAmount = letterOfCredit.getCreditAmount();
    creditAmount.setMaximum(maximumCheckBox.getValue());
    if (!creditAmount.isMaximum()) {
      creditAmount.setTolerance(Double.parseDouble(toleranceTextBox.getValue()));
    }
    LcAmount lcAmount = new LcAmount();
    lcAmount.setCurrency(currencySelect.getValue().getCurrencyCode());
    double amount =
        NumberFormat.getCurrencyFormat(lcAmount.getCurrency()).parse(amountFieldTextBox.getValue());
    lcAmount.setValue(amount);
    creditAmount.setLcAmount(lcAmount);
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
