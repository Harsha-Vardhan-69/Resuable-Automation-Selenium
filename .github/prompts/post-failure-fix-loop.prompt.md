---
name: "Post-Failure Fix Loop"
description: "Review a failing Selenium/TestNG run, implement the smallest safe fix, and rerun focused verification in this framework."
argument-hint: "Describe the failing test, latest report folder, current symptom, or whether the newest failure run should be used"
agent: "Master Automatin Agent"
---
Related skills: artifact-triage, targeted-test-verification, locator-wait-stabilization, verification-impact-mapper.

Use .github/workflow/Framework.md as the source of truth and move from evidence to the smallest verified fix without widening scope unnecessarily.

Requirements:
- Start from the most relevant failure evidence first, such as surefire output, report artifacts, screenshots, logs, or the specific failing test named by the user.
- Diagnose the likely root cause before editing any code.
- Keep the fix limited to the direct failure path unless shared framework risk clearly requires a broader change.
- Re-run the narrowest useful Maven or TestNG verification first, then expand only when the changed area justifies it.
- If the issue is environment-only, third-party-site drift, or missing evidence rather than a clear code defect, state that explicitly instead of forcing a code change.
- Summarize root cause, changed files, verification run, and any remaining risk.

Failure loop context:
{{input}}