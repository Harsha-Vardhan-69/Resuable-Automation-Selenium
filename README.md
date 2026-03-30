# Lynx Automation Framework

A Java-based Selenium Test Automation Framework using TestNG, Maven, and ExtentReports.

## Features

- Selenium WebDriver for browser automation
- TestNG for test orchestration, grouping, and parallel execution
- ExtentReports for rich HTML reports and screenshots
- Reusable locator and page-flow structure with worked examples
- Data-driven testing with CSV/Excel files
- Configurable via environment profiles (`config.properties`, `config.uat.properties`, `config.prod.properties`)
- Secrets management: override config values with environment variables
- Automatic screenshot and artifact capture on test failure and failed steps
- Maven for build and dependency management

## Project Structure

``` Structure
├── .github/
│   ├── agents/
│   ├── hooks/
│   ├── instructions/
│   └── prompts/
├── src/
│   ├── main/
│   │   ├── java/com/framework/...
│   │   └── resources/
│   └── test/
│       ├── java/com/framework/pageLocators/
│       ├── java/com/framework/tests/
│       ├── java/com/framework/utils/
│       └── resources/
├── Reports/
├── logs/
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java 21 or above
- Maven 3.6+
- Chrome / Edge installed locally
- Drivers are managed via WebDriverManager; if that fails, the framework falls back to local binaries in `drivers/`

### Setup

1. Clone the repository:

 ```sh
 git clone <repo-url>
 cd Reuseable_AutomationFramework
 ```

1. Install dependencies:

 ```sh
 mvn clean install
 ```

1. Configure your test settings in `src/test/resources/config.properties`.

- To use a specific environment, set the `env` system property or environment variable (e.g., `-Denv=uat` or `ENV=uat`).
- If no terminal override is provided, the framework falls back to the `env` value in `src/test/resources/config.properties`.
- Sensitive values (e.g., passwords) can be set as environment variables (e.g., `LOGIN_PASSWORD`).

### Environment profiles (config.*.properties)

- The framework selects the config file based on `env`:
  - Default: `config.properties`
  - With env: `config.<env>.properties` (example: `config.uat.properties`)
- The resolved `url` must be a real URL or hostname. Placeholder values such as `uat` now fail fast during test startup.

Examples:

```sh
mvn -q clean test -Denv=uat
```

PowerShell:

```powershell
$env:env = "uat"
mvn -q clean test
```

### Browser + headless selection

The driver is selected from config key `browser` and `headless`:

- `browser`: `chrome` (default), `edge`
- `headless`: `true` / `false`

If `browser` is set to any value outside `chrome` or `edge`, the framework now fails fast during driver initialization with a clear configuration error.

ConfigLoader also supports overriding any config key via environment variables using the uppercased key name:

- `browser` -> `BROWSER`
- `headless` -> `HEADLESS`
- `url` -> `URL`

PowerShell examples:

```powershell
$env:BROWSER = "edge"
$env:HEADLESS = "true"
mvn -q clean test
```

### Running Tests

- To run all tests using Maven:

 ```sh
 mvn test
 ```

- Default suite execution:
  - Surefire is configured to run `src/test/resources/testng.xml` by default (see `pom.xml`).
  - The checked-in suite currently runs the thin scenario tests `TC00_LoginSmokeTest`, `TC01_FramewokBasicTest`, `TC02_HanoverAvailabilityJourneyTest`, and `TC03_GoogleSearchYoutubeTest` by default.

- To run a specific TestNG suite:

 ```sh
 mvn test -DsuiteXmlFile=src/test/resources/testng.xml
 ```

- To run only a group (e.g., smoke):

 ```sh
 mvn test -Dgroups=smoke
 ```

- Default suite execution uses `src/test/resources/testng.xml`.
- The checked-in TestNG suite is non-parallel by default (`parallel="none"`), while Maven Surefire still uses multiple forks.

### Reports & Screenshots

- HTML reports: `Reports/Run_<timestamp>/Extent Reports/extent-report.html`
- Screenshots: `Reports/Run_<timestamp>/Screenshots/<TestCaseName>/`
- Framework log: `Reports/Run_<timestamp>/Logs/framework.log`

## Customization

- Add new scenario tests in `src/test/java/com/framework/tests/`
- Add or update page-specific locator files in `src/test/java/com/framework/pageLocators/`
- Update configuration in `src/test/resources/config.properties` or environment-specific files
- Use TestNG groups for selective test execution
- Add or update agent workflow assets in `.github/agents/`, `.github/prompts/`, `.github/instructions/`, and `.github/hooks/`

## Agent Workflow

- Use `Master Automatin Agent` as the default visible entry point for mixed, ambiguous, or multi-step workflow requests.
- Internal specialist routing handles framework code and test implementation through `Automation Framework Maintainer (Read & Write)` when needed.
- Internal specialist routing handles failing run analysis, logs, screenshots, and report review through `Test Failure Diagnosis (Read Only)` when needed.
- Internal specialist routing handles README, CONTRIBUTING, and agent workflow documentation updates through `Project Docs Maintainer (Read & Write)` when needed.
- Reusable prompt entry points live under `.github/prompts/`.
- For larger tracked workstreams, use `.github/workflow/tasks.md` as a handoff ledger and `.github/workflow/changeLog.md` when actionable work completes.

## Contributing

See `CONTRIBUTING.md` for guidelines on contributing to this project.

## License

This project is for internal use of Hanover Insurance.
