---
name: targeted-test-verification
description: 'Use for running one Selenium/TestNG class, one method, or a narrow subset, reviewing the result, and fixing only the directly related issue with focused Maven verification.'
argument-hint: 'Provide the test class, optional method or group, and any browser or env constraint'
---

# Targeted Test Verification

## When to Use
- A user wants one test class or method checked.
- Verification should stay narrow instead of running the whole suite.
- A fix should remain tightly scoped to the failing test path.

## Procedure
1. Choose the smallest Maven or TestNG command that hits the requested scope.
2. Inspect the direct output, reports, and stack traces before editing.
3. Fix only the failing behavior needed for that scope.
4. Re-run the same narrow verification first.
5. Expand verification only if the change clearly touches shared behavior.

## Checks
- Do not widen the task into a broad cleanup.
- Prefer deterministic repro steps and direct evidence.
- Summarize the exact command and result.