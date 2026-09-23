// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.shared.model.Account;
import org.dominokit.domino.formsamples.shared.model.Beneficiary;
import org.dominokit.domino.formsamples.shared.model.ContactPerson;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.formsamples.shared.model.SellerBeneficiary;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class BeneficiarySection implements ImportSection {

  private Select<Beneficiary> beneficiariesSelect;

  private Select<Account> accountsSelect;

  private Card card;

  private HTMLDivElement element = div().element();

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  public BeneficiarySection(List<Beneficiary> beneficiaries) {
    element.appendChild(BlockHeader.create("Seller(Beneficiary) *").element());
    card = Card.create();
    beneficiariesSelect =
        FormExampleSupport.onSelect(
            Select.<Beneficiary>create("Beneficiary Name")
                .groupBy(fieldsGrouping)
                .setRequired(true)
                .setAutoValidation(true)
                .appendChild(PrefixAddOn.of(Icons.label())),
            option -> revalidate());
    accountsSelect =
        FormExampleSupport.onSelect(
            Select.<Account>create("Through")
                .appendChild(PrefixAddOn.of(Icons.bank()))
                .groupBy(fieldsGrouping)
                .setRequired(true)
                .setAutoValidation(true),
            option -> revalidate());
    for (Beneficiary beneficiary : beneficiaries) {
      beneficiariesSelect.appendChild(
          SelectOption.create(
              String.valueOf(beneficiary.getName()), beneficiary, beneficiary.getName()));
    }
    FormExampleSupport.onSelect(
        beneficiariesSelect,
        option -> {
          accountsSelect.removeAllOptions();
          Beneficiary beneficiary = beneficiariesSelect.getValue();
          List<Account> accounts = beneficiary.getAccounts();
          for (Account account : accounts) {
            accountsSelect.appendChild(
                SelectOption.create(
                    String.valueOf(account.getAccountAlias()), account, account.getAccountAlias()));
          }
        });
    element.appendChild(
        card.styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(
                Row.create()
                    .appendChild(Column.span6().appendChild(beneficiariesSelect))
                    .appendChild(Column.span6().appendChild(accountsSelect)))
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
    SellerBeneficiary beneficiary = letterOfCredit.getBeneficiary();
    Beneficiary selectedBeneficiary = beneficiariesSelect.getValue();
    beneficiary.setReference(selectedBeneficiary.getId());
    beneficiary.setName(selectedBeneficiary.getName());
    beneficiary.setAddress(selectedBeneficiary.getAddress());
    ContactPerson contactPerson = new ContactPerson();
    contactPerson.setAddress(selectedBeneficiary.getContactPerson().getAddress());
    contactPerson.setEmail(selectedBeneficiary.getContactPerson().getEmail());
    contactPerson.setName(selectedBeneficiary.getContactPerson().getName());
    beneficiary.setContactPerson(contactPerson);
    beneficiary.setAccount(accountsSelect.getValue());
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
