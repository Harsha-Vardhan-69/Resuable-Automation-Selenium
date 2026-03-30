---
name: reporting-context-debugger
description: 'Use for debugging report folder ownership, screenshot path context, Extent report attachment paths, listener artifact coordination, and related reporting-context issues in this framework.'
argument-hint: 'Describe the artifact path, screenshot context, or reporting symptom to debug'
---

# Reporting Context Debugger

## When to Use
- Screenshots land in the wrong folder.
- Extent reports or listener artifacts are missing or cross-wired.
- Report context system properties or test-folder ownership look inconsistent.

## Procedure
1. Inspect the relevant listener, report, and screenshot utility flow before editing.
2. Trace how run-folder and test-folder context is set, used, and cleared.
3. Prefer explicit ownership and local context over hidden cross-class dependencies.
4. Add or adjust verification that proves context is cleaned up correctly.
5. Re-run only the reporting-related test coverage needed for the change.

## Checks
- Treat listeners, reports, and shared config as high-risk files.
- Keep changes tightly scoped and reversible.
- Preserve current Extent report integration unless the task requires change.