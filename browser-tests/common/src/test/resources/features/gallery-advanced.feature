@skip-jvm
Feature: Pinned original advanced and layout examples
  Scenario Outline: The original <route> example renders
    Given the Domino showcase page "<route>" is open
    Then the original example renders with loaded images and no browser errors

    Examples:
      | route |
      | animation |
      | carousel |
      | media |
      | thumbnails |
      | modals |
      | dnd |
      | table-basic-data-table |
      | table-selection-plugin |
      | table-sort-and-search-plugin |
      | table-pagination-plugin |
      | table-header-bar-plugin |
      | table-column-resize-plugin |
      | table-pin-columns-plugin |
      | table-empty-state-plugin |
      | table-fixed-data-table |
      | table-marker-plugin |
      | table-columns-groups |
      | table-editable-table |
      | table-drag-drop-plugin |
      | table-grouping-plugin |
      | table-record-details-plugin |
      | table-row-menu-plugin |
      | table-scroll-loading |
      | table-summary-plugin |
      | table-top-panel-plugin |
      | table-eager-tree-plugin |
      | table-lazy-tree-plugin |
      | table-mix-plugins |
      | app-layout |
      | flex-layout |
      | grid-layout |
      | form-samples |
      | colors |
      | mdiicons |
      | themes |
