package io.instanto.domino.build;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FormDataGeneratorTest {
  @Test
  public void readsThePinnedLongFormSamplesWithoutRegexRecursion() throws Exception {
    Path root = Path.of("..");
    String source =
        Files.readString(
            root.resolve(
                "upstream/showcase/formsamples/src/main/java/org/dominokit/domino/formsamples/client/views/ui/FormSamplesViewImpl.java"));
    String generated = FormDataGenerator.generate(root, source);
    assertTrue(generated.contains("public static CorporateProfile profile()"));
    assertTrue(generated.contains("Andorra"));
    assertTrue(generated.contains("British Pound Sterling"));
  }
}
