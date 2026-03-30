---
name: "verification-impact-mapper"
description: "Use for mapping changed framework files to the smallest defensible Maven or TestNG verification plan, including shared-component risk and browser or environment scope."
argument-hint: "Describe the changed files, diff scope, or verification decision to make"
---

# Verification Impact Mapper

## When to Use
- The current diff is known, but the right verification scope is unclear.
- A change touches shared framework code and you want something more precise than a full-suite guess.
- You need to justify why a class, group, suite, browser, or env run is enough.

## Procedure
1. Inspect the changed files or diff before recommending any command.
2. Classify each change as local scenario code, locator or page-flow code, shared utility, driver or listener or reporting code, config, or suite or build wiring.
3. Map each category to the narrowest sensible verification target, such as one class, one method, one group, one suite, or a browser or env-specific run.
4. Recommend one smallest-first verification command, then add broader follow-up coverage only when shared risk justifies it.
5. Explain why each recommended run covers the changed surface and what risk remains outside that scope.

## Checks
- Do not jump to full-suite verification without explaining why narrower coverage is insufficient.
- Call out browser-specific or env-specific risk when the changed files affect config, navigation, or driver behavior.
- Keep recommendations aligned with the actual repo structure, current suite layout, and existing focused checks.