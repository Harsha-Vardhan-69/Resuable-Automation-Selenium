---
name: "Work Next Framework Task"
description: "Run the next tracked Selenium/TestNG framework maintenance task from .github/workflow/tasks.md through Master Automatin Agent, which routes to the maintainer path when needed."
argument-hint: "Optional scope or constraint for the next tracked task"
agent: "Master Automatin Agent"
---
Related skill: task-ledger-manager.

This prompt enters through `Master Automatin Agent` and is expected to route internally to `Automation Framework Maintainer (Read & Write)` for implementation.

Read .github/workflow/Framework.md and .github/workflow/tasks.md, then complete the next real open ledger item owned by or ready for the maintainer path in the smallest safe scope.

Requirements:
- Work only one task at a time.
- If `.github/workflow/tasks.md` only contains template or reference items, stop and ask the user for a real tracked task instead of inventing one.
- Prefer an open item whose owner agent is `Automation Framework Maintainer (Read & Write)` or whose next agent points to that maintainer path.
- Follow the framework patterns already used in the repository.
- Verify with the most relevant Maven or TestNG command you can run locally.
- If the tracked task is completed, verify that .github/workflow/changeLog.md has a matching entry; add a concise draft entry or call out the missing log explicitly.
- Summarize assumptions, changed areas, verification, and remaining risk.

Additional user input:
{{input}}