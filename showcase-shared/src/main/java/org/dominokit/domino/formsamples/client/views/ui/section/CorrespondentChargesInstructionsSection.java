// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import org.dominokit.domino.formsamples.shared.model.ChargesInstructions;
import org.dominokit.domino.formsamples.shared.model.CorporateProfile;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;

public class CorrespondentChargesInstructionsSection implements ImportSection {

  private SwitchButton correspondentChargesSwitch;

  private CorporateAccountsSelect corporateAccountsSelect;

  private Card correspondentChargesInstructionsCard;

  private HTMLDivElement element = div().element();

  public CorrespondentChargesInstructionsSection(CorporateProfile corporateProfile) {
    correspondentChargesInstructionsCard =
        Card.create("Correspondent Charges Instructions", "").collapse();
    correspondentChargesInstructionsCard.getBody().style().setPaddingTop("40px");
    correspondentChargesSwitch =
        SwitchButton.create()
            .styler(sampleStyle -> sampleStyle.setMarginBottom("0px"))
            .setOnTitle("Applicant")
            .setOffTitle("Beneficiary")
            .addChangeListener(
                (oldValue, value) -> {
                  if (value) {
                    correspondentChargesInstructionsCard.expand();
                  } else {
                    correspondentChargesInstructionsCard.collapse();
                    revalidate();
                  }
                });
    correspondentChargesInstructionsCard
        .getHeader()
        .getDescriptionElement()
        .appendChild(correspondentChargesSwitch.element());
    corporateAccountsSelect =
        CorporateAccountsSelect.create("Charges Instructions", corporateProfile);
    FormExampleSupport.onSelect(
        corporateAccountsSelect
            .getAccountSelect()
            .withOptionsMenu(
                (select, menu) ->
                    menu.setDropDirection(
                        org.dominokit.domino.ui.menu.direction.DropDirection.TOP_LEFT))
            .setAutoValidation(true)
            .setRequired(true),
        option -> revalidate());
    element.appendChild(
        correspondentChargesInstructionsCard
            .appendChild(
                Row.create().appendChild(Column.span6().appendChild(corporateAccountsSelect)))
            .element());
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    ChargesInstructions chargesInstructions = letterOfCredit.getChargesInstructions();
    chargesInstructions.setOutsideCountryChargesOn(
        correspondentChargesSwitch.getValue() ? "APPLICANT" : "BENEFICIARIES");
  }

  @Override
  public boolean validate() {
    boolean valid = isValid();
    markCardValidation(correspondentChargesInstructionsCard, valid);
    return valid;
  }

  public void revalidate() {
    if (isInvalidatedCard(correspondentChargesInstructionsCard) && isValid()) {
      markCardValidation(correspondentChargesInstructionsCard, true, false);
    }
  }

  private boolean isValid() {
    return !correspondentChargesSwitch.getValue()
        || corporateAccountsSelect.getAccountSelect().validate().isValid();
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
