package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.CucumberSuite;
import io.instanto.cucumber.tea.Then;

@CucumberSuite(value = "features/visual-references.feature", runner = DominoRunner.class)
public class VisualReferenceSteps extends BrowserSteps {
  @Then("the colour gallery shows material and semantic colour scales")
  public void colours() {
    contains(el("#gallery-examples"), "dui_bg_red");
    contains(el("#gallery-examples"), "dui_bg_primary");
    // 2.1.3 resolves the legacy dui_border utility to a dynamic width class.
    var swatches = el("#gallery-examples").all(".dui-card .dui-border-solid");
    assertTrue("Expected the complete colour scale", swatches.size() >= 200);
    assertEquals("1px", swatches.get(0).css("border-top-width"));
  }

  @Then("I can search and select an icon")
  public void icons() {
    var browser = el(".showcase-icon-browser");
    browser.first("input[type=search]").fill("account");
    check(() -> assertFalse(browser.all(".showcase-icon-item").isEmpty()));
    browser.first(".showcase-icon-item").click();
    check(() -> assertTrue(browser.first("input[readonly]").value().startsWith("Icons.")));
  }

  @Then("I can switch the showcase accent")
  public void theme() {
    el("#gallery-examples .dui-bg-blue").click();
    check(() -> assertTrue(root().attr("class").contains("dui-accent-blue")));
    el("#gallery-examples .dui-bg-red").click();
    check(() -> assertTrue(root().attr("class").contains("dui-accent-red")));
  }
}
