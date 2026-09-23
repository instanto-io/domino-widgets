// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views;

import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;

public interface FormSamplesView {

  void onSuccessCreate(String bodyAsString);

  void onErrorCreate(String errorMessage);

  interface FormSamplesUIHandlers {
    void onCreate(LetterOfCredit letterOfCredit);
  }
}
