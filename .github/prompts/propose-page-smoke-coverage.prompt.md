---
name: "Propose Page Smoke Coverage"
description: "Inspect a page or small site area and propose practical Selenium/TestNG smoke-test coverage candidates before implementation."
argument-hint: "Describe the page URL, feature area, environment, and whether screenshots, forms, links, or multi-tab behavior matter"
agent: "Master Automatin Agent"
---
Related skills: website-smoke-coverage, page-flow-pattern-builder, window-tab-navigation.

Use .github/workflow/Framework.md and the current test patterns to propose smoke coverage candidates in a way that can be implemented cleanly later.

Requirements:
- Inspect the page, relevant existing tests, and current locator or page-flow patterns before proposing coverage.
- Recommend a small set of high-value smoke scenarios, expected assertions, and likely screenshot checkpoints.
- Suggest how to split coverage across test classes, locator files, or reusable flows without overengineering.
- Call out any risky external links, new-tab behavior, auth gates, or environment dependencies that should shape the smoke plan.
- Stay in planning mode unless the user explicitly asks you to implement the proposed coverage.

Coverage target:
{{input}}