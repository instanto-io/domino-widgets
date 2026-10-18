@skip-jvm
Feature: Public DominoKit library playgrounds
  Scenario: Library playgrounds are discoverable in the showcase
    Given the showcase overview is open
    Then the library playgrounds can be discovered from the overview and search

  Scenario: Markdown edits update all four output formats
    Given the Domino showcase page "markdown" is open
    Then the Markdown playground renders extensions and all output formats

  Scenario: Markdown sample buttons update the current output
    Given the Domino showcase page "markdown" is open
    Then Markdown samples replace the source and refresh the selected output

  Scenario: User-entered Markdown follows safe rendering options
    Given the Domino showcase page "markdown" is open
    Then the editable Markdown preview does not execute raw HTML or unsafe links

  Scenario: Logging controls route messages with levels and request context
    Given the Domino showcase page "logging" is open
    Then the logging playground routes events and filters disabled levels

  Scenario: Nested logging scopes restore the outer request context
    Given the Domino showcase page "logging" is open
    Then the logging playground demonstrates nested MDC restoration
