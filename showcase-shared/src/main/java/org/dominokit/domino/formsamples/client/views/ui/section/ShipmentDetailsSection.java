// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.Constants.DATE_PATTERN;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.grid.Column.span6;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.formsamples.shared.model.ShipmentDetails;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.DateBox;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;
import org.gwtproject.i18n.shared.DateTimeFormat;

public class ShipmentDetailsSection implements ImportSection {

  private DateBox latestDateOfShipmentDateBox;

  private Select<String> shipmentBySelect;

  private SwitchButton partialShipmentSwitch;

  private SwitchButton transShipmentSwitch;

  private TextBox shipmentFromTextBox;

  private TextBox shipmentToTextBox;

  private TextBox placeOfDestinationTextBox;

  private Select<String> termsOfDeliverySelect;

  private Card card;

  private HTMLDivElement element = div().element();

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  public ShipmentDetailsSection() {
    card = Card.create();
    latestDateOfShipmentDateBox =
        DateBox.create()
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .setPattern(DATE_PATTERN)
            .setHelperText(DATE_PATTERN)
            .setLabel("Latest Date Of Shipment")
            .appendChild(PrefixAddOn.of(Icons.calendar_range()));
    latestDateOfShipmentDateBox.addChangeListener((date, dateTimeFormatInfo) -> revalidate());
    latestDateOfShipmentDateBox.getInputElement().addEventListener("input", evt -> revalidate());
    shipmentBySelect =
        FormExampleSupport.onSelect(
            Select.<String>create("Shipment By")
                .groupBy(fieldsGrouping)
                .setRequired(true)
                .setAutoValidation(true)
                .appendChild(PrefixAddOn.of(Icons.ship_wheel()))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Air Freight"), "AIR_FREIGHT", "Air Freight"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Sea Freight"), "SEA_FREIGHT", "Sea Freight"))
                .appendChild(SelectOption.create(String.valueOf("Land"), "LAND", "Land"))
                .appendChild(
                    SelectOption.create(String.valueOf("Multimodal"), "MULTIMODAL", "Multimodal")),
            option -> revalidate());
    partialShipmentSwitch =
        SwitchButton.create("Partial Shipments", "Not permitted", "Permitted").withValue(true);
    transShipmentSwitch = SwitchButton.create("Transshipment", "Not permitted", "Permitted");
    shipmentFromTextBox =
        TextBox.create("Shipment From")
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .appendChild(PrefixAddOn.of(Icons.location_enter()));
    shipmentFromTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    shipmentToTextBox =
        TextBox.create("Shipment To")
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .appendChild(PrefixAddOn.of(Icons.location_enter()));
    shipmentToTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    placeOfDestinationTextBox =
        TextBox.create("Place Of Destination")
            .groupBy(fieldsGrouping)
            .setRequired(true)
            .setAutoValidation(true)
            .appendChild(PrefixAddOn.of(Icons.location_enter()));
    placeOfDestinationTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    termsOfDeliverySelect =
        FormExampleSupport.onSelect(
            Select.<String>create("Terms Of Delivery (Incoterms 2010)")
                .groupBy(fieldsGrouping)
                .setRequired(true)
                .setAutoValidation(true)
                .appendChild(PrefixAddOn.of(Icons.wallet_membership()))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("EXW – Ex Works (named place of delivery)"),
                        "EXW",
                        "EXW – Ex Works (named place of delivery)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("FCA – Free Carrier (named place of delivery)"),
                        "FCA",
                        "FCA – Free Carrier (named place of delivery)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("CPT – Carriage Paid To (named place of destination)"),
                        "CPT",
                        "CPT – Carriage Paid To (named place of destination)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf(
                            "CIP – Carriage and Insurance Paid to (named place of destination)"),
                        "CIP",
                        "CIP – Carriage and Insurance Paid to (named place of destination)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf(
                            "DAT – Delivered At Terminal (named terminal at port or place of"
                                + " destination)"),
                        "DAT",
                        "DAT – Delivered At Terminal (named terminal at port or place of"
                            + " destination)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("DAP – Delivered At Place (named place of destination)"),
                        "DAP",
                        "DAP – Delivered At Place (named place of destination)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("DDP – Delivered Duty Paid (named place of destination)"),
                        "DDP",
                        "DDP – Delivered Duty Paid (named place of destination)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("FAS – Free Alongside Ship (named port of shipment)"),
                        "FAS",
                        "FAS – Free Alongside Ship (named port of shipment)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("FOB – Free on Board (named port of shipment)"),
                        "FOB",
                        "FOB – Free on Board (named port of shipment)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("CFR – Cost and Freight (named port of destination)"),
                        "CFR",
                        "CFR – Cost and Freight (named port of destination)"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf(
                            "CIF – Cost, Insurance & Freight (named port of destination)"),
                        "CIF",
                        "CIF – Cost, Insurance & Freight (named port of destination)")),
            option -> revalidate());
    element.appendChild(BlockHeader.create("Shipment Details *").element());
    element.appendChild(
        card.styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(
                Row.create()
                    .appendChild(span6().appendChild(latestDateOfShipmentDateBox))
                    .appendChild(span6().appendChild(transShipmentSwitch)))
            .appendChild(
                Row.create()
                    .appendChild(span6().appendChild(shipmentBySelect))
                    .appendChild(span6().appendChild(partialShipmentSwitch)))
            .appendChild(
                Row.create()
                    .appendChild(span6().appendChild(shipmentFromTextBox))
                    .appendChild(span6().appendChild(shipmentToTextBox)))
            .appendChild(
                Row.create()
                    .appendChild(span6().appendChild(termsOfDeliverySelect))
                    .appendChild(span6().appendChild(placeOfDestinationTextBox)))
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
    ShipmentDetails shipmentDetails = letterOfCredit.getShipmentDetails();
    shipmentDetails.setLatestDateOfShipment(
        DateTimeFormat.getFormat(DATE_PATTERN).format(latestDateOfShipmentDateBox.getValue()));
    shipmentDetails.setPartialShipmentsPermitted(partialShipmentSwitch.getValue());
    shipmentDetails.setTransshipmentPermitted(transShipmentSwitch.getValue());
    shipmentDetails.setShipmentBy(shipmentBySelect.getValue());
    shipmentDetails.setShipmentFrom(shipmentFromTextBox.getValue());
    shipmentDetails.setShipmentTo(shipmentToTextBox.getValue());
    shipmentDetails.setPlaceOfDestination(placeOfDestinationTextBox.getValue());
    shipmentDetails.setTermsOfDelivery(termsOfDeliverySelect.getValue());
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
