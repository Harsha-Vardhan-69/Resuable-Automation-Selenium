---
name: website-smoke-coverage
description: 'Use for adding website smoke tests, page screenshot coverage, simple navigation checks, external link validation, and lightweight locator-based Selenium coverage for public sites or small page sets.'
argument-hint: 'Describe the site URL, pages to cover, and screenshot or navigation requirements'
---

# Website Smoke Coverage

## When to Use
- A website needs quick Selenium smoke coverage.
- The goal is screenshots plus a few stable navigation checks.
- A user wants to cover home, about, contact, or portfolio-style pages.

## Procedure
1. Inspect the live site or current page structure before writing locators.
2. Prefer direct navigation for stable coverage when third-party search paths are flaky.
3. Keep page classes locator-only unless reusable flow logic clearly reduces duplication.
4. Add meaningful screenshots and lightweight validations for each covered page.
5. Verify with a focused Maven run for the new class.

## Checks
- Favor stable headings, nav items, and explicit page markers over brittle layout selectors.
- Keep each test scenario tightly scoped.
- Use new tab or window handling only when the site behavior actually requires it.