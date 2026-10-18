package io.instanto.domino.logging;

import org.dominokit.domino.logger.DefaultDominoLoggingServiceProvider;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.spi.SLF4JServiceProvider;

/** TeaVM-only replacement for SLF4J's JVM provider discovery. */
public final class TeaVmLoggerFactory {
  private TeaVmLoggerFactory() {}

  public static Logger getLogger(String name) {
    return Provider.INSTANCE.getLoggerFactory().getLogger(name);
  }

  public static Logger getLogger(Class<?> type) {
    return Provider.INSTANCE.getLoggerFactory().getLogger(type.getName());
  }

  public static ILoggerFactory getILoggerFactory() {
    return Provider.INSTANCE.getLoggerFactory();
  }

  // Called by the unchanged SLF4J 2 MDC and MarkerFactory classes.
  public static SLF4JServiceProvider getProvider() {
    return Provider.INSTANCE;
  }

  private static final class Provider {
    static final SLF4JServiceProvider INSTANCE = initialize();

    private static SLF4JServiceProvider initialize() {
      var provider = new DefaultDominoLoggingServiceProvider();
      provider.initialize();
      return provider;
    }
  }
}
