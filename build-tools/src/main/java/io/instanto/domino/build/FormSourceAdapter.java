package io.instanto.domino.build;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.UnknownType;
import java.util.Map;

/** Updates the pinned form sample's Domino 1 API calls to Domino 2. */
final class FormSourceAdapter {
  static String adapt(String source) {
    if (!source.contains(".client.views.ui") || source.contains("class BanksComponent"))
      return source;
    source =
        source
            .replace(
                "org.dominokit.domino.ui.utils.ElementUtil.numbersOnly",
                "io.instanto.domino.client.FormExampleSupport.numbersOnly")
            .replace("ElementUtil.numbersOnly(", "FormExampleSupport.numbersOnly(")
            .replace(
                "numbersOnly(TextBox.create(NUMBER_OF_COPIES))",
                "FormExampleSupport.positiveWholeNumber(TextBox.create(NUMBER_OF_COPIES))")
            .replace("Select.SelectionHandler<", "java.util.function.Consumer<SelectOption<")
            .replace(
                "CorporateAccount> corporateAccountSelectionHandler",
                "CorporateAccount>> corporateAccountSelectionHandler")
            .replace(".getHeaderDescription()", ".getHeader().getDescriptionElement()")
            .replace(".getHeaderBar()", ".getHeader().getMainHeader()")
            .replace(".bodyStyle()", ".getBody().style()")
            .replace("dui_lead", "dui_font_size_5")
            .replace("valuesContainerCollapsible.show()", "valuesContainerCollapsible.expand()")
            .replace("valuesContainerCollapsible.hide()", "valuesContainerCollapsible.collapse()")
            .replace(
                "otherDocumentListGroupRow.isCollapsed()", "otherDocumentListGroupRow.isHidden()")
            .replace("addButton.isCollapsed()", "addButton.isHidden()")
            .replace("accountSelect.isCollapsed()", "accountSelect.isHidden()")
            .replace(
                "Collapsible.create(valuesContainer).show()",
                "Collapsible.create(valuesContainer).expand()");
    var cu = StaticJavaParser.parse(source);
    cu.addImport("io.instanto.domino.client.FormExampleSupport");
    cu.addImport("org.dominokit.domino.ui.forms.suggest.SelectOption");
    cu.addImport("org.dominokit.domino.ui.utils.PrefixAddOn");
    cu.addImport("org.dominokit.domino.ui.utils.PostfixAddOn");
    cu.addImport("org.dominokit.domino.ui.typography.BlockHeader");
    cu.addImport("org.dominokit.domino.ui.utils.Domino", true, true);
    var cardNames = new java.util.HashSet<String>();
    cu.findAll(com.github.javaparser.ast.body.VariableDeclarator.class).stream()
        .filter(v -> v.getTypeAsString().equals("Card"))
        .forEach(v -> cardNames.add(v.getNameAsString()));
    Map<String, String> names =
        Map.of(
            "value",
            "withValue",
            "addColumn",
            "appendChild",
            "createPrimary",
            "create",
            "createDefault",
            "create");
    // Style.get() used to return the owning widget. styler preserves that fluent return type.
    for (var get : cu.findAll(MethodCallExpr.class)) {
      if (!get.getNameAsString().equals("get") || get.getScope().isEmpty()) continue;
      Expression chain = get.getScope().get().clone(), cursor = chain;
      while (cursor instanceof MethodCallExpr m) {
        if (m.getNameAsString().equals("style")
            && m.getArguments().isEmpty()
            && m.getScope().isPresent()) {
          Expression owner = m.getScope().get().clone();
          m.replace(new NameExpr("sampleStyle"));
          get.replace(
              new MethodCallExpr(owner, "styler")
                  .addArgument(StaticJavaParser.parseExpression("sampleStyle -> " + chain)));
          break;
        }
        if (m.getNameAsString().equals("of")
            && m.getScope().map(Object::toString).orElse("").equals("Style")) {
          Expression owner = m.getArgument(0).clone();
          m.replace(new NameExpr("sampleStyle"));
          get.replace(
              new MethodCallExpr(owner, "styler")
                  .addArgument(StaticJavaParser.parseExpression("sampleStyle -> " + chain)));
          break;
        }
        if (m.getScope().isEmpty()) break;
        cursor = m.getScope().get();
      }
    }
    var calls = new java.util.ArrayList<>(cu.findAll(MethodCallExpr.class));
    java.util.Collections.reverse(calls);
    for (var m : calls) {
      String name = m.getNameAsString();
      if ((name.equals("hide") || name.equals("show")) && m.getScope().isPresent()) {
        String scope = m.getScope().get().toString();
        if (cardNames.contains(scope) || scope.startsWith("Card.create(")) {
          m.setName(name.equals("hide") ? "collapse" : "expand");
        }
      }
      if (name.equals("linkify")) {
        m.setName("addCss");
        m.addArgument(
            StaticJavaParser.parseExpression(
                "org.dominokit.domino.ui.style.DominoCss.dui_transparent"));
      }
      if (name.equals("setContent") && m.getArgument(0).isStringLiteralExpr())
        m.setName("setTextContent");
      if (name.equals("copy")
          && m.getScope().map(Object::toString).orElse("").startsWith("Column.span"))
        m.replace(m.getScope().orElseThrow().clone());
      if (name.equals("getDisplayValue")) m.setName("getValue");
      if (name.equals("show")
          && m.getScope().map(Object::toString).orElse("").startsWith("Collapsible.create"))
        m.setName("expand");
      if (name.equals("dropup"))
        m.replace(
            new MethodCallExpr(m.getScope().orElseThrow().clone(), "withOptionsMenu")
                .addArgument(
                    StaticJavaParser.parseExpression(
                        "(select, menu) -> menu.setDropDirection(org.dominokit.domino.ui.menu.direction.DropDirection.TOP_LEFT)")));
      if (name.equals("addValidator")) {
        if (m.getArgument(0).isLambdaExpr()) {
          var l = m.getArgument(0).asLambdaExpr();
          if (l.getParameters().isEmpty())
            l.addParameter(new Parameter(new UnknownType(), "field"));
        } else if (m.getArgument(0).isMethodReferenceExpr())
          m.setArgument(
              0,
              StaticJavaParser.parseExpression(
                  "field -> " + m.getArgument(0).toString().replace("::", ".") + "()"));
      }
      if (name.equals("create")
          && m.getScope().map(Object::toString).orElse("").equals("Popover")
          && m.getArguments().size() == 3) {
        m.replace(
            StaticJavaParser.parseExpression(
                "Popover.create("
                    + m.getArgument(0)
                    + ").apply(p -> {p.getHeaderElement().setTextContent("
                    + m.getArgument(1)
                    + ");p.getBody().appendChild("
                    + m.getArgument(2)
                    + ");})"));
      }
      if (names.containsKey(name)) m.setName(names.get(name));
      if (name.equals("withGap")) m.replace(m.getScope().orElseThrow().clone());
      if (name.equals("addLeftAddOn") || name.equals("addRightAddOn")) {
        m.setName("appendChild");
        m.setArgument(
            0,
            new MethodCallExpr(
                    new NameExpr(name.equals("addLeftAddOn") ? "PrefixAddOn" : "PostfixAddOn"),
                    "of")
                .addArgument(m.getArgument(0).clone()));
      }
      if (name.equals("css")
          && m.getArguments().stream().anyMatch(a -> a.toString().startsWith("dui_")))
        m.setName("addCss");
      if (name.equals("addSelectionHandler")) {
        m.replace(
            new MethodCallExpr(new NameExpr("FormExampleSupport"), "onSelect")
                .addArgument(m.getScope().orElseThrow().clone())
                .addArgument(m.getArgument(0).clone()));
      }
      if (name.equals("addChangeHandler")) {
        m.setName("addChangeListener");
        var lambda = m.getArgument(0).asLambdaExpr();
        lambda.getParameters().addFirst(new Parameter(new UnknownType(), "oldValue"));
        lambda.setEnclosingParameters(true);
      }
      if (name.equals("create")
          && m.getScope().map(Object::toString).orElse("").equals("SelectOption")) {
        if (m.getArguments().size() == 2)
          m.getArguments()
              .addFirst(
                  new MethodCallExpr(new NameExpr("String"), "valueOf")
                      .addArgument(m.getArgument(1).clone()));
        else if (m.getArguments().size() == 3) {
          var value = m.getArgument(0).clone();
          m.setArgument(0, m.getArgument(1).clone());
          m.setArgument(1, value);
        }
      }
      if (name.equals("setBodyPaddingTop")) {
        m.replace(
            new MethodCallExpr(m.getScope().orElseThrow().clone(), "withBody")
                .addArgument(
                    StaticJavaParser.parseExpression(
                        "(card, body) -> body.setPaddingTop(" + m.getArgument(0) + ")")));
      }
      if (name.equals("addDateSelectionHandler")) {
        var picker = m.getScope().orElseThrow().asMethodCallExpr();
        m.setScope(picker.getScope().orElseThrow().clone());
        m.setName("addChangeListener");
      }
      if (name.equals("add") && m.getScope().map(Object::toString).orElse("").startsWith("div()"))
        m.setName("appendChild");
      if (name.equals("ifPresent")
          && m.getScope().map(Object::toString).orElse("").contains("getSelectedRadio()")) {
        m.setScope(
            new MethodCallExpr(StaticJavaParser.parseExpression("java.util.Optional"), "ofNullable")
                .addArgument(m.getScope().orElseThrow().clone()));
      }
    }
    var type = cu.getType(0).asClassOrInterfaceDeclaration();
    if (type.getNameAsString().equals("AddLCImportComponent")) {
      type.getConstructors()
          .get(0)
          .getBody()
          .addStatement(0, StaticJavaParser.parseStatement("init(this);"));
      type.getMethodsByName("element")
          .get(0)
          .setBody(StaticJavaParser.parseBlock("{return element.element();}"));
    }
    if (type.getNameAsString().equals("CorporateAccountsSelect")) {
      type.getImplementedTypes().removeIf(t -> t.getNameAsString().equals("IsCollapsible"));
      type.getMethods()
          .forEach(
              m -> {
                if (!m.getNameAsString().equals("element"))
                  m.getAnnotationByName("Override").ifPresent(a -> a.remove());
              });
    }
    if (type.getNameAsString().equals("AccountDetailsPopupPosition")) {
      cu.getImports().removeIf(i -> i.getNameAsString().endsWith("PopupPosition"));
      cu.addImport("org.dominokit.domino.ui.menu.direction.DropDirection");
      cu.addImport("org.dominokit.domino.ui.menu.direction.DropDirectionContext");
      type.getImplementedTypes().clear();
      type.addImplementedType("DropDirection");
      var position = type.getMethodsByName("position").get(0);
      position.setType("DropDirection");
      position.setParameters(
          new NodeList<>(
              new Parameter(StaticJavaParser.parseType("DropDirectionContext"), "context")));
      position
          .getBody()
          .orElseThrow()
          .addStatement(
              0,
              StaticJavaParser.parseStatement(
                  "HTMLElement tooltip = (HTMLElement) context.getSource();"));
      position.getBody().orElseThrow().addStatement("return this;");
      type.getMethodsByName("getDirectionClass").get(0).remove();
    }
    return cu.toString()
        .replace(
            ".position(new AccountDetailsPopupPosition",
            ".setPosition(new AccountDetailsPopupPosition")
        .replace("home_currency_usd()", "currency_usd()")
        .replace("file_document_box()", "file_document()")
        .replace("goodsDescriptionTextAreaBox", "goodsDescriptionTextArea");
  }
}
