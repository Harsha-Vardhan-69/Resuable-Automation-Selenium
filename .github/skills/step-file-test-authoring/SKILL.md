---
name: step-file-test-authoring
description: 'Use for txt step files, manual execution notes, plain-language scenarios, and converting them into Selenium WebDriver TestNG tests with the repo''s current page-flow, locator, and wait patterns.'
argument-hint: 'Describe the step file, manual scenario, or target test class'
---

# Step File Test Authoring

## When to Use
- A user provides a txt file with execution steps.
- A manual test script needs to become a new TestNG test.
- An existing test must be updated from plain-language steps.

## Procedure
1. Read .github/workflow/Framework.md and inspect the closest existing tests, page locators, and utilities.
2. Break the steps into navigation, reusable interactions, assertions, screenshots, and data needs.
3. Reuse existing locators, waits, and page flows before adding new ones.
4. Keep test classes readable and business-level; move repeated UI steps into reusable support only when needed.
5. Verify with the smallest relevant Maven or TestNG command.

## Checks
- Avoid Thread.sleep when existing wait utilities can solve the timing.
- Keep screenshots only at meaningful checkpoints.
- Do not widen the task into unrelated framework refactors.