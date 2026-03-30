# Contributing to Lynx Automation Framework

Thank you for your interest in contributing! Please follow these guidelines to help us maintain a high-quality, robust automation framework.

## Getting Started

- Fork the repository and clone your fork.
- Install dependencies with `mvn clean install`.
- Review the project structure in `.github/workflow/Framework.md`.

## Code Guidelines

- Use descriptive test method names and add Javadoc comments.
- Group tests using TestNG `@Test(groups = { ... })` for smoke, regression, etc.
- Follow the Page Object Model for UI interactions.
- Use data-driven approaches for test data.
- Avoid hardcoding sensitive data; use environment variables or config files.
- Prefer explicit waits and stable locators; avoid `Thread.sleep`.

## Adding tests and page objects

- Tests: `src/test/java/com/framework/tests/`
- Locator/page-flow classes: `src/test/java/com/framework/pageLocators/`
- Shared utilities/config/driver: `src/main/java/com/framework/`

## Running locally

```sh
mvn -q clean test
mvn -q test
mvn -q test -Dgroups=smoke
mvn -q clean test -Denv=uat
```

PowerShell (override config keys via env vars):

```powershell
$env:BROWSER = "chrome"   # or edge
$env:HEADLESS = "false"
mvn -q clean test
```

Browser support policy:

- Supported browsers are `chrome` and `edge`.
- Legacy Internet Explorer paths are not maintained in this framework.
- If WebDriverManager cannot provision a driver, fallback local binaries in `drivers/` still use the same browser options, including headless mode.

## Agent Workflow

- Use `Master Automatin Agent` as the default visible entry point when a request spans more than one workflow phase.
- Use prompts or direct requests through `Master Automatin Agent`; it routes framework implementation tasks internally to `Automation Framework Maintainer (Read & Write)`.
- Use prompts or direct requests through `Master Automatin Agent`; it routes log, screenshot, report, or flaky-test review internally to `Test Failure Diagnosis (Read Only)` before editing code.
- Use prompts or direct requests through `Master Automatin Agent`; it routes documentation-only updates internally to `Project Docs Maintainer (Read & Write)`.
- Use prompt files in `.github/prompts/` for repeatable workflows.
- Treat `.github/workflow/Framework.md` as the source of truth for the customization layout.
- Treat template-only entries in `.github/workflow/tasks.md` as examples, not active work items.
- Keep handoff-ledger fields current when work is passed between agents or resumed later.

## Review checklist

- Locators: prefer stable attributes; avoid brittle XPath
- Waits: explicit waits (no sleeps)
- Assertions: validate outcomes, not just clicks
- Test independence: no ordering assumptions
- Reporting: ensure Extent hooks remain intact

## Submitting Changes

- Create a new branch for your feature or bugfix.
- Ensure all tests pass locally before submitting a pull request.
- Update documentation (`README.md`, `.github/workflow/Framework.md`) as needed.
- Update agent workflow docs if the customization layout or usage changes.
- Add or update tests for new features or bug fixes.
- Summarize your changes in the pull request description.

## Review Process

- All pull requests are reviewed by maintainers.
- Address any requested changes before merging.

## Reporting Issues

- Use the issue tracker to report bugs or request features.
- Provide as much detail as possible, including steps to reproduce.

---

- Last updated: 2026-03-26
