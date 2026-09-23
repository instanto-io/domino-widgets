package io.instanto.domino.client;

import java.util.function.Consumer;
import org.dominokit.domino.ui.forms.TextBox;
import org.dominokit.domino.ui.forms.suggest.Select;
import org.dominokit.domino.ui.forms.suggest.SelectOption;
import org.dominokit.domino.ui.forms.validations.ValidationResult;

/** Small adaptations used only by the original letter-of-credit demonstration. */
public final class FormExampleSupport {
  private FormExampleSupport() {}

  public static <T> Select<T> onSelect(Select<T> select, Consumer<SelectOption<T>> handler) {
    return select.addChangeListener(
        (oldValue, value) -> {
          if (select.getSelectedOption() != null) handler.accept(select.getSelectedOption());
        });
  }

  public static TextBox numbersOnly(TextBox field) {
    field.getInputElement().setAttribute("inputmode", "decimal");
    return field.addValidator(
        ignored ->
            field.isEmpty() || field.getValue().matches("[0-9.,\\s]+")
                ? ValidationResult.valid()
                : ValidationResult.invalid("Numbers only"));
  }

  public static TextBox positiveWholeNumber(TextBox field) {
    field.getInputElement().setAttribute("inputmode", "numeric");
    return field.addValidator(
        ignored -> {
          if (field.isEmpty()) return ValidationResult.valid();
          try {
            if (Integer.parseInt(field.getValue()) > 0) return ValidationResult.valid();
          } catch (NumberFormatException invalid) {
            // Copy counts must fit the integer model used by the original form.
          }
          return ValidationResult.invalid("Enter a positive whole number");
        });
  }
}
