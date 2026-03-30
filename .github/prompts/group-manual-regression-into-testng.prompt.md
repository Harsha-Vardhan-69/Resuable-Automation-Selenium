---
name: "Group Manual Regression Into TestNG"
description: "Turn repeated manual regression steps into focused grouped TestNG scenarios using the repo's existing Selenium framework patterns."
argument-hint: "Describe the manual regression steps, source document, target area, and whether this should create new tests or refactor existing ones"
agent: "Master Automatin Agent"
---
Related skills: step-file-test-authoring, data-driven-test-authoring.

Use .github/workflow/Framework.md and the current test structure to convert repeated manual regression coverage into maintainable grouped TestNG scenarios.

Requirements:
- Read the provided manual steps, repeated regression notes, or source file before editing.
- Group related checks into focused TestNG scenarios or groups instead of copying the same flow across many tests.
- Reuse existing locators, page flows, waits, and data providers before adding new support code.
- Prefer data-driven structure when the repetition is mostly input or expected-result variation.
- Verify with the smallest relevant Maven or TestNG command after implementation.

Regression source:
{{input}}