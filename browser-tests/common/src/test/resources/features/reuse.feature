@skip-jvm
Feature: Independent compatibility reuse
  Scenario: Domino Logger 2.0.1 works without widget dependencies
    Given the independent logger application is open
    Then the logger works without widget dependencies

  Scenario: Standard SLF4J API formats ordinary and fluent log messages
    Given the independent logger application is open
    Then the logger contract "api" passes
    And the SLF4J fluent and formatted messages reach the console

  Scenario: Markers route formatted messages and exceptions to custom adapters
    Given the independent logger application is open
    Then the logger contract "routing" passes

  Scenario: Nested MDC scopes restore context after exceptions
    Given the independent logger application is open
    Then the logger contract "mdc" passes

  Scenario Outline: Configured levels filter ordinary and marker log calls
    Given the logger is configured at "<level>"
    Then the logger contract "levels" passes
    Examples:
      | level |
      | TRACE |
      | DEBUG |
      | INFO  |
      | WARN  |
      | ERROR |
      | OFF   |
