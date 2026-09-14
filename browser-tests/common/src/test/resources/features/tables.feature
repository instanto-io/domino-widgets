@skip-jvm
Feature: Advanced table examples on TeaVM
  Scenario: Save an edited contact
    Given the Domino showcase page "table-editable-table" is open
    When I change the first contact name to "Edited contact"
    Then the saved row contains "Edited contact"

  Scenario: Reject a balance beyond the editor limit
    Given the Domino showcase page "table-editable-table" is open
    When I try to save a balance above the limit
    Then the row remains editable until its balance is corrected

  Scenario: Collapse and restore a contact group
    Given the Domino showcase page "table-grouping-plugin" is open
    Then a contact group can be collapsed and restored

  Scenario: Update a record through its details panel
    Given the Domino showcase page "table-record-details-plugin" is open
    Then the contact details can change the row status and close

  Scenario: Activate a contact using its row menu
    Given the Domino showcase page "table-row-menu-plugin" is open
    Then the context menu can activate the first contact

  Scenario: Read table summaries
    Given the Domino showcase page "table-summary-plugin" is open
    Then the summary shows the contact total and average

  Scenario Outline: Load more records by scrolling
    Given the Domino showcase page "<route>" is open
    Then scrolling loads more contacts
    Examples:
      | route |
      | table-scroll-loading |
      | table-top-panel-plugin |

  Scenario Outline: Expand and collapse a tree grid
    Given the Domino showcase page "<route>" is open
    Then expanding a tree row reveals children and collapsing hides them
    Examples:
      | route |
      | table-eager-tree-plugin |
      | table-lazy-tree-plugin |

  Scenario: Rebuild the configurable lazy tree
    Given the Domino showcase page "table-lazy-tree-plugin" is open
    Then the tree controls replace the root records

  Scenario: Select active contacts with combined plugins
    Given the Domino showcase page "table-mix-plugins" is open
    Then the combined table selects active contacts

  Scenario: Reorder contacts within a table
    Given the Domino showcase page "table-drag-drop-plugin" is open
    Then dragging a contact changes the row order

  Scenario: Move a contact between tables
    Given the Domino showcase page "table-drag-drop-plugin" is open
    Then dragging a contact transfers it between tables
