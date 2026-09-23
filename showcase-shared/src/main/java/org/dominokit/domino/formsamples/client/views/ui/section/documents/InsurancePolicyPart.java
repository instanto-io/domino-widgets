// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section.documents;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.grid.Column.span6;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.formsamples.client.views.ui.section.ImportSection;
import org.dominokit.domino.formsamples.shared.model.DocumentsRequired;
import org.dominokit.domino.formsamples.shared.model.Insurance;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class InsurancePolicyPart implements ImportSection {

  private TextBox insuranceCompanyTextBox;

  private TextBox insurancePolicyNumberTextBox;

  private SwitchButton insurancePolicyRequiredSwitchButton;

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  private Card insurancePolicyCard;

  private HTMLDivElement element = div().element();

  public InsurancePolicyPart() {
    insuranceCompanyTextBox =
        TextBox.create("Insurance company")
            .groupBy(fieldsGrouping)
            .appendChild(PrefixAddOn.of(Icons.bank()))
            .setAutoValidation(true)
            .setRequired(true);
    insuranceCompanyTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    insurancePolicyNumberTextBox =
        TextBox.create("Insurance policy number")
            .groupBy(fieldsGrouping)
            .appendChild(PrefixAddOn.of(Icons.phone()))
            .setAutoValidation(true)
            .setRequired(true);
    insurancePolicyNumberTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    insurancePolicyRequiredSwitchButton =
        SwitchButton.create()
            .styler(sampleStyle -> sampleStyle.setMarginBottom("0px"))
            .setOnTitle("Applicant")
            .setOffTitle("Beneficiary")
            .addChangeListener(
                (oldValue, value) -> {
                  if (value) {
                    insurancePolicyCard.expand();
                  } else {
                    insurancePolicyCard.collapse();
                    revalidate();
                  }
                });
    insurancePolicyCard =
        Card.create("Insurance policy required by")
            .withBody((card, body) -> body.setPaddingTop("40px"))
            .collapse();
    insurancePolicyCard
        .getHeader()
        .getDescriptionElement()
        .appendChild(insurancePolicyRequiredSwitchButton.element());
    Row insurancePolicyRow =
        Row.create()
            .appendChild(span6().appendChild(insuranceCompanyTextBox))
            .appendChild(span6().appendChild(insurancePolicyNumberTextBox));
    element.appendChild(insurancePolicyCard.appendChild(insurancePolicyRow).element());
  }

  public void revalidate() {
    if (isInvalidatedCard(insurancePolicyCard) && isValid()) {
      markCardValidation(insurancePolicyCard, true, false);
    }
  }

  private boolean isValid() {
    return !insurancePolicyRequiredSwitchButton.getValue() || fieldsGrouping.validate().isValid();
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    DocumentsRequired documentsRequired = letterOfCredit.getDocumentsRequired();
    if (!insurancePolicyRequiredSwitchButton.isChecked()) {
      Insurance insurance = new Insurance();
      insurance.setInsuranceCompany(insuranceCompanyTextBox.getValue());
      insurance.setInsurancePolicyNumber(insurancePolicyNumberTextBox.getValue());
      documentsRequired.setInsurance(insurance);
    }
  }

  @Override
  public boolean validate() {
    boolean valid = isValid();
    markCardValidation(insurancePolicyCard, valid);
    return valid;
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
