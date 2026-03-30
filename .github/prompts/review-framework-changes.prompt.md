---
name: "Review Framework Changes"
description: "Review the current framework diff or a specified set of changed files for bugs, regressions, risk, and missing verification without editing code."
argument-hint: "Describe the diff scope, changed files, review focus, and any risk area to prioritize; freeform context is also fine"
agent: "Master Automatin Agent"
---
Use .github/workflow/Framework.md as the source of truth and review the current framework changes in read-only mode.

Requirements:
- Focus on bugs, regressions, behavioral drift, flaky-test risk, and missing verification.
- Findings come first, ordered by severity.
- Keep summaries brief and evidence-based.
- Do not edit files as part of this review prompt.

Suggested input template:
- Diff scope or changed files:
- Main review focus:
- Highest-risk areas:
- Verification concerns to check:

Use the template when the request fits naturally; use freeform notes when the review request spans multiple concerns.

Review scope:
{{input}}