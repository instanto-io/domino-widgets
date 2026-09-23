# Verification evidence

The [development summary](development/summary.json) records the last complete
three-browser TeaVM run, before the colour, icon and theme pages were added:

- 66 original showcase pages containing 187 sample methods.
- 108 shared Cucumber scenarios in each of Chrome, Firefox and WebKit: 324 checks,
  with no failures or skips.
- The new application, flex and grid layouts and complete form examples are included.

The expanded local gallery now has 69 original pages and 190 sample methods.
Its 114 shared scenarios pass in Chromium and WebKit. A fresh Firefox run needs
a host with Firefox installed at the path expected by TeaVM's browser runner;
these results have not yet replaced the recorded three-browser summary.

[Browser results](development/browser.json) list the individual scenarios. Raw
JUnit reports are under `development/junit`; generated-source hashes are in
[generated-sha256.json](development/generated-sha256.json). The summary also records
compiler versions, JavaScript size and static-analysis results. JavaScript size
is the uncompressed bundle size, not a startup benchmark. Firefox checks ran in
fresh browser processes for the long gallery suite because a single extended
local session exhausted the browser runner's memory.

The [production summary](production/summary.json) records an earlier optimised
TeaVM build:

- 62 original showcase pages containing 182 sample methods.
- 99 shared Cucumber scenarios in each of Chrome, Firefox and WebKit: 297 checks,
  with no failures or skips.
- 14 advanced table scenarios covering editing, validation, grouping, record
  details, row menus, summaries, scrolling, tree grids, selection and drag and drop.

This production run predates the latest layouts, form examples, and compatibility
snapshot. It needs to be repeated before a release.

## Reproduce the checks

Follow the [build instructions](../docs/DEVELOPMENT.md) and
[browser test guide](../docs/TESTING.md). After all three engines pass against the
same compiled site, record the results with:

```sh
mvn -N io.instanto:domino-build-maven-plugin:0.1.0-SNAPSHOT:record-results -Ddomino.mode=production
```

The goal rejects missing, failed or skipped scenarios and differences between
browser suites. Native wheel and touch tests and external application checks run
separately; the shared scenario count does not certify those behaviours.

## Other records

Older publication, hosted-site and migration reports retain earlier verification
evidence. They do not establish that the current local changes have been
published or tested in those environments.
