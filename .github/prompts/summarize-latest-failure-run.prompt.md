---
name: "Summarize Latest Failure Run"
description: "Read the latest surefire-reports output and latest Reports/Run_* folder, then produce a concise one-page Selenium/TestNG failure summary without editing code."
argument-hint: "Optionally provide the failing test name, report folder hint, or any symptom to emphasize in the summary"
agent: "Master Automatin Agent"
tools: [read, search, execute]
---
Related skill: artifact-triage.

Use .github/workflow/Framework.md as the source of truth and summarize the latest failure evidence before proposing any code changes.

Requirements:
- Start by locating the newest relevant surefire output and the newest matching Reports/Run_* folder.
- Stay read-only unless the user explicitly asks for a fix after the summary.
- Summarize failed tests, main exception patterns, relevant screenshots or page sources, and the most likely affected framework areas.
- Keep the output to a one-page equivalent: concise, evidence-based, and easy to scan.
- End with confidence level and the smallest safe next fix or verification step.

Failure context:
{{input}}