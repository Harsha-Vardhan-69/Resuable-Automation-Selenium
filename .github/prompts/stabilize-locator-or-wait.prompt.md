---
name: "Stabilize Locator Or Wait"
description: "Fix a Selenium locator, intercepted click, stale element, or timing problem in this framework with the smallest safe change and targeted verification."
argument-hint: "Describe the failing test, locator, stack trace, screenshot, or wait symptom to stabilize"
agent: "Master Automatin Agent"
---
Related skill: locator-wait-stabilization.

Use .github/workflow/Framework.md as the source of truth and stabilize the failing locator or wait behavior in the smallest safe scope.

Requirements:
- Start from the observed failure evidence before changing code.
- Prefer stable locators, reusable wait utilities, and framework-level fixes over ad hoc sleeps.
- Do not broaden the change into an unrelated refactor.
- If the root cause is environment-only or third-party-site behavior, state that clearly.
- Verify with the most targeted Maven or TestNG command available locally.

Task:
{{input}}