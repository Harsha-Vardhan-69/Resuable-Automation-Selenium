# Agent User Guide

## Overview

This repository uses a structured AI workflow under `.github/` so framework maintenance, test authoring, failure triage, review, and documentation work can be routed to the correct agent with the correct guardrails.

This guide is the human-friendly summary and entry point. The maintained source of truth for framework structure and agent workflow is `.github/workflow/Framework.md`.

## Where The Workflow Lives

- `.github/workflow/Framework.md` - primary maintained source of truth for the repo AI workflow
- `.github/workflow/agent-user-guide.md` - human-readable summary of agents, prompts, skills, and when to use them
- `.github/agents/` - one visible coordinator agent plus hidden specialist subagents
- `.github/copilot-instructions.md`
- `.github/workflow/tasks.md`
- `.github/workflow/changeLog.md`
- `.github/workflow/suggestions.md`
- `.github/prompts/`
- `.github/skills/`
- `.github/instructions/`
- `.github/hooks/`

## How To Use This Guide

- Start with `.github/workflow/Framework.md` when you need the canonical workflow or repo structure.
- Use this file when you want a quick routing answer such as which agent, prompt, or skill should be used.
- Update this file when the workflow surface changes, such as adding a new agent, prompt, or skill.

## Using Prompt Templates

- Use the suggested prompt template fields when your request maps cleanly to them and you want the fastest structured routing.
- Use freeform input when the context is exploratory, spans multiple concerns, or does not fit the template fields naturally.
- Combining both is fine: give the key template fields first, then add any extra context below them.

## Top Prompts To Use First

If you are not sure where to start, these prompts cover the highest-value day-to-day workflows first.

- `.github/prompts/post-failure-fix-loop.prompt.md` for turning a failing run into the smallest verified fix.
- `.github/prompts/map-changes-to-targeted-verification.prompt.md` for deciding the narrowest safe Maven or TestNG verification for the current diff.
- `.github/prompts/create-test-from-step-file.prompt.md` for turning manual steps or txt instructions into automated coverage.
- `.github/prompts/review-framework-changes.prompt.md` for read-only diff review before merge.
- `.github/prompts/summarize-latest-failure-run.prompt.md` for a one-page summary of the latest failure evidence.
- `.github/prompts/propose-page-smoke-coverage.prompt.md` when coverage shape is unclear and you want a smoke plan before implementation.

## Visible Entry Agent

### Master Automatin Agent

Use this agent for:

- Mixed requests that span diagnosis, implementation, review, and documentation
- Requests where you do not want to pick the specialist agent yourself
- Multi-step workflows that should be split, handed off, and merged cleanly

Expect this agent to:

- Read the workflow guide first
- Route to the right specialist subagent or sequence of subagents
- Use the handoff ledger in `.github/workflow/tasks.md` when work spans multiple steps or sessions
- Merge the result into one user-facing answer
- Keep specialist agents out of the normal picker and use them internally

## Internal Specialist Subagents

These specialist agents are internal subagents used by the coordinator or by prompt entry points. They are not intended to be the main user-visible picker options.

### Automation Framework Maintainer (Read & Write)

Use this agent for:

- Shared framework code changes
- Driver, listener, reporting, config, utility, and other cross-cutting updates
- Tracked tasks from `.github/workflow/tasks.md` when actionable entries exist

Expect this agent to:

- Read framework guidance first
- Work one task at a time
- Make tightly scoped changes
- Verify with Maven when practical
- Hand routine new-test authoring to `Automated Test Author (Read & Write)`

### Automated Test Author (Read & Write)

Use this agent for:

- Creating a new Selenium/TestNG test from scenario text, manual execution notes, or a txt step file
- Modifying an existing test to reflect updated behavior or acceptance criteria
- Adding or extending the smallest supporting page-flow logic needed for a test

Expect this agent to:

- Read the framework guide first
- Reuse existing page flows, waits, locators, and data providers
- Verify with the smallest relevant Maven or TestNG command
- Hand off read-only failure diagnosis or doc-only follow-up to the existing specialist agents

### Test Failure Diagnosis (Read Only)

Use this agent for:

- Failed run review
- Flaky test analysis
- Log, screenshot, report, and surefire artifact triage

Expect this agent to:

- Start read-only
- Work from evidence before suggesting changes
- Return findings, confidence, and the smallest safe next fix

### Code Change Reviewer (Read Only)

Use this agent for:

- Reviewing current diffs or changed files before merge
- Finding regressions, risky framework changes, and missing verification
- Read-only code review of Java, TestNG, config, reporting, or workflow changes

Expect this agent to:

- Stay read-only
- Return findings first, not summaries first
- Call out verification gaps on shared framework changes

### Project Docs Maintainer (Read & Write)

Use this agent for:

- README updates
- CONTRIBUTING updates
- `.github/agents/*.md` updates
- Prompt and workflow documentation cleanup

Expect this agent to:

- Fix path drift and outdated workflow text
- Avoid Java or runtime changes
- Keep docs concise and aligned with the repo

## Prompt Library

### Work Tracking And Maintenance

- `.github/prompts/work-next-framework-task.prompt.md`
- `.github/prompts/maintain-framework-component.prompt.md`
- `.github/prompts/groom-framework-task-ledger.prompt.md`
- `.github/prompts/reconcile-task-ledger-and-change-log.prompt.md`

### Review And Failure Analysis

- `.github/prompts/review-failing-test-artifacts.prompt.md`
- `.github/prompts/summarize-latest-failure-run.prompt.md`
- `.github/prompts/post-failure-fix-loop.prompt.md`
- `.github/prompts/review-flaky-test.prompt.md`
- `.github/prompts/review-framework-changes.prompt.md`
- `.github/prompts/map-changes-to-targeted-verification.prompt.md`
- `.github/prompts/review-locator-quality.prompt.md`

### Test Authoring And Coverage

- `.github/prompts/create-framework-test.prompt.md`
- `.github/prompts/create-test-from-step-file.prompt.md`
- `.github/prompts/create-page-flow.prompt.md`
- `.github/prompts/add-website-smoke-coverage.prompt.md`
- `.github/prompts/propose-page-smoke-coverage.prompt.md`
- `.github/prompts/add-data-driven-test.prompt.md`
- `.github/prompts/group-manual-regression-into-testng.prompt.md`
- `.github/prompts/stabilize-locator-or-wait.prompt.md`
- `.github/prompts/verify-specific-test-class.prompt.md`

### Documentation

- `.github/prompts/sync-framework-docs.prompt.md`

## Recommended Prompt By Task

| Task | Recommended prompt | Specialist path |
| --- | --- | --- |
| Route a mixed or ambiguous workflow request | no specific prompt required | Coordinator only via `Master Automatin Agent` |
| Reconcile tasks.md and changeLog.md after tracked work | `.github/prompts/reconcile-task-ledger-and-change-log.prompt.md` | `Project Docs Maintainer (Read & Write)` via `Master Automatin Agent` |
| Summarize the latest failing run | `.github/prompts/summarize-latest-failure-run.prompt.md` | `Test Failure Diagnosis (Read Only)` via `Master Automatin Agent` |
| Move from a failing run to a smallest verified fix | `.github/prompts/post-failure-fix-loop.prompt.md` | `Automation Framework Maintainer (Read & Write)` via `Master Automatin Agent` |
| Decide what to verify for the current diff | `.github/prompts/map-changes-to-targeted-verification.prompt.md` | `Code Change Reviewer (Read Only)` via `Master Automatin Agent` |
| Review locator quality before flakiness spreads | `.github/prompts/review-locator-quality.prompt.md` | `Code Change Reviewer (Read Only)` via `Master Automatin Agent` |
| Propose smoke coverage for a page or small site area | `.github/prompts/propose-page-smoke-coverage.prompt.md` | `Automated Test Author (Read & Write)` via `Master Automatin Agent` |
| Convert repeated manual regression into grouped automation | `.github/prompts/group-manual-regression-into-testng.prompt.md` | `Automated Test Author (Read & Write)` via `Master Automatin Agent` |
| Create a test from step text or a txt file | `.github/prompts/create-test-from-step-file.prompt.md` | `Automated Test Author (Read & Write)` via `Master Automatin Agent` |
| Run one focused verification command | `.github/prompts/verify-specific-test-class.prompt.md` | `Automation Framework Maintainer (Read & Write)` via `Master Automatin Agent` |
| Sync docs after workflow or framework changes | `.github/prompts/sync-framework-docs.prompt.md` | `Project Docs Maintainer (Read & Write)` via `Master Automatin Agent` |

## Skills

- `.github/skills/step-file-test-authoring/`
- `.github/skills/website-smoke-coverage/`
- `.github/skills/locator-wait-stabilization/`
- `.github/skills/data-driven-test-authoring/`
- `.github/skills/targeted-test-verification/`
- `.github/skills/verification-impact-mapper/`
- `.github/skills/artifact-triage/`
- `.github/skills/page-flow-pattern-builder/`
- `.github/skills/window-tab-navigation/`
- `.github/skills/reporting-context-debugger/`
- `.github/skills/environment-config-checker/`
- `.github/skills/framework-doc-drift-check/`
- `.github/skills/task-ledger-manager/`

Use these skills for repeatable workflows that benefit from stronger task-specific guidance than a one-off prompt alone.

## File-Specific Instructions

The instruction files under `.github/instructions/` auto-load when matching files are edited.

- `java-framework.instructions.md` for shared framework Java code
- `test-authoring.instructions.md` for test classes
- `page-locators.instructions.md` for locator and page-flow files
- `build-config.instructions.md` for `pom.xml` and `src/test/resources/testng.xml`

These instructions enforce the local rules for stability, minimal change scope, robust locators, and verification.

## Hooks

The workflow uses hooks defined in `.github/hooks/copilot-hooks.json`.

### Session Start

Adds a reminder to use the framework guide and tracked workflow files.

### Pre Tool Use

Flags risky edits to shared framework files such as:

- `pom.xml`
- `src/test/resources/testng.xml`
- `ConfigLoader.java`
- `WebDriverFactory.java`
- `TestListener.java`
- `ExtentReport.java`

### Post Tool Use

Reminds the agent to run targeted verification after Java or suite-related edits.

## Recommended Daily Workflow

### For Framework Changes

1. Start with `Master Automatin Agent` or a prompt that routes through it.
1. Point to `.github/workflow/tasks.md` if the work is tracked and the file contains real open tasks rather than reference templates.
1. Let the agent inspect code first.
1. Expect minimal edits and targeted verification.

### For Failure Review

1. Use `Master Automatin Agent` or a review prompt that routes through it.
2. Provide test names, symptoms, and artifact locations.
3. Review findings before asking for a fix.

### For Documentation Updates

1. Use `Master Automatin Agent` or the docs prompt that routes through it.
2. Specify which behavior or workflow changed.
3. Sync the affected docs.

## Example Requests

- Use Master Automatin Agent to fix the next task from `.github/workflow/tasks.md`.
- Run the reconcile-task-ledger-and-change-log prompt when tracked work is done and you want to verify `.github/workflow/tasks.md` and `.github/workflow/changeLog.md` stay aligned.
- Use Master Automatin Agent to decide whether the next request should be diagnosed, implemented, reviewed, or documented first.
- Run the create-framework-test prompt to create a new login or checkout test from a plain-text execution script.
- Run the review-framework-changes prompt to review the current framework diff before merge.
- Run the map-changes-to-targeted-verification prompt to decide what Maven command should run for the current diff.
- Run the sync-framework-docs prompt to sync README with the current prompt workflow.
- Run the create-page-flow prompt for agency search navigation.

## New Prompt Examples

### Summarize Latest Failure Run

- Run the summarize-latest-failure-run prompt and focus on the newest failure for `TC02_HanoverAvailabilityJourneyTest`.
- Run the summarize-latest-failure-run prompt and compare the latest `surefire-reports` stack trace with the latest `Reports/Run_*` screenshots.

### Map Changes To Targeted Verification

- Run the map-changes-to-targeted-verification prompt for changes in `WebDriverFactory.java`, `ConfigLoader.java`, and `testng.xml`.
- Run the map-changes-to-targeted-verification prompt for the current diff and recommend the smallest Maven commands for Chrome and Edge coverage.

### Review Locator Quality

- Run the review-locator-quality prompt for the Hanover website locator files and flag the selectors most likely to become flaky.
- Run the review-locator-quality prompt for `SearchFlowPage` and suggest stronger alternatives for text-based or position-based selectors.

### Propose Page Smoke Coverage

- Run the propose-page-smoke-coverage prompt for `https://www.hanover.com` and suggest 3-5 high-value smoke scenarios.
- Run the propose-page-smoke-coverage prompt for the login area and include screenshot checkpoints plus any risky external-link or new-tab behavior.

### Group Manual Regression Into TestNG

- Run the group-manual-regression-into-testng prompt for the repeated login and logout regression notes in the shared txt file.
- Run the group-manual-regression-into-testng prompt and convert the current manual checkout regression list into focused TestNG groups with minimal duplication.

## Quick Decision Guide

- Shared framework internals or cross-cutting fixes: use `Master Automatin Agent`; internal route is `Automation Framework Maintainer (Read & Write)`.
- New or changed tests from scenario steps: use `Master Automatin Agent`; internal route is `Automated Test Author (Read & Write)`.
- Diagnosis before changes: use `Master Automatin Agent`; internal route is `Test Failure Diagnosis (Read Only)`.
- Diff or change review without edits: use `Master Automatin Agent`; internal route is `Code Change Reviewer (Read Only)`.
- Only markdown or workflow docs change: use `Master Automatin Agent`; internal route is `Project Docs Maintainer (Read & Write)`.

## Maintenance Rule

- When workflow behavior changes, update `.github/workflow/Framework.md` first.
- Then update this guide so the summary stays aligned with the source of truth.
- If a prompt, skill, or agent is added or removed, reflect that change here in the same edit when practical.

## Important Notes

- `.github/` is the active home for AI workflow assets.
- `.github/workflow/Framework.md` is the canonical workflow file to keep maintained.
- `.github/workflow/agent-user-guide.md` should stay shorter and easier to scan than the framework guide.
- `.github/agents/` should expose only the coordinator in normal agent selection; the specialist `.agent.md` files are hidden subagents.
- Prioritize wording cleanup and intake consistency before adding many more prompts.
- If docs and code disagree, update the docs as part of the same change when practical.
- For tracked work, record meaningful changes in `.github/workflow/changeLog.md`.
