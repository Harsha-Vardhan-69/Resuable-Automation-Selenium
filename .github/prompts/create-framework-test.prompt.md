---
name: "Create Framework Test"
description: "Create or extend a Selenium/TestNG test in this repo using existing framework conventions, data providers, waits, and reporting behavior."
argument-hint: "Describe the test case, steps, expected outcome, and edge cases to cover; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skills: step-file-test-authoring, data-driven-test-authoring.

Use .github/workflow/Framework.md and the current test structure to create or extend a test class.

Requirements:
- Follow the existing `src/test/java/com/framework/tests/` pattern.
- Reuse data providers, waits, and framework utilities where practical.
- Keep assertions readable and deterministic.
- Verify with the smallest relevant Maven or TestNG command.

Suggested input template:
- Test case description:
- Steps or flow to automate:
- Expected outcome:
- Edge cases or special coverage needs:

Freeform notes are still allowed when the scenario needs more context than the template provides.

Task:
{{input}}