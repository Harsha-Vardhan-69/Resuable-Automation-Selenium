---
name: "Sync Framework Docs"
description: "Sync README, CONTRIBUTING, and agent workflow docs with the current Selenium/TestNG framework behavior and customization layout."
argument-hint: "Describe the section to update, summary of the change, reason, and any points to clarify; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skill: framework-doc-drift-check.

Update the relevant documentation so it matches the current repository behavior and agent workflow.

Requirements:
- Keep docs aligned with `.github/workflow/Framework.md` and the actual repo layout.
- Prefer concise updates over duplicating existing guidance.
- Call out any remaining doc drift that still needs cleanup.

Suggested input template:
- Section to update:
- Summary of the change:
- Reason for the update:
- Points that need extra clarification:

Freeform notes are still allowed when the documentation request spans multiple areas.

Task:
{{input}}