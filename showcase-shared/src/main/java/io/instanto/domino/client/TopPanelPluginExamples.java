// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import java.util.List;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datatable.CellTextAlign;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.events.TableDataUpdatedEvent;
import org.dominokit.domino.ui.datatable.plugins.header.HeaderBarPlugin;
import org.dominokit.domino.ui.datatable.plugins.header.TopPanelPlugin;
import org.dominokit.domino.ui.datatable.plugins.pagination.BodyScrollPlugin;
import org.dominokit.domino.ui.datatable.plugins.pagination.SortPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListScrollingDataSource;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.icons.lib.Icons;

public final class TopPanelPluginExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    topPanelPlugin();
    return element.element();
  }

  private void topPanelPlugin() {
    ContactsTopPanel<Contact> topPanel = new ContactsTopPanel<>();
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .setFixed(true)
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .sortable()
                .styleCell(
                    cellElement ->
                        elementOf(cellElement).setCssProperty("vertical-align", "middle"))
                .setTextAlign(CellTextAlign.RIGHT)
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getIndex() + 1 + "")))
        .addColumn(
            ColumnConfig.<Contact>create("status", "Status")
                .setTextAlign(CellTextAlign.CENTER)
                .setCellRenderer(
                    cell -> {
                      if (cell.getTableRow().getRecord().isActive()) {
                        return Icons.check_circle().addCss(dui_fg_green_d_3).element();
                      } else {
                        return Icons.close_circle().addCss(dui_fg_red_d_3).element();
                      }
                    }))
        .addColumn(
            ColumnConfig.<Contact>create("firstName", "First name")
                .sortable()
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getName()))
                .setWidth("200px"))
        .addColumn(
            ColumnConfig.<Contact>create("gender", "Gender")
                .setCellRenderer(cell -> ContactUiUtils.getGenderElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("eyeColor", "Eye color")
                .styleHeader(head -> elementOf(head).setWidth("100px"))
                .setCellRenderer(cell -> ContactUiUtils.getEyeColorElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("balance", "Balance")
                .sortable()
                .setCellRenderer(cellInfo -> ContactUiUtils.getBalanceElement(cellInfo.getRecord()))
                .setWidth("250px"))
        .addColumn(
            ColumnConfig.<Contact>create("email", "Email")
                .setWidth("300px")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getEmail())))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
                .setWidth("150px")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getPhone())))
        .addColumn(
            ColumnConfig.<Contact>create("badges", "Badges")
                .setCellRenderer(
                    cell -> {
                      if (cell.getTableRow().getRecord().getAge() < 35) {
                        return Badge.create("Young").addCss(dui_green, dui_float_none).element();
                      }
                      return text("");
                    }))
        .addPlugin(new BodyScrollPlugin<>())
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()))
        .addPlugin(
            new TopPanelPlugin<Contact>() {

              @Override
              public HTMLElement element() {
                return topPanel.element();
              }

              @Override
              public void handleEvent(org.dominokit.domino.ui.utils.DominoEvent event) {
                if (TableDataUpdatedEvent.DATA_UPDATED.equals(event.getType())) {
                  topPanel.update((TableDataUpdatedEvent<Contact>) event);
                }
              }
            })
        .addPlugin(new SortPlugin<>());

    LocalListScrollingDataSource<Contact> scrollingDataSource =
        new LocalListScrollingDataSource<Contact>(10)
            .setSearchFilter(new ContactSearchFilter())
            .setRecordsSorter(new ContactSorter());

    DataTable<Contact> table = new DataTable<>(tableConfig, scrollingDataSource);

    element.appendChild(
        Card.create(
                "TOP PANEL PLUGIN",
                "A simple panel that listens to datatable events and update its content"
                    + " accordingly.")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    List<Contact> data = ContactsProvider.instance.subList(90);
    scrollingDataSource.setData(data);
    table.load();
    topPanel.update(data);
  }
}
