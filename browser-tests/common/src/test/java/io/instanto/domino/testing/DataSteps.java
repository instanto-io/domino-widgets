package io.instanto.domino.testing;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/data.feature", runner = DominoRunner.class)
public class DataSteps extends BrowserSteps {
  @Given("the independent data application is open")
  public void openData() {
    openApplication("data", "");
    attribute(root(), "data-ready", "true");
  }

  @Then("the Jackson record preserves escaped text collections and dates")
  public void jackson() {
    var result = el("#data-result");
    attribute(result, "data-jackson-name", "TeaVM \"record\"");
    attribute(result, "data-jackson-count", "42");
    attribute(result, "data-jackson-labels", "one,two");
    attribute(result, "data-jackson-date", "1792281600000");
  }

  @Then("REST sends parameterized requests and JSON bodies with response interceptors")
  public void rest() {
    var result = el("#data-result");
    el("#data-get").click();
    check(
        () ->
            org.junit.Assert.assertEquals(
                "REST failure: " + result.attr("data-error") + " " + browser.console(),
                "server:42",
                result.attr("data-get")));
    attribute(result, "data-response-intercepted", "true");
    el("#data-post").click();
    attribute(result, "data-post", "TeaVM \"record\":2");
    el("#data-fail").click();
    attribute(result, "data-failed-status", "404");
  }
}
