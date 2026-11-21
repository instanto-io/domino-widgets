package io.instanto.domino;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.dominokit.rest.DominoRestConfig;
import org.junit.Test;

public class RestDefaultsTest {
  @Test
  public void defaultsAreSharedAcrossConfigInstances() {
    var writer = new DominoRestConfig();
    var reader = DominoRestConfig.getInstance();
    var previousFormatter = reader.getDateParamFormatter();
    try {
      assertSame(writer, writer.setDateParamFormatter((date, pattern) -> "shared:" + pattern));
      assertSame(writer.getDateParamFormatter(), reader.getDateParamFormatter());
      assertEquals("shared:pattern", reader.getDateParamFormatter().format(null, "pattern"));
    } finally {
      writer.setDateParamFormatter(previousFormatter);
    }
  }
}
