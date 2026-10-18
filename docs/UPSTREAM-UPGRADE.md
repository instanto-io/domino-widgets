# DominoKit release intake

## Domino UI 2.1.3

The source archive and matching CSS, fonts and theme assets now use upstream
tag `2.1.3`, commit `086379cf654a02e7f4adfd4c32af6e57eb0ec6fa`.
The port still publishes under `io.instanto` with its own artifact version.
The build uses JDK 21 and emits Java 17 bytecode, satisfying the new upstream baseline.

`upstream/source-lock.json` records the archive checksum. The archive contains
the release plus `upstream/teavm-2.1.3.patch`: retained browser timers and
scheduling, and integral epoch milliseconds for CSS-safe identifiers. The patch
applies to a checkout of the exact release with `git apply`. Shared GWT services,
including calendar locale support, continue to come from `teavm-compat`.

`GenerateDomino` retains the record-details target-row repair and adapts two new
GWT runtime hooks: Tooltip uses `DominoTimer`, and Dynamic CSS uses TeaVM's
ahead-of-time semantics instead of GWT's development-mode preloading check.
The CSSOM selector search first rejects text that cannot contain the requested
selector, before splitting comma-separated selectors. This preserves matching
behavior while removing the reproducible WebKit load timeout on the utility-heavy
helpers page (the focused scenario completes in about three seconds).

The `release-213` browser fixture covers deferred theme restoration during a
theme operation, theme-change listeners, CSSOM padding generation, construction
of PageHeader/UnitValue/Fab, and direct DateBox/TimeBox text entry. Existing
gallery, widget and native browser tests remain applicable.

The artifact boundary check copies the three compatibility libraries explicitly;
compiler patch JARs inherited from the parent are not widget runtime libraries.

## Validation

The development reactor build passes, including nine build-tool tests, TeaVM
compilation, the artifact/source-hash checks and the SpotBugs gate. The 119-scenario
Chrome suite passed after updating two test assumptions: the dynamic border-width
class and waiting for the tree expansion transition before clicking again.

The 119-scenario WebKit run found the same border selector issue and a reproducible
helpers-page timeout. Focused reruns pass after the selector update and the CSSOM
lookup optimization. The six focused release, theme, colour and helpers scenarios
pass in both Chrome and WebKit with the optimization in place.

The production reactor build also passes with advanced optimization, minification,
static analysis and packaging checks enabled. After adding the independent ports
and showcase playgrounds, the full expanded 139-scenario suite passes against its
JavaScript output in both Chrome and WebKit. Formatting and `git diff --check` pass.

The production native input/layout check with
`-Ddomino.browser.engines=chromium,webkit` passes five tests; the sixth is the
intentional WebKit skip for Chromium-only touch injection. Local build and browser
logs are retained under `target/upgrade-validation/` (not committed).

Firefox validation is incomplete. The TeaVM launcher expects an absent
`/Applications/Firefox.app`. Connecting the cached Firefox 141 test browser instead
repeatedly stalled on its first page, emitting sandbox and SWGL framebuffer errors;
those runs were stopped. The native scrolling matrix also stalled at Firefox and
was stopped. These are not recorded as passes.

## Other announced releases

| Library | Requested release | Finding |
| --- | --- | --- |
| SLF4J Logger | 2.0.1 | Added as `io.instanto:domino-slf4j-teavm`, with a TeaVM SLF4J 2 provider bridge. |
| History | 1.0.6 | Added as `io.instanto:domino-history-teavm`, based on the 1.0.6 client and shared model. |
| REST | 2.0.2 | Added as `io.instanto:domino-rest-teavm`, using REST 2.0.2 shared code and processor, with repaired TeaVM regex adapters. |
| Jackson | 1.0.6 | Added as `io.instanto:domino-jackson-teavm`, using 1.0.6 runtime sources and upstream processor. Record/date/collection mapping is exercised in the browser. |
| Brix | 1.0.0 | Deferred by project decision; keep Sarto CDI and the existing application composition model. |
| Markdown | 1.0.0 | Added as `io.instanto:domino-md-teavm`, including all four renderers and bundled extensions. |

### Logger 2.0.1

The old TeaVM SLF4J bridge lacked the provider API required by SLF4J 2; using JVM
provider discovery instead reached unsupported class-loader and queue APIs.
`domino-slf4j-teavm` now replaces that discovery only during TeaVM compilation,
while keeping the standard SLF4J 2.0.16 API and normal JVM backend discovery.
No duplicate `org.slf4j` classes or JVM provider registration are packaged.

The logger uses the same pinned source intake and Elemental2 runtime as the other
ports. It fixes upstream level comparisons and marker filtering, and connects
standard fluent events to Domino marker routing. UI and REST use the aligned API;
the showcase, independent logger fixture and REST fixture select the new backend.
See [logger usage and limits](ECOSYSTEM.md#logger) for setup and MDC semantics.

## Additional port validation

History 1.0.6, Markdown 1.0.0, Jackson 1.0.6, REST 2.0.2, and Logger 2.0.1 are separate
TeaVM artifacts, managed by the BOM. The final combined production build passes
258 JVM tests, static analysis, and artifact/source-hash verification. Seventeen
focused browser scenarios pass in Chrome and WebKit against its optimized output.
See [ecosystem usage and compatibility limits](ECOSYSTEM.md) for source provenance,
processor configuration, the REST regex repair, Jackson's GWT helper exclusions,
and the precise scope of these checks.

The showcase includes an editable Markdown playground with all four renderers,
extension samples, and a logging playground with marker routes and nested MDC.
Both are linked from the home page and searchable navigation. Their six browser
scenarios pass in Chrome and WebKit. Publication uses the existing main-branch
verification workflow: Pages deploys its verified production artifact, and the
Maven workflow publishes snapshots after verification succeeds.
