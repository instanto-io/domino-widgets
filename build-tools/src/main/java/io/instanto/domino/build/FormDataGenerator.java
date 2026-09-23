package io.instanto.domino.build;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/** Turns the original form's fixed JSON examples into ordinary Java sample objects. */
final class FormDataGenerator {
  private final Path models;
  private final StringBuilder methods = new StringBuilder();
  private int sequence;

  private FormDataGenerator(Path root) {
    models =
        root.resolve(
            "upstream/showcase/formsamples/src/main/java/org/dominokit/domino/formsamples/shared/model");
  }

  static String generate(Path root, String source) throws Exception {
    FormDataGenerator generator = new FormDataGenerator(root);
    Map<String, JsonElement> constants = new HashMap<>();
    Matcher fields =
        Pattern.compile("(?m)^[ \\t]*private static final String (\\w+)[ \\t]*=[ \\t]*")
            .matcher(source);
    while (fields.find()) {
      String json = stringLiterals(source, fields.end(), declarationEnd(source, fields.end()));
      constants.put(
          fields.group(1),
          JsonParser.parseString(json.replace("countryIsoCode", "countryISOCode")));
    }
    StringBuilder entry = new StringBuilder();
    for (String[] row :
        new String[][] {
          {"profile", "CorporateProfile", "PROFILE_JSON", ""},
          {"countries", "List<Country>", "COUNTRIES_JSON", "countries"},
          {"beneficiaries", "List<Beneficiary>", "BENEFICIARIES_JSON", ""},
          {"banks", "List<Bank>", "BANKS_JSON", ""},
          {"currencies", "List<CurrencyData>", "CURRENCIES_JSON", "currencies"}
        }) {
      JsonElement data = Objects.requireNonNull(constants.get(row[2]), row[2]);
      if (!row[3].isEmpty()) data = data.getAsJsonObject().get(row[3]);
      entry
          .append("public static ")
          .append(row[1])
          .append(' ')
          .append(row[0])
          .append("(){return ")
          .append(generator.value(row[1], data))
          .append(";}\n");
    }
    return "// Sample objects generated from the pinned original form JSON; not a JSON runtime.\n"
        + "package io.instanto.domino.client;\nimport java.util.*;\n"
        + "import org.dominokit.domino.formsamples.shared.model.*;\nimport org.dominokit.domino.formsamples.shared.model.Country;\n"
        + "public final class FormSampleData {\n"
        + entry
        + generator.methods
        + "}\n";
  }

  private static int declarationEnd(String source, int start) {
    boolean quoted = false;
    boolean escaped = false;
    for (int i = start; i < source.length(); i++) {
      char c = source.charAt(i);
      if (quoted) {
        if (escaped) escaped = false;
        else if (c == '\\') escaped = true;
        else if (c == '"') quoted = false;
      } else if (c == '"') quoted = true;
      else if (c == ';') return i;
    }
    throw new IllegalArgumentException("Unterminated sample JSON declaration");
  }

  private static String stringLiterals(String source, int start, int end) {
    StringBuilder joined = new StringBuilder();
    Gson gson = new Gson();
    for (int i = start; i < end; i++) {
      if (source.charAt(i) != '"') continue;
      int opening = i++;
      boolean escaped = false;
      while (i < end) {
        char c = source.charAt(i);
        if (escaped) escaped = false;
        else if (c == '\\') escaped = true;
        else if (c == '"') break;
        i++;
      }
      if (i == end) throw new IllegalArgumentException("Unterminated sample JSON string");
      joined.append(gson.fromJson(source.substring(opening, i + 1), String.class));
    }
    return joined.toString();
  }

  private String value(String type, JsonElement data) throws Exception {
    if (data == null || data.isJsonNull()) return "null";
    if (type.startsWith("List<")) {
      String itemType = type.substring(5, type.length() - 1);
      List<String> entries = new ArrayList<>();
      for (JsonElement entry : data.getAsJsonArray()) entries.add(value(itemType, entry));
      return "new ArrayList<>(Arrays.asList(" + String.join(",", entries) + "))";
    }
    if (data.isJsonPrimitive()) {
      return switch (type) {
        case "String" -> new Gson().toJson(data.getAsString());
        case "int", "Integer", "double", "Double", "boolean", "Boolean" -> data.toString();
        case "long", "Long" -> data.getAsString() + "L";
        case "BigDecimal" ->
            "new java.math.BigDecimal(" + new Gson().toJson(data.getAsString()) + ")";
        default -> type + ".valueOf(" + new Gson().toJson(data.getAsString()) + ")";
      };
    }
    String model = Files.readString(models.resolve(type + ".java"));
    String name = "sample" + sequence++;
    StringBuilder body =
        new StringBuilder(
            "private static "
                + type
                + " "
                + name
                + "(){"
                + type
                + " value = new "
                + type
                + "();\n");
    for (var property : data.getAsJsonObject().entrySet()) {
      if (property.getValue().isJsonNull()) continue;
      String setter =
          "set"
              + Character.toUpperCase(property.getKey().charAt(0))
              + property.getKey().substring(1);
      Matcher parameter =
          Pattern.compile("\\b" + Pattern.quote(setter) + "\\(\\s*([\\w<>]+)\\s+\\w+\\s*\\)")
              .matcher(model);
      if (!parameter.find())
        throw new IllegalArgumentException("Missing sample setter " + type + "." + setter);
      body.append("value.")
          .append(setter)
          .append('(')
          .append(value(parameter.group(1), property.getValue()))
          .append(");\n");
    }
    methods.append(body).append("return value;}\n");
    return name + "()";
  }
}
