---
name: environment-config-checker
description: 'Use for browser selection, environment profiles, config property drift, base URL issues, Maven/TestNG execution settings, and environment-aware Selenium framework checks.'
argument-hint: 'Describe the env, browser, config file, or execution behavior to inspect'
---

# Environment Config Checker

## When to Use
- A test behaves differently across env or browser settings.
- Base URL or config resolution is unclear.
- Maven, TestNG, or environment property wiring needs inspection.

## Procedure
1. Inspect config files, loader behavior, and command-line overrides before editing.
2. Confirm which browser, env, and URL actually resolve at runtime.
3. Identify drift between docs, config defaults, and current framework behavior.
4. Make the smallest safe config or validation adjustment if a change is needed.
5. Verify with a focused command that exercises the relevant env path.

## Checks
- Do not hardcode env-specific values into tests.
- Call out when the issue is bad input rather than bad framework logic.
- Keep config changes explicit and minimally invasive.