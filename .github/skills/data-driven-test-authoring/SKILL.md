---
name: data-driven-test-authoring
description: 'Use for Excel, CSV, JSON, or provider-based Selenium/TestNG tests, including adding providers, wiring row-based assertions, and keeping test data separate from test flow.'
argument-hint: 'Describe the scenario, data source, and target test or provider'
---

# Data Driven Test Authoring

## When to Use
- A test should run from spreadsheet, CSV, JSON, or provider-driven inputs.
- Inline test data needs to move into a reusable provider.
- A new scenario needs data rows with targeted assertions.

## Procedure
1. Inspect existing data readers and provider patterns before creating new plumbing.
2. Match the scenario to the simplest current data source format.
3. Keep data definitions separate from navigation and assertions where practical.
4. Make row failures easy to understand by keeping assertions specific.
5. Verify with a focused command that exercises the relevant provider path.

## Checks
- Avoid ad hoc sleeps or ad hoc file parsing when existing readers fit.
- Keep provider logging and reporting aligned with the framework.
- Add only the minimum data structure needed for the scenario.