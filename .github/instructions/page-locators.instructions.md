---
name: "Page Locator Instructions"
description: "Use when editing locator classes or page flow classes under src/test/java/com/framework/pageLocators. Covers robust locators and readable page interactions."
applyTo: "src/test/java/com/framework/pageLocators/**/*.java"
---
# Page Locator Guidelines

- Prefer robust locators such as ids, names, stable attributes, or narrow CSS selectors over brittle XPath.
- Keep locator classes readable and grouped by page responsibility.
- Avoid burying wait logic inside locator definitions; use framework wait utilities for timing concerns.
- When a test flow grows beyond locator constants, favor a page-flow method or dedicated page object pattern rather than duplicating steps in tests.