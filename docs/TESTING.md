# Browser tests

This document describes how we verify the port across the supported browsers.

## Purpose

The browser checks confirm that the ported widget code can run with TeaVM and that key interactions remain available to users. They focus on browser behaviour and interaction paths.

## How the browser checks are built

All checks start as Gherkin scenarios in
`../browser-tests/common/src/test/resources/features`.
Cucumber Tea generates the JUnit tests from those scenarios.

The generated tests run in three places:

- Chrome and Firefox: `TeaVMTestRunner` with `webapp-testkit` DOM services.
- WebKit: Java Playwright driving the WebKit browser.

The generated JUnit tests are reused by both build profiles.

## Running focused tests

Build the sites as described in [development](DEVELOPMENT.md), then run:

```sh
mvn -f browser-tests/teavm/pom.xml -Dtest=WidgetStepsTest test
mvn -f browser-tests/webkit/pom.xml -Dtest=WidgetStepsTest test
mvn -f browser-tests/teavm/pom.xml -Dtest=TableStepsTest test
```

Use this when you want to iterate on a specific area.

To add browser screenshots for WebKit, pass:

```sh
-Ddomino.capture=true
```

for the WebKit run.

## Running full browser suites

```sh
mvn -f browser-tests/teavm/pom.xml test
mvn -f browser-tests/webkit/pom.xml test
mvn -f browser-tests/platform-checks/pom.xml test
```

CI runs the two gallery halves and the remaining Firefox scenarios in separate
Maven processes. This restarts the browser between groups: in one long run, its
showcase process grew past 8 GB and stopped responding after the gallery. All
69 gallery examples and 46 focused scenarios still run.

### Native touch and scrolling checks

The platform checks run their own loopback server, perform native scrolling checks in all three engines, and execute touch gesture checks in Chromium.

## Suites and scenario counts

The `Legacy suite` column shows equivalent coverage in the historical JavaScript-driven suite used before this port, kept for parity tracking. The current checks now run as Cucumber Tea-generated JUnit tests; the JS suite itself is no longer the active driver.

| Feature | Scenarios per engine | Legacy suite |
|---|---:|---|
| `contracts.feature` | 11 | `contracts.spec.js` |
| `interactions.feature` | 13 | `interactions.spec.js` |
| `layouts.feature` | 5 | Original layout and complete-form interactions |
| `gallery-components.feature` | 34 | `gallery.spec.js` |
| `gallery-advanced.feature` | 35 | `gallery.spec.js` |
| `visual-references.feature` | 3 | Colour, icon and theme interactions |
| `tables.feature` | 14 | Advanced table interactions |
| `native.feature` | 1 | `native.spec.js` |
| `reuse.feature` | 1 | `reuse.spec.js` |
| `external.feature` | 1, during publication | `external.spec.js` |

The normal suite has 117 scenarios per engine, or 351 across Chrome, Firefox and WebKit. The gallery outlines name every route in the pinned showcase manifest.

## What is verified by `external.feature`

The publication flow copies the examples into an independent directory and rebuilds with an empty Maven cache. It runs `ExternalStepsTest` in each browser using:

```sh
-Ddomino.external.root=/path/to/copied/examples
```

This checks the published artifact path separately from the local source checkout.

## Results and evidence

JUnit XML is written to each module's `target/surefire-reports-<engine>` directory.
To keep Chrome and Firefox reports separate, pair the report command with:

```sh
-Dbrowser.engine=firefox -Dteavm.junit.js.runner=browser-firefox
```

Use the Maven `record-results` goal to aggregate verified reports and source metadata:

```sh
mvn -N io.instanto:domino-build-maven-plugin:0.1.0-SNAPSHOT:record-results -Ddomino.mode=development
```

It writes them under `reports/`, which is not committed. CI uploads the raw XML
along with machine-readable result metadata.

## Focused validation scope

Current coverage is split by mechanism:

- `webapp-testkit` drives DOM-level interaction checks in Chrome and Firefox.
- Playwright drives WebKit interaction checks.
- `platform-checks` adds native scrolling and gallery presentation checks.

Additional validation targets:

- Media playback and device permission behaviour.
- Upload retry, error and cancellation flows.
- Visual quality and accessibility review.
