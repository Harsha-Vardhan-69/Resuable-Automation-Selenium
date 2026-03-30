---
name: task-ledger-manager
description: 'Use for grooming .github/workflow/tasks.md and .github/workflow/changeLog.md, keeping the handoff ledger accurate, clarifying ownership and next steps, and preserving one-task-at-a-time execution clarity.'
argument-hint: 'Describe how the task ledger or change log should be reviewed or updated'
---

# Task Ledger Manager

## When to Use
- The tracked task list or handoff ledger needs cleanup or reprioritization.
- A change log entry should be added after a completed task.
- The workflow has become ambiguous or too broad.

## Procedure
1. Read .github/workflow/tasks.md, .github/workflow/changeLog.md, and .github/workflow/Framework.md together.
2. Separate real actionable workstreams from templates, references, or stale notes.
3. Ensure each open item has clear owner agent, next agent, status, blocked-on, and verification target fields.
4. If an item is being marked `done`, verify whether .github/workflow/changeLog.md already contains a matching completion entry.
5. When a completed tracked item is missing from the change log, add a concise draft entry or leave an explicit reminder in the workflow output.
6. Validate the markdown after edits.

## Checks
- Preserve history instead of rewriting completed work.
- Do not invent tracked tasks when none exist.
- Keep wording aligned with current repo structure and workflow.
- Keep one-task-at-a-time execution clear even when multiple workstreams are recorded.
- Do not mark a tracked task `done` and ignore the corresponding change log check.