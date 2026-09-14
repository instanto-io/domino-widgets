// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datatable.CellTextAlign;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.plugins.DragDropPlugin;
import org.dominokit.domino.ui.datatable.plugins.header.HeaderBarPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.typography.BlockHeader;

public final class DragAndDropPluginExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    dragAndDrop();
    dragAndDropDifferentSources();
    return element.element();
  }

  private void dragAndDrop() {
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
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
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getName())))
        .addColumn(
            ColumnConfig.<Contact>create("gender", "Gender")
                .setCellRenderer(cell -> ContactUiUtils.getGenderElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("eyeColor", "Eye color")
                .setCellRenderer(cell -> ContactUiUtils.getEyeColorElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("balance", "Balance")
                .setCellRenderer(
                    cellInfo -> ContactUiUtils.getBalanceElement(cellInfo.getRecord())))
        .addColumn(
            ColumnConfig.<Contact>create("email", "Email")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getEmail())))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
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
        .addPlugin(new DragDropPlugin<>())
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()));

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);

    element.appendChild(
        Card.create("DRAG & DROP", "Reorder records by dragging and dropping them")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    localListDataStore.setData(ContactsProvider.instance.subList());
  }

  private void dragAndDropDifferentSources() {
    TableConfig<Contact> tableConfig = new TableConfig<>();
    DragDropPlugin<Contact> dragDropPlugin = new DragDropPlugin<>();
    tableConfig
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
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
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getName())))
        .addColumn(
            ColumnConfig.<Contact>create("gender", "Gender")
                .setCellRenderer(cell -> ContactUiUtils.getGenderElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("eyeColor", "Eye color")
                .setCellRenderer(cell -> ContactUiUtils.getEyeColorElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("balance", "Balance")
                .setCellRenderer(
                    cellInfo -> ContactUiUtils.getBalanceElement(cellInfo.getRecord())))
        .addColumn(
            ColumnConfig.<Contact>create("email", "Email")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getEmail())))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
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
        .addPlugin(dragDropPlugin)
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()));

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);

    // ------ second table

    TableConfig<Contact> tableConfig2 = new TableConfig<>();
    DragDropPlugin<Contact> dragDropPlugin2 = new DragDropPlugin<>();
    tableConfig2
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
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
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getName())))
        .addColumn(
            ColumnConfig.<Contact>create("gender", "Gender")
                .setCellRenderer(cell -> ContactUiUtils.getGenderElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("eyeColor", "Eye color")
                .setCellRenderer(cell -> ContactUiUtils.getEyeColorElement(cell.getRecord()))
                .setTextAlign(CellTextAlign.CENTER))
        .addColumn(
            ColumnConfig.<Contact>create("balance", "Balance")
                .setCellRenderer(
                    cellInfo -> ContactUiUtils.getBalanceElement(cellInfo.getRecord())))
        .addColumn(
            ColumnConfig.<Contact>create("email", "Email")
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getEmail())))
        .addColumn(
            ColumnConfig.<Contact>create("phone", "Phone")
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
        .addPlugin(dragDropPlugin2)
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()));

    LocalListDataStore<Contact> localListDataStore2 = new LocalListDataStore<>();
    DataTable<Contact> table2 = new DataTable<>(tableConfig2, localListDataStore2);

    dragDropPlugin.linkWith(table2);
    dragDropPlugin2.linkWith(table);

    element.appendChild(
        Card.create(
                "DRAG & DROP DIFFERENT TABLES",
                "Moving records by dragging and dropping them from/to data tables")
            .setCollapsible(true)
            .appendChild(new TableStyleActions(table))
            .appendChild(
                div()
                    .addCss(dui_flex, dui_gap_5, dui_flex_col, dui_justify_between)
                    .appendChild(
                        div().appendChild(BlockHeader.create("TABLE - 1")).appendChild(table))
                    .appendChild(
                        div().appendChild(BlockHeader.create("TABLE - 2")).appendChild(table2)))
            .element());

    localListDataStore.setData(ContactsProvider.instance.subList(10));
    localListDataStore2.setData(ContactsProvider.instance.subList(10));
  }
}
