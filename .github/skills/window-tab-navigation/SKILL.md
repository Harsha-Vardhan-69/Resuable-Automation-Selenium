---
name: window-tab-navigation
description: 'Use for Selenium flows involving new browser windows, tabs, external links, handle switching, returning to the original page, and multi-site screenshot scenarios.'
argument-hint: 'Describe the tab or window flow, external links, and expected return path'
---

# Window Tab Navigation

## When to Use
- A test opens external links in new tabs.
- The scenario requires switching between browser windows.
- A smoke flow spans multiple sites or tabs.

## Procedure
1. Capture the current window handle before opening a new tab or window.
2. Use reusable helpers for opening, switching, validating, and closing tabs.
3. Validate the destination using stable URL or title expectations.
4. Return to the original window explicitly.
5. Verify with a focused command that exercises the whole navigation path.

## Checks
- Avoid duplicating low-level handle logic across tests.
- Prefer stable URL and title assertions over fragile UI-only checks.
- Keep cross-site flows minimal and purposeful.