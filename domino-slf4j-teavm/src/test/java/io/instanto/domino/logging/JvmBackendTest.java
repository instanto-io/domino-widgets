package io.instanto.domino.logging;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.simple.SimpleLoggerFactory;

/** The TeaVM bridge must not intercept provider discovery on the JVM. */
public class JvmBackendTest {
  @Test
  public void standardSlf4jStillSelectsTheJvmBackend() {
    assertTrue(LoggerFactory.getILoggerFactory() instanceof SimpleLoggerFactory);
    LoggerFactory.getLogger(JvmBackendTest.class).atInfo().log("JVM backend remains independent");
  }
}
