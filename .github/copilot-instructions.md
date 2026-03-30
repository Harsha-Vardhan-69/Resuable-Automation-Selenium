# Project Guidelines

## Scope
- This repository is a Selenium WebDriver, Java, TestNG, and Maven web automation framework.
- For mixed or multi-step requests where routing is unclear, prefer the `Master Automatin Agent` custom agent.
- For framework maintenance work, prefer the `Automation Framework Maintainer (Read & Write)` custom agent.
- For artifact review or flaky test analysis, prefer the `Test Failure Diagnosis (Read Only)` custom agent.
- For README, CONTRIBUTING, or agent-workflow docs, prefer the `Project Docs Maintainer (Read & Write)` custom agent.
- Treat `.github/workflow/Framework.md` as the implementation source of truth for framework structure and expectations.

## Workflow
- Work one task at a time and verify before moving to the next task.
- Use `.github/workflow/tasks.md` as a handoff ledger for larger or ongoing tracked workstreams, and update `.github/workflow/changeLog.md` when meaningful tracked work completes.
- Make the smallest safe change that solves the root cause; avoid broad refactors unless explicitly requested.
- Read the relevant page objects, utilities, listeners, and config classes before editing.
- For review requests, inspect reports, logs, screenshots, and relevant code before proposing edits.
- Treat `pom.xml`, `src/test/resources/testng.xml`, driver lifecycle classes, listeners, reporting hooks, and shared config loaders as high-risk files.

## Build And Test
- Use Maven for verification.
- Prefer the smallest relevant command first, then broader coverage when needed.
- Common commands:
  - `mvn -q test`
  - `mvn -q clean test`
  - `mvn -q clean test -Dbrowser=chrome`
  - `mvn -q clean test -Dbrowser=edge`

## Framework Conventions
- Prefer explicit waits and reusable wait utilities over `Thread.sleep`.
- Keep locators robust and page objects readable.
- Preserve driver lifecycle behavior, TestNG listeners, and Extent report integration.
- Keep tests deterministic, isolated, and environment-aware.
- Follow existing project structure:
  - `src/main/java/com/framework/...` for framework code
  - `src/test/java/com/framework/pageLocators/` for current locator/page classes
  - `src/test/java/com/framework/tests/` for test classes

## Documentation
- Keep README and CONTRIBUTING aligned with actual framework behavior when changes affect setup, execution, or contribution flow.
- If a repo doc conflicts with the code, fix the doc as part of the same change when practical.
- Prefer `.github/workflow/`, `.github/agents/`, `.github/prompts/`, `.github/instructions/`, and `.github/hooks/` as the source of truth for agent workflow assets.