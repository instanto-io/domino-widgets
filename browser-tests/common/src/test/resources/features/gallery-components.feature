@skip-jvm
Feature: Pinned original component examples
  Scenario Outline: The original <route> example renders
    Given the Domino showcase page "<route>" is open
    Then the original example renders with loaded images and no browser errors

    Examples:
      | route |
      | buttons |
      | forms |
      | dialogs |
      | alerts |
      | badges |
      | breadcrumb |
      | cards |
      | chips |
      | collapse |
      | grids |
      | helpers |
      | infobox |
      | inputfields |
      | labels |
      | lists |
      | loaders |
      | menu |
      | notifications |
      | pagination |
      | popover |
      | preloaders |
      | progress |
      | sliders |
      | spin |
      | splitPanel |
      | steppers |
      | tabs |
      | timepicker |
      | typography |
      | waves |
      | datepicker |
      | formsvalidations |
      | tree |
      | advanced-forms |
