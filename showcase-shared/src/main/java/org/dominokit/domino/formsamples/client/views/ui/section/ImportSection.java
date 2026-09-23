// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui.section;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLElement;
import org.dominokit.domino.formsamples.shared.model.LetterOfCredit;
import org.dominokit.domino.ui.IsElement;

public interface ImportSection extends IsElement<HTMLElement> {

  void collect(LetterOfCredit letterOfCredit);

  boolean validate();
}
