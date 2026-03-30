---
name: "Map Changes To Targeted Verification"
description: "Review the current changed files or a user-specified change set and recommend the smallest targeted Maven or TestNG verification for this framework."
argument-hint: "Describe the changed files, branch diff, or verification goal; include browser or environment constraints if relevant"
agent: "Master Automatin Agent"
tools: [read, search, execute]
---
Related skills: verification-impact-mapper, targeted-test-verification, environment-config-checker.

Use .github/workflow/Framework.md as the source of truth and map code changes to the narrowest defensible verification plan.

Requirements:
- Start from the current diff or the files named by the user.
- Recommend the smallest useful Maven or TestNG command first, then optional broader follow-up coverage only if risk justifies it.
- Call out which changed files are high-risk because they affect shared utilities, driver lifecycle, listeners, reporting, config, or suite wiring.
- Prefer concrete verification recommendations such as a class, method, group, suite, or browser-specific run over generic full-suite advice.
- Stay read-only and return rationale for each recommended verification target.

Change context:
{{input}}