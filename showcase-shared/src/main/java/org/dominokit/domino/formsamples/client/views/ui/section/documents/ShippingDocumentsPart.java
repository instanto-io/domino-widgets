// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section.documents;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createCopiesField;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createDescriptionField;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createRequiredField;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.grid.Column.span6;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.section.ImportSection;
import org.dominokit.domino.formsamples.shared.model.AirwayBill;
import org.dominokit.domino.formsamples.shared.model.Bank;
import org.dominokit.domino.formsamples.shared.model.DocumentsRequired;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.formsamples.shared.model.OceanBillsOfLanding;
import org.dominokit.domino.formsamples.shared.model.TruckConsignmentNote;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class ShippingDocumentsPart implements ImportSection {

  private final SwitchButton shippingDocumentsSwitchButton;

  private final Select<Bank> orderOfBankSelect;

  private final Select<String> freightSelect;

  private TextBox shippingDocumentsCopiesTextBox;

  private TextBox shippingDocumentsDescriptionTextBox;

  private Select<String> shippingDocumentsTypeSelect;

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  private Card card;

  private HTMLDivElement element = div().element();

  public ShippingDocumentsPart(List<Bank> banks) {
    Row shippingDocumentInfoRow = Row.create();
    Row shippingDocumentSelectRow = Row.create();
    shippingDocumentsCopiesTextBox =
        createCopiesField().groupBy(fieldsGrouping).setAutoValidation(true).setRequired(true);
    shippingDocumentsCopiesTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    shippingDocumentsDescriptionTextBox =
        createDescriptionField().groupBy(fieldsGrouping).setAutoValidation(true).setRequired(true);
    shippingDocumentsDescriptionTextBox
        .getInputElement()
        .addEventListener("input", evt -> revalidate());
    orderOfBankSelect =
        FormExampleSupport.onSelect(
            Select.<Bank>create("Order of")
                .groupBy(fieldsGrouping)
                .appendChild(PrefixAddOn.of(Icons.domain()))
                .setAutoValidation(true)
                .setRequired(true),
            option -> revalidate());
    for (Bank bank : banks) {
      orderOfBankSelect.appendChild(SelectOption.create(bank.getSwiftCode(), bank, bank.getName()));
    }
    freightSelect =
        FormExampleSupport.onSelect(
            Select.<String>create("Freight")
                .groupBy(fieldsGrouping)
                .setAutoValidation(true)
                .setRequired(true)
                .appendChild(PrefixAddOn.of(Icons.credit_card()))
                .appendChild(SelectOption.create(String.valueOf("Prepaid"), "Prepaid", "Prepaid"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Payable at destination"),
                        "Payable at destination",
                        "Payable at destination")),
            option -> revalidate());
    shippingDocumentsTypeSelect =
        FormExampleSupport.onSelect(
            Select.<String>create("Shipping documents type")
                .groupBy(fieldsGrouping)
                .setRequired(true)
                .setAutoValidation(true)
                .appendChild(PrefixAddOn.of(Icons.ship_wheel()))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Ocean bills of lading in"),
                        "Ocean bills of lading in",
                        "Ocean bills of lading in"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Airway bill in"), "Airway bill in", "Airway bill in"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Truck consignment note"),
                        "Truck consignment note",
                        "Truck consignment note"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Multimodal transport document"),
                        "Multimodal transport document",
                        "Multimodal transport document"))
                .selectAt(0),
            option -> revalidate());
    shippingDocumentsSwitchButton =
        createRequiredField()
            .addChangeListener(
                (oldValue, value) -> {
                  if (value) {
                    card.expand();
                  } else {
                    card.collapse();
                    revalidate();
                  }
                });
    card =
        Card.create("Shipping documents")
            .withBody((card, body) -> body.setPaddingTop("40px"))
            .collapse();
    card.getHeader().getDescriptionElement().appendChild(shippingDocumentsSwitchButton.element());
    element.appendChild(
        card.appendChild(Row.create().appendChild(span6().appendChild(shippingDocumentsTypeSelect)))
            .appendChild(
                shippingDocumentInfoRow
                    .appendChild(span6().appendChild(shippingDocumentsCopiesTextBox))
                    .appendChild(span6().appendChild(shippingDocumentsDescriptionTextBox)))
            .appendChild(
                shippingDocumentSelectRow
                    .appendChild(span6().appendChild(orderOfBankSelect))
                    .appendChild(span6().appendChild(freightSelect)))
            .element());
  }

  public void revalidate() {
    if (isInvalidatedCard(card) && fieldsGrouping.validate().isValid()) {
      markCardValidation(card, true, false);
    }
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    if (shippingDocumentsSwitchButton.getValue()) {
      DocumentsRequired documentsRequired = letterOfCredit.getDocumentsRequired();
      if ("Ocean bills of lading in".equals(shippingDocumentsTypeSelect.getValue())) {
        OceanBillsOfLanding oceanBillsOfLanding = new OceanBillsOfLanding();
        oceanBillsOfLanding.setRequired(true);
        oceanBillsOfLanding.setNumberOfCopies(
            Integer.parseInt(shippingDocumentsCopiesTextBox.getValue()));
        oceanBillsOfLanding.setDescription(shippingDocumentsDescriptionTextBox.getValue());
        documentsRequired.setOceanBillsOfLanding(oceanBillsOfLanding);
      } else if ("Airway bill in".equals(shippingDocumentsTypeSelect.getValue())) {
        AirwayBill airwayBill = new AirwayBill();
        airwayBill.setRequired(true);
        airwayBill.setNumberOfCopies(Integer.parseInt(shippingDocumentsCopiesTextBox.getValue()));
        airwayBill.setDescription(shippingDocumentsDescriptionTextBox.getValue());
        documentsRequired.setAirwayBill(airwayBill);
      } else if ("Truck consignment note".equals(shippingDocumentsTypeSelect.getValue())) {
        TruckConsignmentNote truckConsignmentNote = new TruckConsignmentNote();
        truckConsignmentNote.setRequired(true);
        truckConsignmentNote.setNumberOfCopies(
            Integer.parseInt(shippingDocumentsCopiesTextBox.getValue()));
        truckConsignmentNote.setDescription(shippingDocumentsDescriptionTextBox.getValue());
        documentsRequired.setTruckConsignmentNote(truckConsignmentNote);
      }
    }
  }

  @Override
  public boolean validate() {
    boolean valid =
        !shippingDocumentsSwitchButton.getValue() || fieldsGrouping.validate().isValid();
    markCardValidation(card, valid);
    return valid;
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
