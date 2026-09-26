# Coverage and limitations

A compiled declaration is not evidence of browser behaviour. `browser-tested` below refers only to the named scenarios. The last complete development and optimised production run passed the shared 115-scenario suite in Chromium, Firefox and WebKit, including the three reference pages. Native scrolling checks passed on both builds; two platform-specific cases were skipped as expected. See the [verified CI run](https://github.com/instanto-io/domino-widgets/actions/runs/35999251003) for the recorded outcomes; its artifacts hold the compiler and source metadata. Two further rich-text formatting scenarios pass focused local checks; the expanded 117-scenario CI run remains pending.

| Area | Coverage |
|---|---|
| Advanced tables | Editing and validation, grouping, details updates, context menus, summaries, scroll loading, eager/lazy trees, configured roots, combined selection and row drag and drop |
| Original gallery | 69 pages, 190 extracted sample methods: render, nonempty content and uncaught browser errors; [page inventory](SHOWCASE.md) |
| Visual references | Original colour scales, searchable icon categories and theme accents render; focused browser scenarios exercise swatches, icon selection and accent changes |
| Showcase scrolling | Native wheel scrolling in Chromium, Firefox and WebKit; native touch scrolling to the footer and back in Chromium, on both compiler builds. Physical iOS touch behaviour remains unverified. |
| Button and lifecycle | Clicks, exact handler removal, repeated detach/reattach, mutation observers, original sample interactions |
| Text inputs | Required validation, invalid-state clearing, value-change events, clearing and changed numeric input |
| Dialogs | Repeated open/close, Escape, original message/alert/custom content interactions |
| Tables | 22 original table pages render; contracts also cover selection, record replacement, store search and pagination |
| Tree, chips, tabs | Nested branch expansion/collapse; removing chips; repeated visible tab switching |
| Calendar | Day selection, next/previous month navigation; strict leap-date parsing and invalid-date rejection |
| Locale labels | Explicit Spanish and Arabic month labels through native Intl; full CLDR/week-start/time-zone parity remains unassessed |
| Layouts and complete form | Application drawers, flex add/reset, populated form data, required validation, optional packing-list sections and adding/removing documents; shared Cucumber Tea scenarios |
| Advanced forms | Original dynamic country suggestions fetch JSON and select a result; original file upload sends multipart bytes through XHR and handles a controlled successful response |
| Rich text | Original editor renders, accepts content editing, returns HTML and resets its value; selecting content and applying Bold, Italic or Underline passes focused browser checks. Other toolbar commands, clipboard and browser permission cases remain unassessed. |
| Browser APIs | Local/session storage roundtrip/deletion, SVG namespace, Blob URL/fetch/text, FileReader, promise fulfillment/rejection conversion, pushState/popstate |
| Elemental2 and JsInterop foundations | Inherited fields, globals/native constructors, generic arrays/maps, native and Java identity, primitive conversion, missing values, null/undefined, custom events, union timer callbacks |
| Independent reuse | Published Elemental2 console logger uses compatibility artifacts without Domino or Verrai |
| Remaining widget behaviours | `compiles`; a gallery render test does not establish all interactions, accessibility or edge cases |
| Native constructor varargs with supplied arguments | `unsupported`; TeaVM 0.15 does not spread them. Zero-argument Array is explicitly adapted |
| `Js.asConstructorFn(Class)` | `unsupported`; explicit failure rather than an invented reflection mapping |
| Arbitrary JsInterop reflection/base surface | Outside the implemented subset |
| Optional Domino history/REST packages | Not included; native API contracts above do not certify those optional libraries |
| WebAssembly GC | Outside the supported browser-JavaScript targets |
| Verrai/Sarto application retirement | In progress; [consumer inventory and concrete blocker](MIGRATION.md) |

Baseline: TeaVM 0.15.0, JDK 21, Java release 17, Elemental2 1.2.3, native JsInterop base 1.0.1 and annotations 2.0.2. Current builds use upstream links for comparison.

The complete widget archive is retained. Original widget implementations are shared;
the [details-renderer correction](DESIGN.md#record-details-and-parent-rows) is applied
when generating the port. The framework demo shell, optional history/REST modules
and annotation/webjar processor build machinery are excluded from the runtime
libraries. Checked-in generated icons and their inputs are retained.

Known upstream behaviour: rapid tree collapse during the expansion animation can leave height inconsistent with the collapsed flag (also reproduced on GWT); the normal expand/collapse contract waits for the animation to finish.  `DataTable.filterRows` adds `table-row-filtered`, but the pinned stylesheet has no corresponding hiding rule. Tests use actual data-store search filtering. GitHub Pages has no upload backend, and gallery media rendering does not certify playback or device APIs. Further application-layout interactions, locale/time-zone edge cases, upload cancellation/error/retry, rich-text toolbar/clipboard and optional integrations need further targeted contracts.
