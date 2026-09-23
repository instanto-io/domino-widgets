// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section.documents;

import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createCopiesField;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createCountriesSelect;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createDescriptionField;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.createRequiredField;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.isInvalidatedCard;
import static org.dominokit.domino.formsamples.client.views.ui.CustomElements.markCardValidation;
import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.section.ImportSection;
import org.dominokit.domino.formsamples.shared.model.CertificateOfOrigin;
import org.dominokit.domino.formsamples.shared.model.Country;
import org.dominokit.domino.formsamples.shared.model.DocumentsRequired;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.forms.FieldsGrouping;
import org.dominokit.domino.ui.forms.SwitchButton;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class CertificateOfOriginPart implements ImportSection {

  private final TextBox numberOfCopiesTextBox;

  private TextBox certificateOfOriginCopiesTextBox;

  private SwitchButton certificateOfOriginRequiredSwitchButton;

  private TextBox certificateOfOriginTextBox;

  private Select<String> certificateOfOriginLocalizationEntitiesSelect;

  private Select<Country> certificateOfOriginOriginCountrySelect;

  private Select<Country> certificateOfOriginOriginOfGoodsCountrySelect;

  private Select<Country> certificateOfOriginOriginOfLocalizationEntitiesCountrySelect;

  private FieldsGrouping fieldsGrouping = FieldsGrouping.create();

  private Card certificateOfOriginCard;

  private HTMLDivElement element = div().element();

  public CertificateOfOriginPart(List<Country> countries) {
    certificateOfOriginCopiesTextBox =
        createCopiesField().setLabel("Original copies").groupBy(fieldsGrouping);
    certificateOfOriginCopiesTextBox
        .getInputElement()
        .addEventListener("input", evt -> revalidate());
    numberOfCopiesTextBox = createCopiesField().groupBy(fieldsGrouping);
    numberOfCopiesTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    certificateOfOriginRequiredSwitchButton =
        createRequiredField()
            .groupBy(fieldsGrouping)
            .addChangeListener(
                (oldValue, value) -> {
                  if (value) {
                    certificateOfOriginCard.expand();
                  } else {
                    certificateOfOriginCard.collapse();
                    revalidate();
                  }
                });
    certificateOfOriginTextBox = createDescriptionField().groupBy(fieldsGrouping);
    certificateOfOriginTextBox.getInputElement().addEventListener("input", evt -> revalidate());
    certificateOfOriginLocalizationEntitiesSelect =
        FormExampleSupport.onSelect(
            Select.<String>create("Legalization entities")
                .groupBy(fieldsGrouping)
                .setAutoValidation(true)
                .setRequired(true)
                .appendChild(PrefixAddOn.of(Icons.domain()))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Chamber of commerce"),
                        "Chamber of commerce",
                        "Chamber of commerce"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Official trade office"),
                        "Official trade office",
                        "Official trade office"))
                .appendChild(
                    SelectOption.create(
                        String.valueOf("Chamber of industries"),
                        "Chamber of industries",
                        "Chamber of industries")),
            option -> revalidate());
    certificateOfOriginOriginCountrySelect =
        FormExampleSupport.onSelect(
            createCountriesSelect("Country of origins", countries)
                .groupBy(fieldsGrouping)
                .setAutoValidation(true)
                .setRequired(true),
            option -> revalidate());
    certificateOfOriginOriginOfGoodsCountrySelect =
        FormExampleSupport.onSelect(
            createCountriesSelect("Origin of goods", countries)
                .groupBy(fieldsGrouping)
                .setAutoValidation(true)
                .setRequired(true),
            option -> revalidate());
    certificateOfOriginOriginOfLocalizationEntitiesCountrySelect =
        FormExampleSupport.onSelect(
            createCountriesSelect("Country of legalization entities", countries)
                .groupBy(fieldsGrouping)
                .setAutoValidation(true)
                .setRequired(true),
            option -> revalidate());
    certificateOfOriginCard =
        Card.create("Certificate of origin in")
            .withBody((card, body) -> body.setPaddingTop("40px"))
            .collapse();
    certificateOfOriginCard
        .getHeader()
        .getDescriptionElement()
        .appendChild(certificateOfOriginRequiredSwitchButton.element());
    element.appendChild(
        certificateOfOriginCard
            .appendChild(
                Row.create()
                    .appendChild(Column.span4().appendChild(certificateOfOriginCopiesTextBox))
                    .appendChild(Column.span4().appendChild(numberOfCopiesTextBox)))
            .appendChild(
                Row.create().appendChild(Column.span8().appendChild(certificateOfOriginTextBox)))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6().appendChild(certificateOfOriginLocalizationEntitiesSelect))
                    .appendChild(
                        Column.span6().appendChild(certificateOfOriginOriginCountrySelect)))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span6().appendChild(certificateOfOriginOriginOfGoodsCountrySelect))
                    .appendChild(
                        Column.span6()
                            .appendChild(
                                certificateOfOriginOriginOfLocalizationEntitiesCountrySelect)))
            .element());
  }

  public void revalidate() {
    if (isInvalidatedCard(certificateOfOriginCard) && isValid()) {
      markCardValidation(certificateOfOriginCard, true, false);
    }
  }

  private boolean isValid() {
    return !certificateOfOriginRequiredSwitchButton.getValue()
        || fieldsGrouping.validate().isValid();
  }

  @Override
  public void collect(LetterOfCredit letterOfCredit) {
    DocumentsRequired documentsRequired = letterOfCredit.getDocumentsRequired();
    CertificateOfOrigin certificateOfOrigin = new CertificateOfOrigin();
    certificateOfOrigin.setRequired(certificateOfOriginRequiredSwitchButton.getValue());
    if (certificateOfOrigin.isRequired()) {
      certificateOfOrigin.setDescription(certificateOfOriginTextBox.getValue());
      certificateOfOrigin.setNumberOfCopies(
          Integer.parseInt(certificateOfOriginCopiesTextBox.getValue()));
    }
    documentsRequired.setCertificateOfOrigin(certificateOfOrigin);
  }

  @Override
  public boolean validate() {
    boolean valid = isValid();
    markCardValidation(certificateOfOriginCard, valid);
    return valid;
  }

  @Override
  public HTMLElement element() {
    return element;
  }
}
