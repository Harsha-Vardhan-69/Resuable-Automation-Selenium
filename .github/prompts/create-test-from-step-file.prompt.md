---
name: "Create Test From Step File"
description: "Create or update a Selenium/TestNG test from a txt step file, manual execution notes, or plain-language scenario using the repo's current framework patterns."
argument-hint: "Describe the step file, scenario, target test class, and whether this is a new test or an update"
agent: "Master Automatin Agent"
---
Related skill: step-file-test-authoring.

Use .github/workflow/Framework.md and the current test structure to turn a step file or manual scenario into automated coverage.

Requirements:
- Read the provided txt file or plain-language steps before editing.
- Reuse existing page flows, locators, waits, and data providers where practical.
- Add the smallest supporting page-flow or locator change needed for the scenario.
- Keep the final test deterministic, readable, and aligned with src/test/java/com/framework/tests/.
- Verify with the smallest relevant Maven or TestNG command.

Task:
{{input}}