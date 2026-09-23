// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static io.instanto.domino.client.FormExampleSupport.numbersOnly;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.grid.Column.span12;
import static org.dominokit.domino.ui.grid.Column.span4;
import static org.dominokit.domino.ui.style.ColorsCss.*;
import static org.dominokit.domino.ui.style.DominoCss.*;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.Constants;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.formsamples.shared.model.PaymentScheduleItem;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.button.Button;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.collapsible.Collapsible;
import org.dominokit.domino.ui.forms.*;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.forms.validations.ValidationResult;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.grid.flex.FlexItem;
import org.dominokit.domino.ui.grid.flex.FlexLayout;
import org.dominokit.domino.ui.icons.MdiIcon;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.lists.ListGroup;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class PaymentScheduleSection implements ImportSection {

  private final HTMLElement validationMessageElement =
      small().textContent("Total payment schedules should be 100%").addCss(dui_fg_red).element();

  private final Row paymentSchedulerListGroupRow;

  private TextBox numberOfDaysTextBox;

  private Select<String> paymentScheduleAfterSelect;

  private TextBox percentageTextBox;

  private RadioGroup<String> paymentScheduleRadioGroup;

  private ListGroup<PaymentScheduleItem> paymentScheduleItemsListGroup;

  private Button addButton;

  private Collapsible valuesContainerCollapsible;

  private Card paymentScheduleCard;

  private HTMLDivElement element = div().element();

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  public PaymentScheduleSection() {
    numberOfDaysTextBox =
        numbersOnly(
                TextBox.create("No. Of Days")
                    .appendChild(PrefixAddOn.of(Icons.looks()))
                    .setHelperText(Constants.NUMBERS_ONLY))
            .groupBy(fieldsGrouping)
            .setAutoValidation(true);
    Column numberOfDaysColumn = span4().appendChild(numberOfDaysTextBox).hide();
    paymentScheduleAfterSelect =
        Select.<String>create("After")
            .appendChild(
                SelectOption.create(
                    String.valueOf("Presentation Of Documents"),
                    "Presentation Of Documents",
                    "Presentation Of Documents"))
            .appendChild(
                SelectOption.create(
                    String.valueOf("Bill Of Lading Date"),
                    "Bill Of Lading Date",
                    "Bill Of Lading Date"))
            .appendChild(
                SelectOption.create(
                    String.valueOf("Commercial Invoice"),
                    "Commercial Invoice",
                    "Commercial Invoice"))
            .appendChild(PrefixAddOn.of(Icons.redo()))
            .groupBy(fieldsGrouping)
            .setAutoValidation(true);
    Column paymentScheduleAfterColumn = span4().appendChild(paymentScheduleAfterSelect).hide();
    percentageTextBox =
        numbersOnly(TextBox.create("Percentage"))
            .setHelperText("Numbers only")
            .setAutoValidation(true)
            .withValue("100")
            .setRequired(true)
            .groupBy(fieldsGrouping)
            .appendChild(PrefixAddOn.of(i().css("fas", "fa-percent", "fa-sm")))
            .addValidator(
                (field) -> {
                  int percentage = Integer.parseInt(percentageTextBox.getValue());
                  int remainingPercentage = remainingPercentage();
                  if (percentage > 0 && percentage <= remainingPercentage) {
                    return ValidationResult.valid();
                  }
                  return ValidationResult.invalid(
                      "Maximum allowed percentage is " + remainingPercentage);
                });
    paymentSchedulerListGroupRow = Row.create();
    paymentScheduleItemsListGroup =
        ListGroup.<PaymentScheduleItem>create()
            .setItemRenderer(
                (listGroup, listItem) -> {
                  MdiIcon delete =
                      Icons.delete()
                          .clickable()
                          .styler(style -> style.setMarginTop("-3px").setMarginLeft("10px"))
                          .addEventListener(
                              "click",
                              evt1 -> {
                                paymentScheduleItemsListGroup.removeItem(listItem);
                                percentageTextBox.setValue(remainingPercentage() + "");
                                addButton.show();
                                valuesContainerCollapsible.expand();
                                if (listGroup.getValues().size() == 0) {
                                  paymentSchedulerListGroupRow.hide();
                                }
                                revalidate();
                              });
                  FlexLayout flexLayout = FlexLayout.create().addCss(dui_p_2);
                  listItem.appendChild(
                      flexLayout
                          .appendChild(
                              FlexItem.create()
                                  .setFlexGrow(1)
                                  .apply(
                                      self -> {
                                        java.util.Optional.ofNullable(
                                                paymentScheduleRadioGroup
                                                    .getSelectedRadio()
                                                    .getLabel())
                                            .ifPresent(s -> self.appendChild(text(s)));
                                      }))
                          .appendChild(FlexItem.create().appendChild(delete)));
                  if (numberOfDaysTextBox.isRequired()) {
                    flexLayout.appendChild(
                        FlexItem.create()
                            .appendChild(
                                Badge.create(
                                        listItem.getValue().getNumberOfDays()
                                            + " days after "
                                            + listItem.getValue().getAfterIncident().toLowerCase())
                                    .addCss(dui_bg_green)));
                  }
                  flexLayout.appendChild(
                      FlexItem.create()
                          .appendChild(
                              Badge.create(listItem.getValue().getPercentage() + "%")
                                  .addCss(dui_bg_green)));
                });
    paymentScheduleRadioGroup =
        RadioGroup.<String>create("paymentSchedule")
            .appendChild(Radio.create("SIGHT", "Payment Sight").check())
            .appendChild(Radio.create("NEGOTIATION", "Negotiation"))
            .appendChild(Radio.create("ACCEPTANCE", "Acceptance at"))
            .appendChild(Radio.create("DEFERRED", "Deferred Payment"))
            .addChangeListener(
                (oldValue, value) -> {
                  if (value.equals("DEFERRED") || value.equals("ACCEPTANCE")) {
                    numberOfDaysColumn.show();
                    paymentScheduleAfterColumn.show();
                    numberOfDaysTextBox.setRequired(true);
                    paymentScheduleAfterSelect.setRequired(true);
                  } else {
                    numberOfDaysColumn.hide();
                    paymentScheduleAfterColumn.hide();
                    numberOfDaysTextBox.setRequired(false);
                    paymentScheduleAfterSelect.setRequired(false);
                  }
                })
            .horizontal();
    paymentScheduleCard = Card.create("Payment Schedule *");
    addButton =
        Button.create(Icons.plus())
            .setTextContent("ADD")
            .addCss(org.dominokit.domino.ui.style.DominoCss.dui_transparent)
            .styler(sampleStyle -> sampleStyle.setMarginTop("-10px"));
    paymentScheduleCard
        .getHeader()
        .getMainHeader()
        .appendChild(
            addButton
                .addClickListener(
                    evt -> {
                      if (fieldsGrouping.validate().isValid()) {
                        addPaymentSchedule();
                      }
                    })
                .element());
    Row paymentTypeRow = Row.create().appendChild(span12().appendChild(paymentScheduleRadioGroup));
    Row paymentValuesRow =
        Row.create()
            .appendChild(span4().appendChild(percentageTextBox))
            .appendChild(numberOfDaysColumn)
            .appendChild(paymentScheduleAfterColumn);
    HTMLDivElement valuesContainer =
        div().appendChild(paymentTypeRow).appendChild(paymentValuesRow).element();
    valuesContainerCollapsible = Collapsible.create(valuesContainer).expand();
    element.appendChild(
        paymentScheduleCard
            .appendChild(valuesContainer)
            .appendChild(
                paymentSchedulerListGroupRow
                    .appendChild(Column.span12().appendChild(paymentScheduleItemsListGroup))
                    .hide())
            .element());
  }

  private int remainingPercentage() {
    List<PaymentScheduleItem> allValues = paymentScheduleItemsListGroup.getValues();
    return 100 - allValues.stream().mapToInt(PaymentScheduleItem::getPercentage).sum();
  }

  public void revalidate() {
    if (isInvalidatedCard(paymentScheduleCard) && remainingPercentage() == 0) {
      markCardValidation(paymentScheduleCard, true, false);
      markWithValidationMessage(true);
    }
  }

  private void addPaymentSchedule() {
    PaymentScheduleItem item = new PaymentScheduleItem();
    item.setType(paymentScheduleRadioGroup.getValue());
    if (paymentScheduleAfterSelect.isRequired())
      item.setAfterIncident(paymentScheduleAfterSelect.getValue());
    if (numberOfDaysTextBox.isRequired())
      item.setNumberOfDays(Integer.parseInt(numberOfDaysTextBox.getValue()));
    item.setPercentage(Integer.parseInt(percentageTextBox.getValue()));
    paymentScheduleItemsListGroup.addItem(item);
    int remainingPercentage = remainingPercentage();
    if (remainingPercentage == 0) {
      addButton.hide();
      valuesContainerCollapsible.collapse();
    } else {
      if (addButton.isHidden()) addButton.show();
      if (valuesContainerCollapsible.isCollapsed()) valuesContainerCollapsible.expand();
      percentageTextBox.setValue(remainingPercentage + "");
    }
    numberOfDaysTextBox.clear();
    paymentScheduleAfterSelect.clear();
    paymentSchedulerListGroupRow.show();
    revalidate();
  }

  private void markWithValidationMessage(boolean valid) {
    if (!valid) {
      paymentScheduleCard.getHeader().getDescriptionElement().appendChild(validationMessageElement);
    } else {
      validationMessageElement.remove();
    }
  }

  @Override
  public boolean validate() {
    boolean valid = remainingPercentage() == 0;
    markCardValidation(paymentScheduleCard, valid);
    markWithValidationMessage(valid);
    return valid;
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    letterOfCredit.getPaymentSchedule().addAll(paymentScheduleItemsListGroup.getValues());
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
