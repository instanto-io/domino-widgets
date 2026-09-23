package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/layouts.feature", runner = DominoRunner.class)
public class LayoutSteps extends BrowserSteps {
  private static final String EXAMPLES = "#gallery-examples";
  private static final String CARD = EXAMPLES + " .dui-card";
  private static final String TEXT_INPUT = "input[type=text]";

  private Browser.Element card(String heading) {
    return withText(root(), CARD, heading);
  }

  private Browser.Element input(Browser.Element card, int index) {
    return card.all(TEXT_INPUT).get(index);
  }

  @Then("I can add and remove an additional document")
  public void additionalDocument() {
    var card = card("Other documents");
    input(card, 0).fill("1.5");
    input(card, 1).fill("Shipment certificate");
    card.text("ADD", false).click();
    contains(card, "Enter a positive whole number");
    assertFalse(card.text().contains("Shipment certificate"));
    input(card, 0).fill("2");
    card.text("ADD", false).click();
    var item = card.text("Shipment certificate", false);
    visible(item, true);
    contains(card, "2 Copies");
    card.first(".mdi-delete").click();
    check(() -> assertFalse(card.text().contains("Shipment certificate")));
  }

  @Then("I can enable and complete the packing list section")
  public void packingList() {
    var card = card("Packing list in");
    assertTrue(card.visible());
    var copies = input(card, 0);
    visible(copies, false);
    card.first(".dui-switch-track").click();
    browser.settle();
    visible(copies, true);
    copies.fill("2");
    input(card, 1).fill("A copy for each shipment");
    root().text("Submit", false).click();
    check(() -> assertFalse(card.attr("class").contains("invalid-section")));
    card.first(".dui-switch-track").click();
    browser.settle();
    visible(copies, false);
    assertTrue(card.visible());
  }

  @Then("the flex playground adds and resets its blocks")
  public void flex() {
    int initial = root().all(".demo-flex-layout-block").size();
    root().text("ADD BLOCK", false).click();
    check(() -> assertEquals(initial + 1, root().all(".demo-flex-layout-block").size()));
    root().text("RESET", false).click();
    check(() -> assertEquals(initial, root().all(".demo-flex-layout-block").size()));
  }

  @Then("the layout drawers respond to their controls")
  public void drawers() {
    root().text("Open right", false).click();
    browser.settle();
    check(() -> assertEquals(1, root().all(EXAMPLES + " .dui-right-open").size()));
    el(EXAMPLES + " .dui-right-drawer .mdi-close").click();
    browser.settle();
    check(() -> assertTrue(root().all(EXAMPLES + " .dui-right-open").isEmpty()));
  }

  @Then("the complete form contains sample data and validates missing values")
  public void form() {
    assertTrue(root().all(EXAMPLES + " input").size() > 20);
    assertTrue(
        "The profile address should populate the form",
        root().all(EXAMPLES + " input").stream().anyMatch(e -> "OM - Muscat".equals(e.value())));
    root().text("Submit", false).click();
    check(() -> assertFalse(root().all(".invalid-section").isEmpty()));
    assertTrue(root().all(".dui-dialog").stream().noneMatch(Browser.Element::visible));
  }
}
