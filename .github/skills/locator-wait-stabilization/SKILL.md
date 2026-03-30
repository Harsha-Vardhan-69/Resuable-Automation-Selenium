---
name: locator-wait-stabilization
description: 'Use for intercepted clicks, stale element errors, timing issues, brittle selectors, wait problems, and Selenium locator stabilization in this framework.'
argument-hint: 'Describe the failing locator, stack trace, screenshot, or timing symptom'
---

# Locator Wait Stabilization

## When to Use
- Selenium click interception appears in logs.
- A locator is brittle or environment-sensitive.
- A test is failing because waits are too weak or too local.

## Procedure
1. Start from the failure evidence: stack trace, screenshot, page source, or report output.
2. Identify whether the issue is selector quality, page timing, fixed headers, stale DOM, or third-party site behavior.
3. Prefer the smallest framework-aligned fix: stronger locator, reusable wait, safer navigation, or targeted utility improvement.
4. Keep the fix local to the failing behavior.
5. Re-run the smallest verification command that exercises the failure.

## Checks
- Avoid replacing a clear root-cause fix with sleeps.
- Call out environment-only or anti-bot failures explicitly.
- Do not refactor unrelated test flows while stabilizing one failure.