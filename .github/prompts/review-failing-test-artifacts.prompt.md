---
name: "Review Failing Test Artifacts"
description: "Review failing Selenium/TestNG test artifacts, logs, reports, screenshots, and surefire output through Master Automatin Agent without editing code by default."
argument-hint: "Describe the failing test, symptom, likely related changes, and any focus area; freeform context is also fine"
agent: "Master Automatin Agent"
tools: [read, search, execute]
---
Related skill: artifact-triage.

This prompt enters through `Master Automatin Agent` and is expected to route internally to `Test Failure Diagnosis (Read Only)` for read-only triage.

Use .github/workflow/Framework.md as the source of truth and investigate the failing test artifacts before proposing code changes.

Requirements:
- Start with read-only triage of reports, logs, screenshots, surefire output, and relevant test code.
- Do not edit files unless the user explicitly asks for a fix after the review.
- Correlate the observed failure with the relevant test, page locator, utility, listener, or config area.
- Prefer concrete evidence from logs, stack traces, screenshots, report output, or current code over speculation.
- Summarize likely root cause, confidence level, affected files or components, and the smallest safe next fix.

Suggested input template:
- Failing test name:
- Error message or symptom:
- Recent related changes:
- Focus areas for the review:

Freeform notes are still allowed when the failure context does not fit the template cleanly.

Failure context:
{{input}}