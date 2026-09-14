# Port design

Domino Widgets provides the TeaVM adaptation of DominoKit’s Domino UI. Widget API,
rendering and behavior come from DominoKit. GWT users should use upstream directly;
this repository no longer maintains or publishes a separate GWT distribution.

The pinned source archive retains provenance, including fixes submitted upstream.
Java packages and copyright headers remain unchanged. The build verifies the
archive and selects Domino sources for the separately published
`jsinterop-binding-generator`. Widget-specific source selection stays here.

Elemental2, JsInterop base and modular service compatibility implementations now
live in [teavm-compat](https://github.com/instanto-io/teavm-compat), with an independent
parent, BOM, immutable inputs, tests and publication workflow. Elemental2 source JARs
and their lock file are owned there and are no longer bundled in this widget repository. The runtime widget
JAR depends on compatibility APIs, not JavaParser or the generator.

Published widget artifacts are `domino-widgets-teavm`, `domino-widgets-assets` and
`domino-widgets-bom`, under `io.instanto`. Former TeaVM compatibility artifact names
remain relocation POMs during migration. The GWT widget and reference service
artifacts are no longer published.

The showcase retains adapted upstream examples and links each page to DominoKit's
upstream showcase. Historical reports include the previous GWT baseline; current
verification covers TeaVM in Chromium, Firefox and WebKit, in both build modes.

See [the extraction record](COMPAT-EXTRACTION.md), [showcase provenance](SHOWCASE.md),
[coverage](COMPATIBILITY.md) and [development commands](DEVELOPMENT.md).

## Derive the showcase from the original examples

The showcase manifest pins each original sample and its hash, the methods to
include, and any changes needed by the launcher. Maven generates the adapters
and checks that the maintained files match. The original sample files remain
available for comparison.

The table examples use the original widgets, plugins and event handlers with
local sample contacts. Their manifest maps older sample APIs to the pinned widget
API: `CellRenderer.CellInfo` becomes `RowCellInfo`, group cells expose `element()`,
and table event listeners accept `DominoEvent`. Details-renderer lambdas explicitly
select the legacy renderer overload. The launcher supplies CSS constants and
element factories previously inherited from the upstream demo's base classes.

## Record details and parent rows

The pinned widget code passes a temporary details row to legacy details renderers.
That row has no status column, so updating a contact's status from its details
panel fails. The Domino source adapter exposes the existing target row through
`RecordDetailsCellInfo.getTargetRow()` and passes it to the legacy renderer.
The temporary row still provides the details panel's HTML container.

The build requires the expected source fragments to be present so an upstream
change prompts review of this adjustment. The shared table scenarios verify
editing the parent record through the details panel and closing it afterwards.
