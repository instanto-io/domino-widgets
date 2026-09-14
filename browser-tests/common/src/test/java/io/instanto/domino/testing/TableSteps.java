package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;
import java.util.List;

@CucumberSuite(value = "features/tables.feature", runner = DominoRunner.class)
public class TableSteps extends BrowserSteps {
  private Browser.Element table() {
    return el("#gallery-examples table.dui-datatable");
  }

  private List<Browser.Element> rows(Browser.Element table) {
    return table.all("tbody > tr.dui-datatable-row").stream()
        .filter(row -> !row.attr("class").contains("dui-datatable-drop-row"))
        .toList();
  }

  private Browser.Element row() {
    return rows(table()).get(0);
  }

  private long visibleRows(Browser.Element table) {
    return rows(table).stream().filter(Browser.Element::visible).count();
  }

  @When("I change the first contact name to {string}")
  public void editName(String name) {
    row().first(".mdi-pencil").click();
    row().first("td:nth-child(4) input").fill(name);
    row().first(".mdi-content-save").click();
  }

  @Then("the saved row contains {string}")
  public void savedName(String name) {
    contains(row(), name);
    check(() -> assertTrue(row().all("input").isEmpty()));
  }

  @When("I try to save a balance above the limit")
  public void invalidBalance() {
    row().first(".mdi-pencil").click();
    row().first("td:nth-child(7) input").fill("5000");
    row().first(".mdi-content-save").click();
  }

  @Then("the row remains editable until its balance is corrected")
  public void correctBalance() {
    check(() -> assertFalse(row().all(".dui-field-invalid").isEmpty()));
    row().first("td:nth-child(7) input").fill("2500");
    row().first(".mdi-content-save").click();
    check(() -> assertTrue(row().all("input").isEmpty()));
    row().first(".mdi-pencil").click();
    assertEquals("2,500", row().first("td:nth-child(7) input").value());
    row().first(".mdi-content-save").click();
  }

  @Then("a contact group can be collapsed and restored")
  public void group() {
    long before = visibleRows(table());
    table().first("tbody .mdi-minus-box").click();
    check(() -> assertTrue(visibleRows(table()) < before));
    table().first("tbody .mdi-plus-box").click();
    check(() -> assertEquals(before, visibleRows(table())));
  }

  @Then("the contact details can change the row status and close")
  public void details() {
    assertFalse(row().all(".mdi-close-circle").isEmpty());
    row().first(".mdi-fullscreen").click();
    Browser.Element details = table().first(".dui-datatable-details-tr");
    contains(details, "Alice Example");
    details.first(".dui-switch-track").click();
    check(() -> assertFalse(row().all(".mdi-check-circle").isEmpty()));
    row().first(".mdi-fullscreen-exit").click();
    check(
        () ->
            assertTrue(
                table().all(".dui-datatable-details-tr").stream()
                    .noneMatch(Browser.Element::visible)));
  }

  @Then("the context menu can activate the first contact")
  public void contextMenu() {
    assertFalse(row().all(".mdi-close-circle").isEmpty());
    row().contextClick();
    root().text("Activate", false).click();
    check(() -> assertFalse(row().all(".mdi-check-circle").isEmpty()));
  }

  @Then("the summary shows the contact total and average")
  public void summary() {
    Browser.Element footer = table().first("tfoot");
    contains(footer, "Sum");
    contains(footer, "25500");
    contains(footer, "Average");
    contains(footer, "1700");
  }

  @Then("scrolling loads more contacts")
  public void scrolling() {
    int before = rows(table()).size();
    assertTrue(before > 0 && before < 90);
    table().scrollToEnd();
    check(() -> assertTrue("Scroll must append records", rows(table()).size() > before));
  }

  @Then("expanding a tree row reveals children and collapsing hides them")
  public void tree() {
    long before = visibleRows(table());
    Browser.Element toggle = row().first("[order='10']");
    toggle.click();
    check(() -> assertTrue("Children must become visible", visibleRows(table()) > before));
    toggle.click();
    check(() -> assertEquals(before, visibleRows(table())));
  }

  @Then("the tree controls replace the root records")
  public void treeControls() {
    Browser.Element card = el("#gallery-examples").all(".dui-card").get(2);
    card.first("input").fill("3");
    card.text("Update", false).click();
    Browser.Element tree = card.first("table.dui-datatable");
    check(() -> assertEquals(3, visibleRows(tree)));
  }

  @Then("the combined table selects active contacts")
  public void combined() {
    el("#gallery-examples .dui-datatable-nav-bar .mdi-check-circle").click();
    check(
        () ->
            assertTrue(
                rows(table()).stream()
                    .anyMatch(r -> r.attr("class").contains("dui-datatable-row-selected"))));
    for (Browser.Element r : rows(table())) {
      boolean active = !r.all(".mdi-check-circle").isEmpty();
      assertEquals(active, r.attr("class").contains("dui-datatable-row-selected"));
    }
    contains(el("#gallery-examples"), "TOTAL COUNT");
    contains(el("#gallery-examples"), "80");
  }

  @Then("dragging a contact changes the row order")
  public void reorder() {
    List<Browser.Element> records = rows(table());
    String first = records.get(0).text();
    records.get(0).first(".dui-row-dnd-grab").dragTo(records.get(2).first(".dui-row-dnd-grab"));
    check(() -> assertNotEquals(first, row().text()));
    assertEquals(15, rows(table()).size());
    assertTrue(rows(table()).stream().anyMatch(r -> r.text().equals(first)));
  }

  @Then("dragging a contact transfers it between tables")
  public void transfer() {
    List<Browser.Element> tables = el("#gallery-examples").all("table.dui-datatable");
    Browser.Element source = tables.get(1), destination = tables.get(2);
    assertEquals(10, rows(source).size());
    assertEquals(10, rows(destination).size());
    rows(source)
        .get(9)
        .first(".dui-row-dnd-grab")
        .dragTo(rows(destination).get(0).first(".dui-row-dnd-grab"));
    check(() -> assertEquals(9, rows(source).size()));
    check(() -> assertEquals(11, rows(destination).size()));
  }
}
