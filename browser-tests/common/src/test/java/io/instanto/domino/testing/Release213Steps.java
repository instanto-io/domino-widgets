package io.instanto.domino.testing;

import static org.junit.Assert.*;

import io.instanto.cucumber.tea.*;

@CucumberSuite(value = "features/release-213.feature", runner = DominoRunner.class)
public class Release213Steps extends BrowserSteps {
  @Then("the release widgets restore themes and generate dynamic CSS")
  public void themesAndCss() {
    attribute(el("#release-213"), "data-theme-restored", "true");
    contains(el("#release-value"), "213");
    check(() -> assertEquals("13px", el("#release-value").css("padding-top")));
  }

  @Then("date and time fields accept direct text entry")
  public void typing() {
    var date = el("#release-date input");
    date.fill("2026-10-18");
    date.press("Enter");
    check(() -> assertEquals("2026-10-18", date.value()));
    var time = el("#release-time input");
    time.fill("14:35");
    time.press("Enter");
    check(() -> assertEquals("14:35", time.value()));
  }
}
