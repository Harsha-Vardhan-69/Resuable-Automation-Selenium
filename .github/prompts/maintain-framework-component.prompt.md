---
name: "Maintain Framework Component"
description: "Maintain or extend a Selenium/TestNG framework component, test, page object, listener, config, or utility through Master Automatin Agent, which routes to the right specialist path."
argument-hint: "Describe the issue or improvement, affected components, priority, and any relevant constraints; freeform context is also fine"
agent: "Master Automatin Agent"
---
Related skills: locator-wait-stabilization, reporting-context-debugger, environment-config-checker, targeted-test-verification.

This prompt enters through `Master Automatin Agent` and is expected to route internally to `Automation Framework Maintainer (Read & Write)` for framework implementation.

Use .github/workflow/Framework.md as the source of truth and complete the requested framework maintenance task.

Requirements:
- Inspect the relevant code before editing.
- Keep the change tightly scoped.
- Prefer explicit waits, reusable utilities, and stable locators.
- Preserve reporting, listener hooks, and driver lifecycle behavior.
- Run the smallest relevant Maven or TestNG verification you can support locally.
- If this becomes a larger tracked workstream, use .github/workflow/tasks.md and .github/workflow/changeLog.md.

Suggested input template:
- Issue or improvement:
- Affected components:
- Priority or urgency:
- Constraints or relevant context:

Freeform notes are still allowed when the request does not fit the template cleanly.

Task:
{{input}}