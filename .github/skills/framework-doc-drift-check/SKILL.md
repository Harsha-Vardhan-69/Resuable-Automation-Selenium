---
name: framework-doc-drift-check
description: 'Use for syncing README, CONTRIBUTING, prompts, agents, instructions, and workflow docs with the actual Selenium/TestNG framework behavior and repository layout.'
argument-hint: 'Describe the doc area, workflow drift, or behavior change to reflect'
---

# Framework Doc Drift Check

## When to Use
- Docs no longer match the code or repo layout.
- Prompt, agent, or instruction files have workflow drift.
- A framework behavior change should be reflected in user-facing guidance.

## Procedure
1. Compare the current code, repo structure, and workflow assets before editing docs.
2. Update only the docs that directly describe the changed behavior.
3. Prefer short, source-of-truth-aligned wording over duplicated prose.
4. Call out any remaining drift that should be handled separately.
5. Validate the changed markdown or customization files.

## Checks
- Keep workflow docs aligned with .github/workflow/Framework.md.
- Avoid documenting behavior that the code does not implement.
- Preserve concise repo-specific guidance.