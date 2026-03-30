---
name: "Create Page Flow"
description: "Create or refactor locator and page-flow code for this Selenium/TestNG framework using robust locators and reusable interactions."
argument-hint: "Describe the page or flow, target elements, intended interaction path, and any stability concerns; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skill: page-flow-pattern-builder.

Use .github/workflow/Framework.md and the existing locator structure to create or refactor a page flow.

Requirements:
- Prefer stable locators and readable flow methods.
- Keep waits in shared utilities rather than ad hoc sleeps.
- Minimize duplicate step logic across tests.
- Verify with the smallest relevant Maven or TestNG command.

Suggested input template:
- Page or feature area:
- Flow or interaction to create/refactor:
- Target elements or locators involved:
- Stability concerns or edge cases:

Use the template when the request fits naturally; use freeform notes when the page-flow request is broader or does not fit the fields cleanly.

Task:
{{input}}