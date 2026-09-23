// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.*;
import elemental2.dom.HTMLDivElement;
import java.util.*;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.grid.Column;
import org.dominokit.domino.ui.grid.Row;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;

public final class MdiIconsExamples implements org.dominokit.domino.ui.style.DominoCss {

  private final DivElement element = div();

  public HTMLDivElement render() {
    element.appendChild(IconBrowser.create());
    mdiEffects();
    return element.element();
  }

  private void mdiEffects() {
    element.appendChild(
        Card.create("Icons advanced features")
            .appendChild(BlockHeader.create("Sizes"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Size 18px"))
                            .appendChild(Icons.account().addCss(dui_font_size_4)))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Size 24px"))
                            .appendChild(Icons.account().addCss(dui_font_size_6)))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Size 36px"))
                            .appendChild(Icons.account().addCss(dui_font_size_10)))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Size 48px"))
                            .appendChild(Icons.account().addCss(dui_font_size_14))))
            .appendChild(BlockHeader.create("Rotate"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("No rotate"))
                            .appendChild(Icons.account()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 45"))
                            .appendChild(Icons.account().rotate45()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 90"))
                            .appendChild(Icons.account().rotate90()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 135"))
                            .appendChild(Icons.account().rotate135())))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 180"))
                            .appendChild(Icons.account().rotate180()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 255"))
                            .appendChild(Icons.account().rotate225()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 270"))
                            .appendChild(Icons.account().rotate270()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Rotate 315"))
                            .appendChild(Icons.account().rotate315())))
            .appendChild(BlockHeader.create("Flip"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span4()
                            .appendChild(h(6).textContent("No flip"))
                            .appendChild(Icons.account_alert()))
                    .appendChild(
                        Column.span4()
                            .appendChild(h(6).textContent("Flip horizontal"))
                            .appendChild(Icons.account_alert().flipH()))
                    .appendChild(
                        Column.span4()
                            .appendChild(h(6).textContent("Flip vertical"))
                            .appendChild(Icons.account_alert().flipV())))
            .appendChild(BlockHeader.create("Spin"))
            .appendChild(
                Row.create()
                    .appendChild(Column.span6().appendChild(Icons.loading().spin()))
                    .appendChild(Column.span6().appendChild(Icons.star().spin())))
            .appendChild(BlockHeader.create("Contrast"))
            .appendChild(
                Row.create()
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Light"))
                            .appendChild(
                                div()
                                    .style("width: 40px; height:40px")
                                    .addCss(dui_black, dui_font_size_10)
                                    .appendChild(Icons.account().light())))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Light inactive"))
                            .appendChild(
                                div()
                                    .style("width: 40px; height:40px")
                                    .addCss(dui_black, dui_font_size_10)
                                    .appendChild(Icons.account().light().inactive())))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Dark"))
                            .appendChild(Icons.account().dark()))
                    .appendChild(
                        Column.span3()
                            .appendChild(h(6).textContent("Dark inactive"))
                            .appendChild(Icons.account().dark().inactive())))
            .setCollapsible(true));
  }
}
