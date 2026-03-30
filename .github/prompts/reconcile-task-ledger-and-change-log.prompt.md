---
name: "Reconcile Task Ledger And Change Log"
description: "Review .github/workflow/tasks.md and .github/workflow/changeLog.md together through Master Automatin Agent and reconcile missing or unclear completion logging."
argument-hint: "Describe the reconciliation scope, specific workstream or date range, whether missing entries should be drafted automatically, and any workflow notes to preserve; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skill: task-ledger-manager.

This prompt enters through `Master Automatin Agent` and is expected to route internally to `Project Docs Maintainer (Read & Write)` for workflow-tracking reconciliation.

Use .github/workflow/Framework.md as the source of truth and compare .github/workflow/tasks.md with .github/workflow/changeLog.md before making any workflow-doc updates.

Requirements:
- Read .github/workflow/tasks.md and .github/workflow/changeLog.md together.
- Flag completed tracked items that appear to be missing a matching change log entry.
- Draft concise change log entries for missing tracked completions when the workflow context is clear.
- Preserve history and avoid rewriting unrelated completed entries.
- Do not change unrelated workflow guidance while reconciling tracking artifacts.
- Validate markdown after edits.

Suggested input template:
- Reconciliation scope:
- Specific workstream or date range:
- Draft missing entries automatically?:
- Workflow notes to preserve:

Freeform notes are still allowed when the reconciliation request does not fit the template cleanly.

Task:
{{input}}