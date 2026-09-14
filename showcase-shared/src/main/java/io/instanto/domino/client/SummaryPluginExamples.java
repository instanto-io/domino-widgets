// Adapted from DominoKit/domino-ui-demo at 51e1f75d43179a544c010ca5e88517c93263eeed; see
// upstream/showcase-lock.json.
package io.instanto.domino.client;

import static org.dominokit.domino.ui.utils.Domino.*;

import elemental2.dom.HTMLDivElement;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import org.dominokit.domino.ui.badges.Badge;
import org.dominokit.domino.ui.cards.Card;
import org.dominokit.domino.ui.datatable.CellTextAlign;
import org.dominokit.domino.ui.datatable.ColumnConfig;
import org.dominokit.domino.ui.datatable.DataTable;
import org.dominokit.domino.ui.datatable.TableConfig;
import org.dominokit.domino.ui.datatable.plugins.column.ResizeColumnMeta;
import org.dominokit.domino.ui.datatable.plugins.header.HeaderBarPlugin;
import org.dominokit.domino.ui.datatable.plugins.summary.SummaryMeta;
import org.dominokit.domino.ui.datatable.plugins.summary.SummaryPlugin;
import org.dominokit.domino.ui.datatable.store.LocalListDataStore;
import org.dominokit.domino.ui.elements.DivElement;
import org.dominokit.domino.ui.icons.lib.Icons;

public final class SummaryPluginExamples implements org.dominokit.domino.ui.style.DominoCss {
  private final DivElement element = div();

  public HTMLDivElement render() {
    summaryPlugin();
    return element.element();
  }

  private void summaryPlugin() {
    SummaryPlugin<Contact, ContactSummary> summaryPlugin = new SummaryPlugin<>();
    TableConfig<Contact> tableConfig = new TableConfig<>();
    tableConfig
        .setFixed(true)
        .addColumn(
            ColumnConfig.<Contact>create("id", "#")
                .applyMeta(ResizeColumnMeta.create(false))
                .setTextAlign(CellTextAlign.RIGHT)
                .setCellRenderer(cell -> text(cell.getTableRow().getRecord().getIndex() + 1 + ""))
                .applyMeta(
                    SummaryMeta.<Contact, ContactSummary>of(
                        cell -> {
                          elementOf(cell.getElement())
                              .setAttribute("colspan", "5")
                              .setTextAlign("right")
                              .setCssProperty("font-weight", "600");
                          return text(cell.getRecord().getType() + " :");
                        })))
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
            ColumnConfig.<Contact>create("balance", "Balance")
                .setCellRenderer(cellInfo -> ContactUiUtils.getBalanceElement(cellInfo.getRecord()))
                .applyMeta(
                    SummaryMeta.<Contact, ContactSummary>of(
                        cell ->
                            div()
                                .addCss(dui_flex)
                                .appendChild(
                                    div().appendChild(text(cell.getRecord().getBalance() + "")))
                                .element())))
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
        .addPlugin(
            new HeaderBarPlugin<Contact>(
                    "Demo table", "Sample table table demonstrating the feature")
                .addActionElement(new HeaderBarPlugin.HoverTableAction<>())
                .addActionElement(new HeaderBarPlugin.CondenseTableAction<>())
                .addActionElement(new HeaderBarPlugin.StripesTableAction<>())
                .addActionElement(new HeaderBarPlugin.BordersTableAction<>()))
        .addPlugin(summaryPlugin);

    LocalListDataStore<Contact> localListDataStore = new LocalListDataStore<>();
    DataTable<Contact> table = new DataTable<>(tableConfig, localListDataStore);
    element.appendChild(
        Card.create("SUMMARY PLUGIN", "Show data aggregation and summary in the table footer.")
            .setCollapsible(true)
            .appendChild(table)
            .element());

    List<Contact> contacts = ContactsProvider.instance.subList();
    localListDataStore.setData(contacts);
    double sum = contacts.stream().mapToDouble(Contact::getBalance).sum();
    OptionalDouble balanceAverage = contacts.stream().mapToDouble(Contact::getBalance).average();
    OptionalDouble ageAverage = contacts.stream().mapToInt(Contact::getAge).average();
    summaryPlugin.setSummaryRecords(
        Arrays.asList(
            new ContactSummary("Sum", sum, -1),
            new ContactSummary(
                "Average",
                balanceAverage.orElse(0),
                Double.valueOf(ageAverage.orElse(-1)).intValue())));
  }
}
