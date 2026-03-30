---
name: artifact-triage
description: 'Use for read-only triage of surefire output, Extent reports, screenshots, logs, page sources, and flaky Selenium/TestNG failure evidence before code changes.'
argument-hint: 'Describe the failing test, artifact folder, or observed symptom'
---

# Artifact Triage

## When to Use
- A failure needs diagnosis before a fix is written.
- Reports, screenshots, or surefire logs are the main evidence.
- The task is flaky-test review or artifact-based root-cause analysis.

## Procedure
1. Start with reports, screenshots, logs, stack traces, and page sources.
2. Map symptoms to likely framework areas such as locator, wait, environment, reporting, data, or driver lifecycle.
3. Inspect only the code directly related to the observed failure path.
4. State the most likely root cause, confidence, and the smallest safe next fix.
5. Stay read-only until the user asks for implementation.

## Checks
- Prefer evidence over speculation.
- Call out missing artifacts or confidence gaps.
- Do not propose broad refactors from thin evidence.