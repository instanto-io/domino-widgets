@skip-jvm
Feature: Original Domino visual references
  Scenario: The colour scales include the original roles and shades
    Given the Domino showcase page "colors" is open
    Then the colour gallery shows material and semantic colour scales

  Scenario: The icon catalogue filters and reveals a Java call
    Given the Domino showcase page "mdiicons" is open
    Then I can search and select an icon

  Scenario: Theme accents change the live page
    Given the Domino showcase page "themes" is open
    Then I can switch the showcase accent
