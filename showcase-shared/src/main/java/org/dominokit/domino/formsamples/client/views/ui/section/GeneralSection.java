// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.formsamples.client.views.ui.Constants.DATE_PATTERN;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import java.util.Date;
import org.dominokit.domino.formsamples.shared.model.CorporateProfile;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.DateBox;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class GeneralSection implements ImportSection {

  private DateBox creationDateBox;

  private TextBox placeTextBox;

  private HTMLDivElement element = div().element();

  public GeneralSection(CorporateProfile corporateProfile) {
    element.appendChild(BlockHeader.create("General").element());
    creationDateBox = DateBox.create();
    placeTextBox = TextBox.create("Place").withValue("Amman");
    element.appendChild(
        Card.create()
            .styler(sampleStyle -> sampleStyle.setPaddingTop("20px"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6()
                            .styler(sampleStyle -> sampleStyle.setMarginBottom("0px"))
                            .appendChild(
                                creationDateBox
                                    .setPattern(DATE_PATTERN)
                                    .setHelperText(DATE_PATTERN)
                                    .setLabel("Date")
                                    .appendChild(PrefixAddOn.of(Icons.calendar_range()))
                                    .setReadOnly(true)
                                    .withValue(new Date())))
                    .appendChild(
                        Column.span6()
                            .styler(sampleStyle -> sampleStyle.setMarginBottom("0px"))
                            .appendChild(
                                placeTextBox
                                    .setReadOnly(true)
                                    .appendChild(PrefixAddOn.of(Icons.location_enter()))
                                    .withValue(
                                        corporateProfile.getAddress().getCountryISOCode()
                                            + " - "
                                            + corporateProfile.getAddress().getCity()))))
            .element());
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {}

  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
