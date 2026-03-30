---
name: page-flow-pattern-builder
description: 'Use for creating or refactoring reusable page-flow patterns, locator-only page classes, and worked examples that show contributors how tests, locators, and flows should be structured in this repo.'
argument-hint: 'Describe the page, flow, or reusable example to build'
---

# Page Flow Pattern Builder

## When to Use
- A page flow is getting duplicated across tests.
- A contributor needs a worked pattern to copy.
- Locator classes need to stay readable while interactions remain reusable.

## Procedure
1. Inspect existing tests and page locator files for the nearest current pattern.
2. Decide whether the task should stay locator-only or gain a small reusable flow abstraction.
3. Keep locators grouped by page responsibility and interactions readable.
4. Move repeated steps out of tests only when that clearly improves reuse.
5. Verify the affected tests with the smallest focused run.

## Checks
- Do not bury timing logic inside raw locator definitions.
- Keep public APIs small and obvious.
- Preserve existing naming and folder conventions.