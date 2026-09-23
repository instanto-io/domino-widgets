// Original showcase helper; see upstream/showcase-lock.json.
package org.dominokit.domino.formsamples.client.views.ui;

import static org.dominokit.domino.ui.utils.Domino.*;

import io.instanto.domino.client.FormExampleSupport;
import java.util.List;
import org.dominokit.domino.formsamples.shared.model.Country;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.icons.lib.Icons;
import org.dominokit.domino.ui.utils.PrefixAddOn;

public class CountriesComponent {

  private Select<Country> countriesSelect;

  private Select<String> citiesSelect;

  public CountriesComponent(List<Country> countries) {
    this();
    setCountries(countries);
  }

  public CountriesComponent() {
    countriesSelect =
        Select.<Country>create("Country")
            .appendChild(PrefixAddOn.of(i().css("fas", "fa-globe", "fa-lg")));
    citiesSelect =
        Select.<String>create("City").appendChild(PrefixAddOn.of(Icons.city())).disable();
    FormExampleSupport.onSelect(
        countriesSelect,
        option -> {
          citiesSelect.enable();
          citiesSelect.removeAllOptions();
          Country country = option.getValue();
          for (String city : country.getCities()) {
            citiesSelect.appendChild(SelectOption.create(String.valueOf(city), city, city));
          }
        });
  }

  public static CountriesComponent create(List<Country> countries) {
    return new CountriesComponent(countries);
  }

  public static CountriesComponent create() {
    return new CountriesComponent();
  }

  public CountriesComponent setCountries(List<Country> countries) {
    countriesSelect.removeAllOptions();
    for (Country country : countries) {
      countriesSelect.appendChild(
          SelectOption.create(String.valueOf(country.getName()), country, country.getName()));
    }
    return this;
  }

  public Select<Country> getCountriesSelect() {
    return countriesSelect;
  }

  public Select<String> getCitiesSelect() {
    return citiesSelect;
  }
}
