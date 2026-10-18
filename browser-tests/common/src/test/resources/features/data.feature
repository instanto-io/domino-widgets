@skip-jvm
Feature: Generated Domino data clients
  Scenario: Generated Jackson record mappers work in TeaVM
    Given the independent data application is open
    Then the Jackson record preserves escaped text collections and dates

  Scenario: Generated REST clients exchange typed JSON and report failures
    Given the independent data application is open
    Then REST sends parameterized requests and JSON bodies with response interceptors
