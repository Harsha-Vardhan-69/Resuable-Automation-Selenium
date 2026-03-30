---
name: "Java Framework Instructions"
description: "Use when editing shared Java framework code such as config, data readers, driver factory, listeners, reporting, or utilities. Covers Selenium/TestNG framework stability rules."
applyTo: "src/main/java/com/framework/**/*.java"
---
# Java Framework Guidelines

- Preserve driver lifecycle, listener hooks, and Extent report integration.
- Prefer reusable utilities and explicit waits over duplicated Selenium calls or `Thread.sleep`.
- Keep shared framework code deterministic and environment-aware.
- Make the smallest safe change and avoid broad refactors in shared classes.
- When editing cross-cutting classes such as config loaders, listeners, reports, or driver factories, call out impact before changing behavior.