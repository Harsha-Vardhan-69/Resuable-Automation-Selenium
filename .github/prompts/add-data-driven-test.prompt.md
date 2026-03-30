---
name: "Add Data-Driven Test"
description: "Create or extend a Selenium/TestNG test that uses the framework's CSV, Excel, JSON, or provider-based data patterns instead of inline test data."
argument-hint: "Describe the scenario, data source, target test area, and expected assertions or row behavior; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skill: data-driven-test-authoring.

Use .github/workflow/Framework.md and the existing data-provider patterns to add or update a data-driven test.

Requirements:
- Reuse existing data readers and providers before creating a new one.
- Keep test data separate from the test logic when practical.
- Keep assertions readable and failure-friendly for each data row.
- Add only the minimal data or provider plumbing required for the requested scenario.
- Verify with the smallest relevant Maven or TestNG command.

Suggested input template:
- Scenario to automate:
- Data source or format:
- Target test class or area:
- Assertions or row-level expectations:

Use the template when the request fits naturally; use freeform notes when the data-driven request is broader or does not fit the fields cleanly.

Task:
{{input}}