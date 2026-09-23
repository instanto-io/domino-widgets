// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import java.util.List;
import org.dominokit.domino.formsamples.client.views.ui.AddLCImportComponent;
import org.dominokit.domino.formsamples.shared.model.*;
import org.dominokit.domino.formsamples.shared.model.Country;
import org.dominokit.domino.ui.dialogs.MessageDialog;
import org.dominokit.domino.ui.utils.ElementUtil;

public final class FormSamplesExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final HTMLDivElement element = div().element();
  private CorporateProfile corporateProfile;
  private List<Country> countries;
  private List<Beneficiary> beneficiaries;
  private List<Bank> banks;
  private List<CurrencyData> currencies;
  private AddLCImportComponent addLCImportComponent;
  private org.dominokit.domino.formsamples.client.views.FormSamplesView.FormSamplesUIHandlers
      uiHandlers;

  private static final class Scheduler {
    static Scheduler get() {
      return new Scheduler();
    }

    void scheduleDeferred(Runnable work) {
      work.run();
    }
  }

  public HTMLDivElement render() {
    this.uiHandlers = this::onCreate;
    init();
    return element;
  }

  protected HTMLDivElement init() {
    element.classList.add("content-margin");
    Scheduler.get()
        .scheduleDeferred(
            () -> {
              this.corporateProfile = FormSampleData.profile();
              this.countries = FormSampleData.countries();
              this.beneficiaries = FormSampleData.beneficiaries();
              this.banks = FormSampleData.banks();
              this.currencies = FormSampleData.currencies();

              reBuildForm();
            });

    return element;
  }

  private void reBuildForm() {
    ElementUtil.clear(element);

    addLCImportComponent =
        new AddLCImportComponent(corporateProfile, countries, beneficiaries, banks, currencies);
    addLCImportComponent.setUiHandlers(this.uiHandlers);
    element.appendChild(addLCImportComponent.element());
  }

  public void onCreate(LetterOfCredit letterOfCredit) {
    MessageDialog.create("Letter of credit", "Your item created.")
        .addCss(org.dominokit.domino.ui.style.DominoCss.dui_success)
        .addCloseListener(
            dialog -> {
              ElementUtil.scrollTop();
              reBuildForm();
            })
        .open();
  }
}
