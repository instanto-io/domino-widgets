// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.TextAreaBox;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class GoodsDescriptionSection implements ImportSection {

  private TextAreaBox goodsDescriptionTextArea;

  private Card card;

  private HTMLDivElement element = div().element();

  public GoodsDescriptionSection() {
    element.appendChild(BlockHeader.create("Goods Description *").element());
    goodsDescriptionTextArea =
        TextAreaBox.create("Goods Description")
            .setAutoValidation(true)
            .setRequired(true)
            .autoSize()
            .setRows(3)
            .appendChild(PrefixAddOn.of(Icons.note()));
    goodsDescriptionTextArea.getInputElement().addEventListener("input", evt -> revalidate());
    card = Card.create().styler(sampleStyle -> sampleStyle.setPaddingTop("20px"));
    element.appendChild(card.appendChild(goodsDescriptionTextArea).element());
  }

  public void revalidate() {
    if (isInvalidatedCard(card) && isValid()) {
      markCardValidation(card, true, false);
    }
  }

  private boolean isValid() {
    return goodsDescriptionTextArea.validate().isValid();
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    letterOfCredit.setDescriptionOfGoods(goodsDescriptionTextArea.getValue());
  }

  @Override
  public boolean validate() {
    boolean valid = isValid();
    markCardValidation(card, valid);
    return valid;
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
