package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/ecosystem.feature", runner = DominoRunner.class)
public class EcosystemSteps extends BrowserSteps {
  @Given("the independent ecosystem application is open")
  public void openEcosystem() {
    openApplication("ecosystem", "");
    attribute(root(), "data-ecosystem", "ready");
  }

  @Then("Markdown extensions render through both browser renderers")
  public void markdownRenderers() {
    for (String id : new String[] {"markdown-dom", "markdown-html"}) {
      var output = el("#" + id);
      contains(output.first("h1"), "TeaVM Markdown");
      contains(output.first("strong"), "world");
      contains(output.first("del"), "old");
      contains(output.first("table td"), "Markdown");
      check(() -> assertEquals(2, output.all("input[type=checkbox]").size()));
      attribute(output.first("input[type=checkbox]"), "checked", "");
      attribute(output.first("a"), "href", "https://example.com");
    }
  }

  @Then("Markdown renders plain text and survives a Markdown round trip")
  public void roundtrip() {
    contains(el("#markdown-text"), "Hello world");
    assertFalse(el("#markdown-text").text().contains("**"));
    contains(el("#markdown-roundtrip strong"), "world");
    contains(el("#markdown-roundtrip table td"), "Markdown");
    contains(el("#markdown-roundtrip del"), "old");
  }

  @Then("raw HTML and unsafe links follow the safe rendering configuration")
  public void safeRendering() {
    for (String id : new String[] {"markdown-safe", "markdown-safe-dom"}) {
      var output = el("#" + id);
      assertTrue(output.all("script").isEmpty());
      assertFalse(output.first("a").attr("href").startsWith("javascript:"));
    }
    contains(el("#markdown-safe"), "<script>");
  }

  @Then("History supports parameters replacement back forward and cancellation")
  public void navigation() {
    var result = el("#history-result");
    attribute(result, "data-count", "0");
    el("#history-push").click();
    attribute(result, "data-count", "1");
    attribute(result, "data-token", "ecosystem/item/42?mode=edit#detail");
    attribute(result, "data-title", "Item 42");
    attribute(result, "data-state", "payload-42");
    assertTrue(browser.url().endsWith("/ecosystem/item/42?mode=edit#detail"));
    el("#history-replace").click();
    attribute(result, "data-count", "2");
    attribute(result, "data-token", "ecosystem/replaced");
    attribute(result, "data-state", "replacement");
    el("#history-block").click();
    attribute(result, "data-blocked-token", "ecosystem/replaced");
    attribute(result, "data-count", "2");
    el("#history-back").click();
    attribute(result, "data-count", "3");
    attribute(result, "data-token", "ecosystem/start");
    assertTrue(browser.url().endsWith("/ecosystem/start"));
    attribute(result, "data-state", "initial");
    el("#history-forward").click();
    attribute(result, "data-count", "4");
    attribute(result, "data-token", "ecosystem/replaced");
    attribute(result, "data-title", "Replaced");
    attribute(result, "data-state", "replacement");
    el("#history-inspect").click();
    attribute(result, "data-removed-count", "0");
    attribute(result, "data-once-count", "1");
    attribute(result, "data-path", "ecosystem/replaced");
    assertTrue(browser.url().endsWith("/ecosystem/replaced"));
  }
}
