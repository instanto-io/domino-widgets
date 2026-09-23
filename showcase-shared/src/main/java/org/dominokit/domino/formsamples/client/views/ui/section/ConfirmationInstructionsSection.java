// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import org.dominokit.domino.formsamples.shared.model.ConfirmationInstructions;
import org.dominokit.domino.formsamples.shared.model.CorporateProfile;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;

public class ConfirmationInstructionsSection implements ImportSection {

  private SwitchButton chargesInstructionsRequiredSwitch;

  private SwitchButton confirmationChargesOnSwitch;

  private CorporateAccountsSelect confirmationChargesAccountSelect;

  private Card confirmationInstructionsCard;

  private HTMLDivElement element = div().element();

  public ConfirmationInstructionsSection(CorporateProfile corporateProfile) {
    confirmationChargesAccountSelect =
        CorporateAccountsSelect.create("Confirmation charges account", corporateProfile);
    FormExampleSupport.onSelect(
            confirmationChargesAccountSelect
                .getAccountSelect()
                .setAutoValidation(true)
                .setRequired(true),
            option -> revalidate())
        .hide();
    chargesInstructionsRequiredSwitch =
        SwitchButton.create()
            .styler(sampleStyle -> sampleStyle.setMarginBottom("0px"))
            .setOffTitle("Required")
            .addChangeListener(
                (oldValue, value) -> {
                  if (value) {
                    confirmationInstructionsCard.expand();
                  } else {
                    confirmationInstructionsCard.collapse();
                    revalidate();
                  }
                });
    confirmationChargesOnSwitch =
        SwitchButton.create("Confirmation charges on", "Beneficiary", "Applicant")
            .addChangeListener(
                (oldValue, value) -> {
                  if (value) {
                    confirmationChargesAccountSelect.show();
                  } else {
                    confirmationChargesAccountSelect.hide();
                    revalidate();
                  }
                });
    confirmationInstructionsCard = Card.create("Confirmation Instructions", "").collapse();
    confirmationInstructionsCard
        .getHeader()
        .getDescriptionElement()
        .appendChild(chargesInstructionsRequiredSwitch.element());
    confirmationInstructionsCard.getBody().style().setPaddingTop("40px");
    element.appendChild(
        confirmationInstructionsCard
            .appendChild(
                Row.create()
                    .appendChild(Column.span6().appendChild(confirmationChargesOnSwitch))
                    .appendChild(Column.span6().appendChild(confirmationChargesAccountSelect)))
            .element());
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    ConfirmationInstructions confirmationInstructions =
        letterOfCredit.getConfirmationInstructions();
    confirmationInstructions.setConfirmationRequired(chargesInstructionsRequiredSwitch.getValue());
    if (confirmationInstructions.isConfirmationRequired()) {
      confirmationInstructions.setConfirmationChargesOn(
          confirmationChargesOnSwitch.getValue() ? "APPLICANT" : "BENEFICIARIES");
    }
  }

  @Override
  public boolean validate() {
    boolean valid = isValid();
    markCardValidation(confirmationInstructionsCard, valid);
    return valid;
  }

  public void revalidate() {
    if (isInvalidatedCard(confirmationInstructionsCard) && isValid()) {
      markCardValidation(confirmationInstructionsCard, true, false);
    }
  }

  private boolean isValid() {
    return !chargesInstructionsRequiredSwitch.getValue()
        || (!confirmationChargesOnSwitch.getValue()
            || confirmationChargesAccountSelect.getAccountSelect().validate().isValid());
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
