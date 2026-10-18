package io.instanto.domino.build;

import static io.instanto.domino.build.BuildFiles.*;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.maven.plugins.annotations.*;

/** Verify pinned upstream inputs and prepare Java sources and matching assets. */
@Mojo(name = "intake", defaultPhase = LifecyclePhase.INITIALIZE, threadSafe = true)
public final class IntakeMojo extends BuildMojo {
  @Override
  protected void run() throws Exception {
    prepare(root());
    getLog().info("Verified upstream source and showcase checksums; prepared target/intake");
  }

  static void prepare(Path root) throws Exception {
    JsonObject lock = object(root.resolve("upstream/source-lock.json"));
    Path archive = within(root.resolve("upstream"), lock.get("archive").getAsString());
    verifyHash(archive, lock.get("sha256").getAsString());
    JsonObject showcase = object(root.resolve("upstream/showcase-lock.json"));
    for (String key : List.of("sources", "supporting", "assets")) {
      if (showcase.has(key))
        for (JsonElement item : showcase.getAsJsonArray(key))
          verifySample(root, item.getAsJsonObject());
    }
    verifySample(root, showcase.getAsJsonObject("asset"));
    Path out = root.resolve("target/intake");
    deleteTree(out);
    List<Map<String, Object>> inventory = new ArrayList<>();
    try (var tar = new TarArchiveInputStream(new GZIPInputStream(Files.newInputStream(archive)))) {
      for (var entry = tar.getNextEntry(); entry != null; entry = tar.getNextEntry()) {
        if (!entry.isFile()) continue;
        byte[] bytes = tar.readAllBytes();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("path", entry.getName());
        row.put("size", bytes.length);
        row.put("sha256", hash(bytes));
        inventory.add(row);
        String name = entry.getName();
        Path destination = null;
        for (String prefix :
            List.of("domino-ui/src/main/java/", "domino-ui-shared/src/main/java/")) {
          if (name.startsWith(prefix))
            destination = within(out.resolve("java"), name.substring(prefix.length()));
        }
        String assets = "domino-ui/src/main/resources/org/dominokit/domino/ui/public/";
        if (name.startsWith(assets))
          destination =
              within(
                  out.resolve("assets/META-INF/resources/domino-widgets"),
                  name.substring(assets.length()));
        if (destination != null) {
          require(!Files.exists(destination), "Duplicate source input: " + destination);
          Files.createDirectories(destination.getParent());
          Files.write(destination, bytes);
        }
      }
    }
    Path css = out.resolve("assets/META-INF/resources/domino-widgets/css/domino-ui");
    List<String> styles = new ArrayList<>();
    for (Path path : files(css.resolve("dui-components"))) {
      String name = path.getFileName().toString();
      if (name.endsWith(".css") && !name.contains(".min."))
        styles.add(Files.readString(path).replace("\r\n", "\n").replace('\r', '\n'));
    }
    require(!styles.isEmpty(), "No upstream component stylesheets");
    write(css.resolve("domino-ui.css"), String.join("\n", styles));
    json(out.resolve("inventory.json"), inventory);
    prepareLibraries(root, out);
  }

  private static void prepareLibraries(Path root, Path out) throws Exception {
    for (var item :
        JsonParser.parseString(Files.readString(root.resolve("upstream/libraries-lock.json")))
            .getAsJsonArray()) {
      var library = item.getAsJsonObject();
      Path archive = within(root.resolve("upstream"), library.get("archive").getAsString());
      verifyHash(archive, library.get("sha256").getAsString());
      Path destination = within(out, library.get("name").getAsString());
      Map<String, String> prefixes = new LinkedHashMap<>();
      for (var prefix : library.getAsJsonArray("sourcePrefixes"))
        prefixes.put(prefix.getAsString(), "java");
      prefixes.put(library.get("testPrefix").getAsString(), "tests");
      prefixes.put(library.get("testResourcesPrefix").getAsString(), "test-resources");
      try (var tar =
          new TarArchiveInputStream(new GZIPInputStream(Files.newInputStream(archive)))) {
        for (var entry = tar.getNextEntry(); entry != null; entry = tar.getNextEntry()) {
          if (!entry.isFile()) continue;
          String name = entry.getName();
          Path file = null;
          for (var prefix : prefixes.entrySet()) {
            if (name.startsWith(prefix.getKey()))
              file =
                  within(
                      destination.resolve(prefix.getValue()),
                      name.substring(prefix.getKey().length()));
          }
          if (name.equals("LICENSE") || name.equals("NOTICE"))
            file = destination.resolve("licenses").resolve(name);
          if (file != null) {
            require(!Files.exists(file), "Duplicate library input: " + file);
            Files.createDirectories(file.getParent());
            Files.write(file, tar.readAllBytes());
          }
        }
      }
    }
  }

  private static void verifySample(Path root, JsonObject sample) throws Exception {
    verifyHash(
        within(root.resolve("upstream/showcase"), sample.get("path").getAsString()),
        sample.get("sha256").getAsString());
  }
}
