---
name: "Review Flaky Test"
description: "Review a flaky Selenium/TestNG test using reports, logs, screenshots, waits, and related framework code before proposing a fix."
argument-hint: "Describe the flaky test, observed pattern, or artifacts to inspect"
agent: "Master Automatin Agent"
---
Related skill: artifact-triage.

Investigate the flaky test using reports, screenshots, logs, and the relevant test or utility code.

Requirements:
- Start read-only.
- Prefer evidence over speculation.
- Identify whether the issue is likely a locator, wait, data, environment, concurrency, or framework lifecycle problem.
- Recommend the smallest safe next fix.

Failure context:
{{input}}