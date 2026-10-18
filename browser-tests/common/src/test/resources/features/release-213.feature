@skip-jvm
Feature: Domino UI 2.1.3 compatibility
  Scenario: New components, theme restoration and dynamic styles work in TeaVM
    Given the Domino showcase page "release-213" is open
    Then the release widgets restore themes and generate dynamic CSS

  Scenario: Date and time fields support direct entry
    Given the Domino showcase page "release-213" is open
    Then date and time fields accept direct text entry
