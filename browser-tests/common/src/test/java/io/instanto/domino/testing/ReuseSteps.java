package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/reuse.feature", runner = DominoRunner.class)
public class ReuseSteps extends BrowserSteps {
  @Given("the independent logger application is open")
  public void openLogger() {
    openApplication("reuse", "");
  }

  @Then("the logger works without widget dependencies")
  public void logger() {
    attribute(root(), "data-reuse", "ready");
    check(() -> assertTrue(browser.console().contains("compat-reuse-success")));
  }

  @Given("the logger is configured at {string}")
  public void configured(String level) {
    openApplication("reuse", level);
  }

  @Then("the logger contract {string} passes")
  public void contract(String name) {
    attribute(root(), "data-logger-" + name, "ready");
  }

  @Then("the SLF4J fluent and formatted messages reach the console")
  public void formatted() {
    check(
        () -> {
          assertTrue(browser.console().contains("compat-fluent-success"));
          assertTrue(browser.console().contains("array [1, 2] and value"));
        });
  }
}
