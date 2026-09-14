# Verification evidence

The [production summary](production/summary.json) records the clean, optimised
TeaVM build and its browser checks:

- 62 original showcase pages containing 182 sample methods.
- 99 shared Cucumber scenarios in each of Chrome, Firefox and WebKit: 297 checks,
  with no failures or skips.
- 14 advanced table scenarios covering editing, validation, grouping, record
  details, row menus, summaries, scrolling, tree grids, selection and drag and drop.

[Browser results](production/browser.json) list the individual scenarios. Raw
JUnit reports are under `production/junit`; generated-source hashes are in
[generated-sha256.json](production/generated-sha256.json). The summary also records
compiler versions, JavaScript size and static-analysis results. JavaScript size
is the uncompressed bundle size, not a startup benchmark.

This run used the existing parent POM configuration. The separate organisation-POM
migration was not included. Firefox used the cached browser test runtime through
TeaVM's browser endpoint because the default Firefox application was absent.

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

The development directory and older publication, hosted-site and migration
reports retain earlier verification evidence. They do not establish that the
current local changes have been published or tested in those environments.
