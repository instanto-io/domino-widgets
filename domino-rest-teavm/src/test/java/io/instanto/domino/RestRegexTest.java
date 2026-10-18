package io.instanto.domino;

import static org.junit.Assert.*;

import org.dominokit.rest.js.JsRegexEngine;
import org.junit.Test;

public class RestRegexTest {
  @Test
  public void matcherIsPositionedBeforeUrlGroupsAreRead() {
    var engine = new JsRegexEngine();
    var match = engine.compile("https://([^/]+)/(.*)").matcher("https://example.com/path");
    assertTrue(match.isMatch());
    assertEquals("example.com", match.group(1));
    assertFalse(engine.compile("https://.*").matcher("relative/path").isMatch());
  }

  @Test
  public void zeroLengthReplacementsPreserveCharactersAndHandleEndOfInput() {
    var engine = new JsRegexEngine();
    assertEquals("_ab_", engine.replaceAll("ab", engine.compile("^|$"), match -> "_"));
    assertEquals("a_b", engine.replaceAll("ab", engine.compile("(?=b)"), match -> "_"));
  }
}
