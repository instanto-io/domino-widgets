// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.BanksComponent;
import org.dominokit.domino.formsamples.shared.model.Bank;
import org.dominokit.domino.formsamples.shared.model.Branch;
import org.dominokit.domino.formsamples.shared.model.CorporateProfile;
import org.dominokit.domino.formsamples.shared.model.Issuer;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class IssuerBankSection implements ImportSection {

  private Select<Bank> issuerBanksSelect;

  private Select<Branch> issuerBranchesSelect;

  private TextBox issuerAddressTextBox;

  private TextBox issuerContactPersonTextBox;

  private Row issuerBankInfoRow;

  private Card card;

  private HTMLDivElement element = div().element();

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  public IssuerBankSection(CorporateProfile corporateProfile) {
    element.appendChild(BlockHeader.create("Issuer Bank *").element());
    List<Bank> banks = corporateProfile.getBanks();
    BanksComponent banksComponent = BanksComponent.create(banks);
    issuerBranchesSelect =
        banksComponent
            .getBranchesSelect()
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true);
    issuerBanksSelect =
        banksComponent
            .getBanksSelect()
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true);
    issuerAddressTextBox = TextBox.create("Address");
    issuerContactPersonTextBox = TextBox.create("Contact Person");
    issuerBankInfoRow =
        Row.create()
            .appendChild(
                Column.span6()
                    .appendChild(
                        issuerAddressTextBox
                            .appendChild(PrefixAddOn.of(Icons.location_enter()))
                            .setReadOnly(true)))
            .appendChild(
                Column.span6()
                    .appendChild(
                        issuerContactPersonTextBox
                            .appendChild(PrefixAddOn.of(Icons.account()))
                            .setReadOnly(true)))
            .hide();
    FormExampleSupport.onSelect(issuerBranchesSelect, option -> issuerBankInfoRow.show());
    FormExampleSupport.onSelect(issuerBanksSelect, option -> issuerBankInfoRow.hide());
    card = Card.create();
    element.appendChild(
        card.styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(
                Row.create()
                    .appendChild(Column.span6().appendChild(issuerBanksSelect))
                    .appendChild(Column.span6().appendChild(issuerBranchesSelect)))
            .appendChild(issuerBankInfoRow)
            .element());
    issuerBanksSelect.focus();
    FormExampleSupport.onSelect(
        issuerBranchesSelect,
        option -> {
          Branch branch = option.getValue();
          issuerAddressTextBox.setValue(
              branch.getAddress().getCountryISOCode() + " - " + branch.getAddress().getCity());
          issuerContactPersonTextBox.setValue(branch.getContactPerson().getName());
          markCardValidation(card, true, false);
        });
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    Issuer issuer = letterOfCredit.getIssuer();
    issuer.setBank(issuerBanksSelect.getValue().getSwiftCode());
    issuer.setBranch(issuerBranchesSelect.getValue().getName());
  }

  @Override
  public boolean validate() {
    boolean valid = fieldsGrouping.validate().isValid();
    markCardValidation(card, valid);
    return valid;
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
