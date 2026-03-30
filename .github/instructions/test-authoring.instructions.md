---
name: "Test Authoring Instructions"
description: "Use when editing or creating TestNG test classes in src/test/java/com/framework/tests. Covers stable automation test design, assertions, and verification habits."
applyTo: "src/test/java/com/framework/tests/**/*.java"
---
# Test Authoring Guidelines

- Keep tests deterministic, isolated, and readable.
- Prefer business-level test steps over low-level click sequences when reusable utilities or page flows already exist.
- Use stable assertions with clear failure context.
- Reuse framework utilities and data providers instead of ad hoc waits or inline test data.
- Verify with the smallest relevant Maven or TestNG command after changing tests.