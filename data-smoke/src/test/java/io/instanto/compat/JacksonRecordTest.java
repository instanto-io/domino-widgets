package io.instanto.compat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.List;
import org.junit.Test;

public class JacksonRecordTest {
  @Test
  public void generatedRecordMapperPreservesEscapingCollectionsAndUtcDates() {
    Payload value = DataSmoke.roundtrip();
    assertEquals("TeaVM \"record\"", value.name());
    assertEquals(42, value.count());
    assertEquals(List.of("one", "two"), value.labels());
    assertEquals(Instant.parse("2026-10-18T00:00:00Z").toEpochMilli(), value.date().getTime());
  }
}
