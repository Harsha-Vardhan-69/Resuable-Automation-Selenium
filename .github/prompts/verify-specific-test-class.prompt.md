---
name: "Verify Specific Test Class"
description: "Run a named Selenium/TestNG test class or small test subset, review the result, and apply the smallest safe fix when the requested verification exposes a framework issue."
argument-hint: "Provide the test target, desired verification scope, browser or environment constraints, and what to do if verification fails; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skill: targeted-test-verification.

Use .github/workflow/Framework.md to verify a specific test class or focused subset without widening the scope.

Requirements:
- Start with the smallest relevant Maven or TestNG command.
- If the test fails, inspect the direct evidence before editing.
- Keep any resulting fix tightly scoped to the failing behavior.
- Do not start a second task once the requested verification is in progress.
- Summarize the command used, result, and any remaining risk.

Suggested input template:
- Test class, method, or group:
- Verification goal:
- Browser or environment constraints:
- Expected follow-up if it fails:

Use the template when the request fits naturally; use freeform notes when the verification request needs broader context.

Task:
{{input}}