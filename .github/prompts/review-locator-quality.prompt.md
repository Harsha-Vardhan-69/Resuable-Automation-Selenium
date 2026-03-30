---
name: "Review Locator Quality"
description: "Review page locators or related UI selectors in this framework and suggest stronger, less flaky alternatives before failures spread."
argument-hint: "Describe the locator file, page, elements, or current selector concerns to review"
agent: "Master Automatin Agent"
tools: [read, search]
---
Related skills: locator-wait-stabilization, page-flow-pattern-builder.

Use .github/workflow/Framework.md as the source of truth and review locator quality in read-only mode before making changes.

Requirements:
- Inspect the current locator definitions, surrounding page-flow usage, and any available failure evidence.
- Flag brittle patterns such as deep XPath chains, text-only selectors, unstable indexes, timing-sensitive click targets, and duplicated selector logic.
- Suggest stronger alternatives using stable attributes, clearer scoping, or reusable wait-friendly patterns that fit this repo.
- Distinguish between urgent flaky-risk locators and acceptable locators that do not need change yet.
- Stay read-only unless the user explicitly asks you to implement the improvements.

Locator review scope:
{{input}}