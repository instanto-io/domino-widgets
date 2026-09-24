package io.instanto.domino.testing;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/** Runs the same generated browser scenarios on the JVM through Playwright WebKit. */
public final class DominoRunner extends BlockJUnit4ClassRunner {
  public DominoRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
    FixtureServer.start();
  }

  @Override
  public void run(RunNotifier notifier) {
    var progress =
        new RunListener() {
          @Override
          public void testStarted(Description description) {
            System.out.println("Cucumber: " + description.getMethodName());
          }
        };
    notifier.addListener(progress);
    try {
      super.run(notifier);
    } finally {
      notifier.removeListener(progress);
    }
  }
}
