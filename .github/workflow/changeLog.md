# Change Log

## 2026-03-30

- Added the first real coordinator-era closed workstream example to `.github/workflow/tasks.md`, showing a completed Master Automatin Agent-led workflow across review, prompt cleanup, validation, and workflow-doc updates, and cleared the matching deferred note from `temp/todo-later.txt`.
- Added a dedicated `reconcile-task-ledger-and-change-log` prompt and linked it in the workflow docs as the focused path for reconciling `.github/workflow/tasks.md` with `.github/workflow/changeLog.md` after tracked work completes.

## 2026-03-27

- Aligned user-facing workflow docs to the coordinator-only model so `Master Automatin Agent` is the public entry point and specialist agents are described only as internal routing paths.
- Updated core workflow prompts to describe coordinator-first routing and added lightweight structured input templates for framework maintenance, failure review, test authoring, and docs while preserving freeform input.
- Added workflow rules so tracked tasks marked `done` must be checked against `.github/workflow/changeLog.md`, plus a deferred todo note for adding the first real coordinator-era ledger example.

## 2026-03-26

- Implemented agent workflow roadmap phases 1-5.
- Added focused custom agents for failure triage and documentation maintenance.
- Added file-specific instructions for framework Java, tests, locators, and build configuration.
- Added hooks for session context, risky file edit review, and post-edit verification reminders.
- Expanded the prompt library for task grooming, test creation, page-flow work, flaky-test review, and doc synchronization.
- Updated README, CONTRIBUTING, Agent.md, and framework workflow docs to make `.github/` the source of truth for agent customization assets.
- Added a quick-reference cheat sheet, onboarding guide, and markdown user guide for the new agent workflow.
- Removed the obsolete workflow roadmap, marked the task template as inactive, aligned docs with the current suite and parallelism configuration, and fixed remaining naming/path drift.
- Replaced the placeholder default suite test with a real login-page smoke flow and updated the suite/documentation references accordingly.
- Added fail-fast runtime configuration validation, enabled config-file-driven default environment selection, and verified the smoke test without terminal env overrides.
- Switched CSV and Excel readers to classpath resource loading, updated data providers to use resource names, and added a focused test to verify classpath-based data access.
- Moved Log4j2 framework log output into the active per-run report folder, added minimal framework logging events, and verified the generated log file under the run folder with a focused reporting test.
- Added explicit cleanup for per-test Extent and screenshot context in the listener lifecycle and verified that success and failure paths clear stale thread-local state.
- Replaced TestDataProviders console debugging with configurable Log4j debug logging and verified the provider diagnostics in the per-run framework log.
- Replaced report-path system-property coordination with a shared ReportContext abstraction and verified report, screenshot, source, and failure-log paths resolve under the active run folder without relying on `reports.runFolder` or `current.screenshot.folder`.
- Refactored `SearchFlowPage` into a worked page-flow example, updated `TC03_GoogleSearchYoutubeTest` to use framework-level steps, and aligned the default TestNG suite and docs to that example flow.
- Optimized `ElementUtil` by centralizing repeated wait/log behavior, adding reusable type and stale-safe click helpers, and adding hover/frame helpers for more complex UI flows.
- Added a small multi-tab/window helper set to `ElementUtil` with optional reporting, plus focused proxy-based tests for new-tab switching, handle restoration, URL/title-based switching, and title validation.
- Moved browser test flow details into a test-scope `TestElementUtil` so the real UI TCs stay as pure `e.method()` calls while keeping scenario-specific locators out of shared main-framework utilities.
- Retired the legacy Internet Explorer branch from `WebDriverFactory`, made Chrome and Edge fallback startup preserve browser options like headless mode, documented the supported browser policy, and added focused tests for the browser policy helpers.
- Changed `TestDataProviders` to fail fast on mismatched multi-sheet row counts or `DataSet` keys instead of silently truncating data, added focused tests for those failure modes, and refreshed `suggestions.md` with the remaining framework review items.
- Stopped suppressing framework listener/reporting failures by surfacing listener lifecycle and report-directory errors as explicit framework exceptions, added focused failure-visibility tests, and re-ranked the remaining suggestions backlog.
- Preserved screenshot failure root causes by retaining the underlying capture error in `ScreenshotUtil`, surfacing that detail through `ExtentReport`, adding focused screenshot-failure tests, and removing that item from the remaining suggestions backlog.
- Normalized direct navigation URLs through `ConfigLoader` so `ElementUtil` accepts bare hostnames for direct and new-tab navigation, added focused proxy-based utility coverage, and cleared the final remaining framework suggestion.
- Cleaned the test-side layout by moving framework self-checks into a dedicated test package, replacing the old portfolio tests with thin `e.method()` scenario tests, splitting portfolio locators into one file per page, and validating the updated scenario plus framework-check coverage together.
- Updated the checked-in TestNG suite to run the thin portfolio scenario tests by default and aligned the README and framework guide with that suite behavior.
- Introduced a coordinator-first agent workflow with `Master Automatin Agent` as the only visible entry point, converted the specialist agents into hidden internal subagents, routed prompt entry points through the coordinator, and upgraded `tasks.md` into a workflow handoff ledger for cross-agent continuation.
