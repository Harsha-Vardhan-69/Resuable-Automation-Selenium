# Framework Structure

## Overview

This project is a Java-based Selenium Test Automation Framework using TestNG, Maven, and ExtentReports. It uses shared utilities plus locator/page-flow classes, and the checked-in default suite now runs the thin scenario tests `TC00_LoginSmokeTest`, `TC01_FramewokBasicTest`, `TC02_HanoverAvailabilityJourneyTest`, and `TC03_GoogleSearchYoutubeTest`.

## Directory Structure

``` Structure
Reuseable_AutomationFramework/
├── pom.xml                # Maven project descriptor
├── README.md              # Project overview and instructions for User
├── .github/
│   ├── copilot-instructions.md # Workspace-wide agent guidance
│   ├── agents/            # Custom agent definitions
│   ├── workflow/          # Framework workflow docs, guides, task ledger, and change log
│   │   ├── Framework.md   # Framework structure and workflow source of truth
│   │   ├── agent-user-guide.md # Quick routing guide for agents, prompts, and skills
│   │   ├── tasks.md       # Workflow handoff ledger and tracked maintenance work
│   │   ├── changeLog.md   # Change log for tracked workstreams
│   │   └── suggestions.md # Optional backlog or review suggestions
│   ├── hooks/             # Runtime hook configuration and scripts
│   ├── instructions/      # File-specific editing guidance
│   └── prompts/           # Reusable task prompts for common workflows
├── src/
│   ├── main/
│   │   ├── java/com/framework/
│   │   │   ├── config/    # Configuration loader
│   │   │   ├── data/      # Data providers and readers (CSV, Excel, JSON)
│   │   │   ├── driver/    # WebDriver factory and management
│   │   │   ├── listeners/ # TestNG listeners (reporting, hooks)
│   │   │   ├── reports/   # ExtentReports integration
│   │   │   └── utils/     # Utilities (ElementUtil, WaitUtil, ScreenshotUtil)
│   │   └── resources/     # Main resources (log4j2.xml, etc.)
│   └── test/
│       ├── java/com/framework/
│       │   ├── pageLocators/ # Locator and page-flow classes (Login, SearchFlow, etc.)
│       │   ├── tests/        # Thin scenario test classes (BaseTest, TC01_FramewokBasicTest, etc.)
│       │   └── utils/        # (Optional) Test-specific utilities
│       └── resources/        # Test data (CSV, Excel), config, testng.xml
├── Reports/                # Test execution reports and screenshots
├── logs/                   # Log output
├── drivers/                # WebDriver binaries (if not using WebDriverManager)
└── target/                 # Maven build output
```

## Key Components

- **TestNG**: Test orchestration, data providers, and reporting.
- **Selenium WebDriver**: Browser automation.
- **ExtentReports**: Rich HTML reporting and screenshots.
- **Page Object Model**: Encapsulates UI locators and actions.
- **Data-driven Testing**: Test data from CSV/Excel/JSON.
- **Utilities**: Common actions, waits, and screenshot helpers.

## How to Extend

- Add new scenario tests in `src/test/java/com/framework/tests/` and keep the test bodies thin.
- Add or update locator classes in `src/test/java/com/framework/pageLocators/`; for multi-page sites, prefer one locator file per page.
- Add new data providers/readers in `src/main/java/com/framework/data/`
- Update configuration in `src/test/resources/config.properties` or environment-specific files (see below)
- Add new utilities in `src/main/java/com/framework/utils/`

## Build & Run

- Build: `mvn clean install`
- Run all tests: `mvn test`
- Run suite: `mvn test -DsuiteXmlFile=src/test/resources/testng.xml`
- Run a group: `mvn test -Dgroups=smoke`
- Use a specific environment: `mvn test -Denv=uat`

## Reporting

- HTML reports: `Reports/Run_<timestamp>/Extent Reports/extent-report.html`
- Screenshots: `Reports/Run_<timestamp>/Screenshots/<TestCaseName>/`
- Framework log: `Reports/Run_<timestamp>/Logs/framework.log`
- Environment/configuration info is included in the report summary.

## Environment Profiles & Secrets Management

- Use `config.properties` for default config, or `config.uat.properties`, `config.prod.properties` for environment-specific settings.
- Select environment via `-Denv=uat` or `ENV=uat`.
- If no terminal override is provided, the framework falls back to the `env` key in `config.properties`.
- Sensitive values (e.g., passwords) can be set as environment variables (e.g., `LOGIN_PASSWORD`).
- The resolved `url` must be a real URL or hostname; placeholder values fail fast during startup.

## Browser Support Policy

- Supported browsers are Chrome and Edge.
- Driver initialization fails fast for unsupported browser values rather than silently routing legacy browser names.
- WebDriverManager remains the primary provisioning path; local binaries under `drivers/` are a fallback.

## Parallel Execution

- TestNG suite execution is non-parallel by default, while Maven Surefire is configured with multiple forks.

## TestNG Groups

- Tests are grouped (e.g., smoke, regression, login, agent, search) for selective execution.

## Agent Workflow

- Use the `Master Automatin Agent` custom agent as the single visible entry point for mixed, ambiguous, or multi-step requests.
- Treat the five specialist agents as internal subagents used by the coordinator or by prompt entry points rather than as the main user-visible picker options.
- Internally, the coordinator routes framework internals and cross-cutting fixes to `Automation Framework Maintainer (Read & Write)`.
- Internally, the coordinator routes new or changed test work to `Automated Test Author (Read & Write)`.
- Internally, the coordinator routes read-only artifact triage to `Test Failure Diagnosis (Read Only)`.
- Internally, the coordinator routes read-only diff or locator review to `Code Change Reviewer (Read Only)`.
- Internally, the coordinator routes documentation-only updates to `Project Docs Maintainer (Read & Write)`.
- Keep this file at `.github/workflow/Framework.md` as the maintained source of truth for workflow structure and routing.
- Keep `.github/workflow/agent-user-guide.md` as the shorter human-readable summary.
- Workspace-wide defaults live in `.github/copilot-instructions.md`.
- File-specific editing rules live in `.github/instructions/`.
- Deterministic safety hooks live in `.github/hooks/`.
- Use prompt files in `.github/prompts/` for common maintenance flows.
- For larger or ongoing workstreams, track progress in `.github/workflow/tasks.md` as a handoff ledger and update `.github/workflow/changeLog.md` when meaningful tracked work completes.

## Routing Matrix

Use this matrix to choose the default workflow path before starting work.

| Request type | Public entry | Preferred prompt | Specialist path | Notes |
| --- | --- | --- | --- | --- |
| Mixed, ambiguous, or multi-step request spanning diagnosis, edits, review, or docs | `Master Automatin Agent` | no single prompt required | coordinator with `task-ledger-manager`, `verification-impact-mapper` | Use as the visible front door when the request should be split and merged. |
| Shared utility, driver, listener, reporting, config, or other cross-cutting framework change | `Master Automatin Agent` | `.github/prompts/maintain-framework-component.prompt.md` | `Automation Framework Maintainer (Read & Write)` with `verification-impact-mapper`, `environment-config-checker`, `reporting-context-debugger` | Public entry stays the coordinator; internal route goes to framework maintenance. |
| Next tracked maintenance item from the ledger | `Master Automatin Agent` | `.github/prompts/work-next-framework-task.prompt.md` | `Automation Framework Maintainer (Read & Write)` with `task-ledger-manager`, `verification-impact-mapper` | Only when `.github/workflow/tasks.md` contains a real open task. |
| Reconcile tasks.md and changeLog.md after tracked work | `Master Automatin Agent` | `.github/prompts/reconcile-task-ledger-and-change-log.prompt.md` | `Project Docs Maintainer (Read & Write)` with `task-ledger-manager` | Use when completed tracked work may be missing a matching change log entry or the two files have drifted. |
| New or changed automated test from scenario text, manual notes, or step file | `Master Automatin Agent` | `.github/prompts/create-framework-test.prompt.md` or `.github/prompts/create-test-from-step-file.prompt.md` | `Automated Test Author (Read & Write)` with `step-file-test-authoring`, `data-driven-test-authoring` | Keep the change scoped to tests and the smallest supporting locators or flows. |
| New page-flow or locator-only structure work | `Master Automatin Agent` | `.github/prompts/create-page-flow.prompt.md` | `Automated Test Author (Read & Write)` with `page-flow-pattern-builder`, `locator-wait-stabilization` | Prefer locator-only classes when the repo pattern calls for it. |
| Read-only failing run triage from reports, logs, screenshots, or surefire output | `Master Automatin Agent` | `.github/prompts/review-failing-test-artifacts.prompt.md` or `.github/prompts/summarize-latest-failure-run.prompt.md` | `Test Failure Diagnosis (Read Only)` with `artifact-triage` | Start read-only and propose the smallest safe next fix. |
| Read-only current diff or changed-file review | `Master Automatin Agent` | `.github/prompts/review-framework-changes.prompt.md` | `Code Change Reviewer (Read Only)` with `verification-impact-mapper` | Findings first, then verification gaps or the smallest next fix. |
| Decide what Maven or TestNG verification should run for a diff | `Master Automatin Agent` | `.github/prompts/map-changes-to-targeted-verification.prompt.md` | `Code Change Reviewer (Read Only)` with `verification-impact-mapper`, `targeted-test-verification` | Use before widening into broad regression runs. |
| Locator quality review before failures spread | `Master Automatin Agent` | `.github/prompts/review-locator-quality.prompt.md` | `Code Change Reviewer (Read Only)` with `locator-wait-stabilization` | Stay read-only unless the user asks for implementation. |
| Propose or add website smoke coverage | `Master Automatin Agent` | `.github/prompts/propose-page-smoke-coverage.prompt.md` or `.github/prompts/add-website-smoke-coverage.prompt.md` | `Automated Test Author (Read & Write)` with `website-smoke-coverage`, `window-tab-navigation` | Use proposal mode first when coverage shape is still unclear. |
| Documentation-only workflow or framework-doc update | `Master Automatin Agent` | `.github/prompts/sync-framework-docs.prompt.md` | `Project Docs Maintainer (Read & Write)` with `framework-doc-drift-check` | Avoid Java, runtime, or suite edits in doc-only work. |

## Verification Policy

Use a smallest-first verification strategy. Start with the narrowest command that proves the changed behavior, then expand only when shared risk justifies it.

| Changed area | Minimum expected verification | Expand when |
| --- | --- | --- |
| One test class under `src/test/java/com/framework/tests/` | Run that class or a named method first | The change also touched shared locators, utilities, config, or listeners |
| Locator or page-flow class under `src/test/java/com/framework/pageLocators/` | Run the directly affected test class or smoke path | The locators are shared by multiple tests or a common navigation path |
| Test data provider or data-reader change | Run the directly affected data-driven test or focused framework check | The reader is shared across formats or multiple suites |
| Shared utility under `src/main/java/com/framework/utils/` | Run the closest affected scenario plus any focused framework checks for that utility | The utility is used across unrelated test areas or changes browser, tab, wait, or screenshot behavior |
| Driver lifecycle, listener, reporting, or config loader change | Run focused framework checks first, then at least one representative scenario test | The change is browser-specific, touches run-folder ownership, or alters setup and teardown behavior |
| `pom.xml` or `src/test/resources/testng.xml` | Run the smallest command that proves the changed build or suite wiring | The change affects groups, parallelism, forks, browsers, or default suite composition |
| Workflow docs, prompts, skills, or agent definitions only | Validate the changed markdown or customization files | The doc change also changes expected commands, paths, or workflow routing |

Verification rules:

- Prefer `mvn -q -Dtest=ClassName test` or an equivalent narrow command before broader suite runs.
- Use `.github/prompts/map-changes-to-targeted-verification.prompt.md` when the right verification scope is unclear.
- Treat `pom.xml`, `src/test/resources/testng.xml`, driver lifecycle classes, listeners, reporting hooks, and shared config loaders as high-risk.
- If a change affects browser-specific behavior, call out whether Chrome, Edge, or both should be exercised.
- If verification cannot be run locally, state exactly what should be run next and why that is the minimum safe coverage.

## Contribution

- See `CONTRIBUTING.md` for contribution guidelines.

---
