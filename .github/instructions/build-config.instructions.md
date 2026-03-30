---
name: "Build And Suite Config Instructions"
description: "Use when editing Maven build config or TestNG suite files such as pom.xml and src/test/resources/testng.xml. Covers parallelism, verification, and framework stability."
applyTo: "pom.xml, src/test/resources/testng.xml"
---
# Build And Suite Config Guidelines

- Treat `pom.xml` and `src/test/resources/testng.xml` as high-risk cross-cutting files.
- Keep concurrency, forks, and suite behavior aligned with framework stability goals.
- Prefer the smallest configuration change that addresses the issue.
- After changing build or suite config, verify with a relevant Maven command and note the impact on local execution.