// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static io.instanto.domino.client.FormExampleSupport.numbersOnly;
import static org.dominokit.domino.formsamples.client.views.ui.Constants.DATE_PATTERN;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.grid.Column.span3;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.Constants;
import org.dominokit.domino.formsamples.client.views.ui.CountriesComponent;
import org.dominokit.domino.formsamples.shared.model.Country;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.formsamples.shared.model.Validity;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.DateBox;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;
import org.gwtproject.i18n.shared.DateTimeFormat;

public class ValiditySection implements ImportSection {

  private Select<Country> countrySelect;

  private Select<String> citySelect;

  private DateBox validityExpiryDateBox;

  private TextBox daysForShipmentTextBox;

  private Card card;

  private HTMLDivElement element = div().element();

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  public ValiditySection(List<Country> countries) {
    element.appendChild(BlockHeader.create("Validity *").element());
    CountriesComponent countriesComponent = CountriesComponent.create(countries);
    card = Card.create();
    revalidate();
    countrySelect =
        FormExampleSupport.onSelect(
            countriesComponent
                .getCountriesSelect()
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping),
            option -> revalidate());
    citySelect =
        FormExampleSupport.onSelect(
            countriesComponent
                .getCitiesSelect()
                .setLabel("City / Town")
                .setRequired(true)
                .setAutoValidation(true)
                .groupBy(fieldsGrouping),
            option -> revalidate());
    validityExpiryDateBox =
        DateBox.create()
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .setPattern(DATE_PATTERN)
            .setLabel("Expiry Date Of Credit")
            .appendChild(PrefixAddOn.of(Icons.calendar_range()))
            .setHelperText(DATE_PATTERN);
    validityExpiryDateBox.getInputElement().addEventListener("input", evt -> revalidate());
    validityExpiryDateBox.addChangeListener((date, dateTimeFormatInfo) -> revalidate());
    daysForShipmentTextBox =
        TextBox.create("Days for Shipping Documents")
            .withValue("21")
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .appendChild(PrefixAddOn.of(Icons.looks()))
            .setHelperText(Constants.NUMBERS_ONLY);
    daysForShipmentTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    element.appendChild(
        card.styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(
                Row.create()
                    .appendChild(span3().appendChild(validityExpiryDateBox))
                    .appendChild(span3().appendChild(countrySelect))
                    .appendChild(span3().appendChild(citySelect))
                    .appendChild(span3().appendChild(numbersOnly(daysForShipmentTextBox))))
            .element());
  }

  private void revalidate() {
    if (isInvalidatedCard(card) && fieldsGrouping.validate().isValid()) {
      markCardValidation(card, true, false);
    }
  }

  @Override
  public boolean validate() {
    boolean valid = fieldsGrouping.validate().isValid();
    markCardValidation(card, valid);
    return valid;
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    Validity validity = letterOfCredit.getValidity();
    validity.setCity(citySelect.getValue());
    validity.setCountry(countrySelect.getValue().getIso());
    validity.setDaysForPresentingDocuments(Integer.parseInt(daysForShipmentTextBox.getValue()));
    validity.setExpiryDateOfCredit(
        DateTimeFormat.getFormat(DATE_PATTERN).format(validityExpiryDateBox.getValue()));
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
