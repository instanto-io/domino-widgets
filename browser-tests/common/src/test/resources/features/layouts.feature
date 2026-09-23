@skip-jvm
Feature: Original layout and complete form examples
  Scenario: Flex layout adds and resets blocks
    Given the Domino showcase page "flex-layout" is open
    Then the flex playground adds and resets its blocks

  Scenario: Layout drawers open and close
    Given the Domino showcase page "app-layout" is open
    Then the layout drawers respond to their controls

  Scenario: Complete form validates required sections
    Given the Domino showcase page "form-samples" is open
    Then the complete form contains sample data and validates missing values

  Scenario: Optional document sections remain reachable
    Given the Domino showcase page "form-samples" is open
    Then I can enable and complete the packing list section

  Scenario: Additional documents can be added and removed
    Given the Domino showcase page "form-samples" is open
    Then I can add and remove an additional document
