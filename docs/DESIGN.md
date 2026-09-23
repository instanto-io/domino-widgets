# Domino Widgets design

## What this is

Domino Widgets is the TeaVM port of DominoKit’s Domino UI. It carries DominoKit API and behaviour into the TeaVM runtime.

For GWT users, use DominoKit upstream directly. This repository does not maintain or publish a separate GWT widget distribution.

## What this port publishes

This repository publishes:

- `domino-widgets-teavm`
- `domino-widgets-assets`
- `domino-widgets-bom`

## Source ownership and compatibility model

- The source set is pinned, archived, and checksummed so the port can be reproduced.
- Package names and copyright headers stay unchanged.
- The build validates the archive and feeds it to the `jsinterop-binding-generator`.
- Widget-specific source selection and adaptations are done in this repository.

TeaVM-compatibility modules are kept together in the
[teavm-compat repository](https://github.com/instanto-io/teavm-compat):

- Elemental2, JsInterop base, and modular service compatibility implementations.
- Shared tests and release flow.
- Runtime widget code relies on the common [TeaVM compatibility libraries](https://github.com/instanto-io/teavm-compat) for browser interop.

## Showcase

The showcase uses adapted upstream examples and links each page back to the matching
DominoKit page.

The process is:

- Keep a pinned list of source examples and checksums.
- Generate adapters for the selected examples.
- Check that maintained files still match the pinned inputs.
- Keep original sample sources for easy comparison.

### Tables

The table examples use upstream widgets, plugins, and event handlers with local
sample contacts.

Small API changes were required to keep behaviour consistent:

- `CellRenderer.CellInfo` became `RowCellInfo`.
- Group cell code now works with the table row element so launcher-level interactions
  (selection, styling, and actions) can use the right DOM node.
- Event callbacks now use `DominoEvent`.
- Where possible, we keep callback interfaces unchanged so existing handlers continue
  to run without changes.

### Record details behaviour

The original DominoKit detail rendering used a temporary row object that missed the status column.
That meant status edits from the details panel could not update the original row.

This is a bug in the adapted port and we fixed it:

- `RecordDetailsCellInfo.getTargetRow()` exposes the original target row.
- The adapter passes that row to the original renderer.

Automated checks cover:

- editing a parent row from details
- closing the details panel after editing

### Forms and layouts

The application, flex, grid, and form samples come from the
pinned DominoKit demo used by the gallery.

Form behaviour is kept stable for:

- section structure
- field relationships
- validation rules

At runtime, sample JSON is converted into Java objects during build-time generation.
No JSON mapper or application service runs in the live showcase.

We intentionally modernised the pinned examples by keeping a local adapter layer.
This allows the examples to stay close to upstream sources while compiling against
the current API. The adapter handles:

- fluent setter usage
- selection callbacks
- validation wiring
- popup placement

Some interactions are preserved to match the source behaviour (for example, how section
visibility works with optional titles and switches).

## Verification

Compatibility coverage now tracks TeaVM output in Chromium, Firefox, and WebKit for both
supported build modes.

## Further references

- [Showcase provenance](SHOWCASE.md)
- [Compatibility matrix](COMPATIBILITY.md)
- [Development commands](DEVELOPMENT.md)
- [Port fixes and upstream PRs](UPSTREAM-PRS.md)
