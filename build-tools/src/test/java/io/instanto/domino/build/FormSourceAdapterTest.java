package io.instanto.domino.build;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormSourceAdapterTest {
  @Test
  public void cardsCollapseTheirBodyWhileOtherWidgetsHide() {
    String adapted =
        FormSourceAdapter.adapt(
            """
        package sample.client.views.ui;
        class Sample {
          Card card = Card.create("Optional").hide();
          Row row = Row.create().hide();
          void toggle() { card.show(); row.show(); card.hide(); }
        }
        """);
    assertTrue(adapted.contains("Card.create(\"Optional\").collapse()"));
    assertTrue(adapted.contains("card.expand()"));
    assertTrue(adapted.contains("card.collapse()"));
    assertTrue(adapted.contains("Row.create().hide()"));
    assertTrue(adapted.contains("row.show()"));
  }

  @Test
  public void nestedFieldAdaptationsSurviveTheirContainingRows() {
    String adapted =
        FormSourceAdapter.adapt(
            """
        package sample.client.views.ui;
        class Sample {
          Object row = Row.create().addColumn(Column.span4().appendChild(TextBox.create().value("Hello")));
        }
        """);
    assertTrue(adapted.contains("Row.create().appendChild("));
    assertTrue(adapted.contains("TextBox.create().withValue(\"Hello\")"));
    assertFalse(adapted.contains(".addColumn("));
  }

  @Test
  public void alreadyCurrentBankComponentAndModelsAreUnchanged() {
    for (String original :
        new String[] {
          "package sample.client.views.ui; class BanksComponent { Object o = SelectOption.create(bank, bank.getId(), label); }",
          "package sample.shared.model; class Bank { String value; }"
        }) assertEquals(original, FormSourceAdapter.adapt(original));
  }
}
