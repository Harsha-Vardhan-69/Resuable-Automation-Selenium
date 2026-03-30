---
name: "Add Website Smoke Coverage"
description: "Create Selenium/TestNG smoke coverage for a website or small page set, including navigation, screenshots, and minimal reusable locators."
argument-hint: "Describe the site URL, pages to cover, and whether screenshots or external link checks are required"
agent: "Master Automatin Agent"
---
Related skills: website-smoke-coverage, window-tab-navigation.

Use .github/workflow/Framework.md and the current test patterns to add website smoke coverage in the smallest safe scope.

Requirements:
- Prefer one focused smoke scenario per test class unless the user explicitly asks for broader coverage.
- Keep page locator classes locator-only when possible and move repeated flow logic into reusable utilities or page flows only when needed.
- Capture screenshots only at meaningful checkpoints.
- Use stable locators and direct navigation when live third-party search flows are unreliable.
- Verify with the smallest relevant Maven or TestNG command.

Task:
{{input}}