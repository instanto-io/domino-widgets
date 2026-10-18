@skip-jvm
Feature: Independent Domino ecosystem ports
  Scenario: Markdown renders extensions to HTML and native DOM
    Given the independent ecosystem application is open
    Then Markdown extensions render through both browser renderers
    And Markdown renders plain text and survives a Markdown round trip

  Scenario: Markdown applies the configured safe rendering options
    Given the independent ecosystem application is open
    Then raw HTML and unsafe links follow the safe rendering configuration

  Scenario: History preserves state across navigation and intercepts blocked routes
    Given the independent ecosystem application is open
    Then History supports parameters replacement back forward and cancellation
