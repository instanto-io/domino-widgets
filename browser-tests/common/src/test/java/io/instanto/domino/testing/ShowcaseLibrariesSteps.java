package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/showcase-libraries.feature", runner = DominoRunner.class)
public class ShowcaseLibrariesSteps extends BrowserSteps {
  @Given("the showcase overview is open")
  public void overview() {
    openApplication("teavm", "");
    check(() -> assertFalse(root().all("#showcase-home").isEmpty()));
  }

  @Then("the library playgrounds can be discovered from the overview and search")
  public void discover() {
    contains(el("#showcase-home"), "New: Markdown & logging");
    attribute(el("#showcase-home a[href='?page=markdown']"), "href", "?page=markdown");
    attribute(el("#showcase-home a[href='?page=logging']"), "href", "?page=logging");
    el(".showcase-search").fill("markdown");
    visible(el("#showcase-sidebar a[href='?page=markdown']"), true);
    visible(el("#showcase-sidebar a[href='?page=logging']"), false);
    el(".showcase-search").fill("MDC");
    visible(el("#showcase-sidebar a[href='?page=logging']"), true);
    visible(el("#showcase-sidebar a[href='?page=markdown']"), false);
  }

  @Then("the Markdown playground renders extensions and all output formats")
  public void markdownFormats() {
    contains(el("#markdown-preview table"), "2.1.3");
    contains(el("#markdown-preview del"), "old ideas");
    check(() -> assertEquals(3, el("#markdown-preview").all("input[type=checkbox]").size()));
    attribute(el("#markdown-preview a"), "href", "https://dominokit.com");
    el("#markdown-source").fill("# Live update\n\nHello **world**.");
    contains(el("#markdown-preview h1"), "Live update");
    contains(el("#markdown-preview strong"), "world");
    el("#markdown-mode-html").click();
    contains(el("#markdown-output"), "<strong>world</strong>");
    attribute(el("#markdown-mode-html"), "aria-pressed", "true");
    visible(el("#markdown-preview"), false);
    el("#markdown-mode-text").click();
    contains(el("#markdown-output"), "Hello world.");
    assertFalse(el("#markdown-output").text().contains("**"));
    el("#markdown-mode-markdown").click();
    contains(el("#markdown-output"), "**world**");
    el("#markdown-mode-dom").click();
    contains(el("#markdown-preview strong"), "world");
  }

  @Then("Markdown samples replace the source and refresh the selected output")
  public void markdownSamples() {
    el("#markdown-document").click();
    contains(el("#markdown-preview h1"), "Notes from the workshop");
    contains(el("#markdown-preview pre"), "parser.parse(source)");
    el("#markdown-mode-html").click();
    el("#markdown-starter").click();
    contains(el("#markdown-output"), "Hello, Markdown");
    assertTrue(el("#markdown-source").value().contains("Hello, Markdown"));
    el("#markdown-extensions").click();
    contains(el("#markdown-output"), "<table>");
  }

  @Then("the editable Markdown preview does not execute raw HTML or unsafe links")
  public void markdownSafety() {
    el("#markdown-source")
        .fill(
            "<script>throw new Error('must not run')</script>\n\n[unsafe](javascript:alert(1))\n\n**Still rendered**");
    contains(el("#markdown-preview strong"), "Still rendered");
    assertTrue(el("#markdown-preview").all("script").isEmpty());
    assertFalse(el("#markdown-preview a").attr("href").startsWith("javascript:"));
    el("#markdown-mode-html").click();
    contains(el("#markdown-output"), "&lt;script&gt;");
    assertTrue(el("#markdown-output").all("script").isEmpty());
  }

  @Then("the logging playground routes events and filters disabled levels")
  public void loggingRoutes() {
    el("#logging-clear").click();
    el("#logging-message").fill("<strong>Order saved</strong>");
    el("#logging-request").fill("request-123");
    el("#logging-route-audit").click();
    el("#logging-level-warn").click();
    el("#logging-send").click();
    var row = el("#logging-events article");
    attribute(row, "data-route", "AUDIT");
    attribute(row, "data-level", "WARN");
    attribute(row, "data-request", "request-123");
    contains(row, "<strong>Order saved</strong>");
    assertTrue(row.all("strong").isEmpty());
    check(() -> assertTrue(browser.console().contains("Order saved")));
    attribute(el("#logging-example"), "data-mdc-cleared", "true");
    el("#logging-level-debug").click();
    el("#logging-send").click();
    contains(el("#logging-status"), "DEBUG is below INFO");
    assertEquals(1, el("#logging-events").all("article").size());
    el("#logging-clear").click();
    contains(el("#logging-count"), "No events yet");
    assertTrue(el("#logging-events").all("article").isEmpty());
  }

  @Then("the logging playground demonstrates nested MDC restoration")
  public void nestedMdc() {
    el("#logging-clear").click();
    el("#logging-request").fill("parent-42");
    el("#logging-nested").click();
    check(() -> assertEquals(3, el("#logging-events").all("article").size()));
    var rows = el("#logging-events").all("article");
    attribute(rows.get(0), "data-request", "parent-42");
    attribute(rows.get(1), "data-request", "parent-42/child");
    attribute(rows.get(2), "data-request", "parent-42");
    contains(rows.get(2), "Outer request ID restored");
    attribute(el("#logging-example"), "data-mdc-cleared", "true");
  }
}
