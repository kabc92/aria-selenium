# Aria Selenium Automation Framework

Aria is a Java-based UI test automation framework built with Selenium WebDriver, TestNG, and Cucumber.

The project focuses on maintainable and reusable test automation, including Page Object Model, centralized WebDriver management, parallel test execution, dependency injection, reporting, and continuous integration.

The current implementation automates the SauceDemo application, covering login validation and an end-to-end purchase flow from login through checkout completion.

## Tech Stack

- Java 21
- Selenium WebDriver
- TestNG
- Cucumber
- PicoContainer
- Maven
- Allure
- GitHub Actions

## Key Features

- Page Object Model with reusable page components
- Centralized WebDriver creation and lifecycle management
- Parallel TestNG execution with ThreadLocal WebDriver isolation
- Parallel Cucumber scenario execution with TestNG DataProvider
- Scenario-scoped dependency injection with PicoContainer
- Reusable PageManager with page caching
- Explicit waits and reusable Selenium actions through BasePage
- Configurable headless Chrome execution for local and CI environments
- Automatic screenshots on test failures
- Allure reporting integration
- GitHub Actions CI for automated TestNG and Cucumber execution

## Architecture

Aria supports two test execution approaches while reusing the same core Selenium components:

- **TestNG tests** use `DriverManager` with `ThreadLocal<WebDriver>` to keep browser sessions isolated during parallel execution.
- **Cucumber scenarios** use `TestContext` and PicoContainer to share the WebDriver between Hooks and Step Definitions within the same scenario.
- `DriverFactory` is responsible for creating WebDriver instances for both approaches.
- `PageManager` creates and caches Page Objects using the WebDriver associated with the current test or scenario.
- All Page Objects extend `BasePage`, which provides shared WebDriver access, reusable Selenium actions, and explicit waits.

### High-Level Flow

```text
TESTNG                              CUCUMBER

Test                               Scenario
 │                                    │
 ▼                                    ▼
DriverManager                     TestContext
(ThreadLocal)                  (shared by PicoContainer)
 │                                    │
 └──────────────┬─────────────────────┘
                │
                ▼
           WebDriver
                │
                ▼
           PageManager
                │
                ▼
           Page Objects
```

`DriverFactory` creates the WebDriver used by either execution path. The main difference is how each path keeps the correct WebDriver isolated: TestNG uses a ThreadLocal driver per execution thread, while Cucumber uses a scenario-scoped `TestContext` shared through PicoContainer.

`BasePage` is the parent class for all Page Objects and provides shared WebDriver access, explicit waits, and reusable Selenium actions.

## Project Structure

```text
src/test/java/com/aria
├── base
│   ├── BasePage.java
│   └── BaseTest.java
├── config
│   └── ConfigReader.java
├── cucumber
│   ├── Hooks.java
│   ├── LoginSteps.java
│   ├── TestContext.java
│   └── runner
│       └── CucumberRunnerTest.java
├── driver
│   ├── DriverFactory.java
│   └── DriverManager.java
├── pages
│   ├── LoginPage.java
│   ├── InventoryPage.java
│   ├── CartPage.java
│   ├── CheckoutPage.java
│   └── CheckoutOverviewPage.java
├── tests
│   ├── LoginTest.java
│   ├── E2ETest.java
│   └── E2ETestWithPageManager.java
└── utils
    ├── PageManager.java
    └── ScreenshotUtil.java

src/test/resources
├── features
│   └── login.feature
├── config.properties
└── allure.properties
```

The framework separates browser management, reusable page behavior, Page Objects, test execution, and Cucumber-specific components into dedicated packages.

- `base` — shared functionality used by tests and Page Objects.
- `driver` — WebDriver creation and thread-based driver management.
- `pages` — Page Objects representing the application's UI.
- `tests` — direct TestNG test cases.
- `cucumber` — Hooks, shared scenario context, Step Definitions, and the Cucumber runner.
- `utils` — reusable framework utilities such as PageManager and screenshot handling.
- `config` — configuration loading.

## Running the Tests

### Prerequisites

Before running the project, make sure the following are installed:

- Java 21
- Maven
- Google Chrome

### Run the TestNG Suite

The default Maven test command executes the TestNG suite configured in `testng.xml`:

```bash
mvn clean test
```

The TestNG suite runs test classes in parallel using the configuration defined in `testng.xml`.

### Run the Cucumber Suite

Cucumber scenarios can be executed through the Cucumber TestNG runner:

```bash
mvn test -Dtest=CucumberRunnerTest
```

Cucumber scenarios run in parallel through TestNG's DataProvider.

### Headless Execution

Chrome runs with the browser UI by default during local execution.

To run the TestNG suite in headless mode:

```bash
mvn clean test -Dheadless=true
```

To run the Cucumber suite in headless mode:

```bash
mvn test -Dtest=CucumberRunnerTest -Dheadless=true
```

Headless mode is configurable through the `headless` Java system property and is used by the CI workflow.

## Continuous Integration

Aria uses GitHub Actions to automatically validate the automation framework on every push and pull request.

The CI workflow runs on a GitHub-hosted Ubuntu environment with Java 21 and executes both automation paths in headless Chrome.

```text
Push / Pull Request
        │
        ▼
  GitHub Actions
        │
        ▼
 Ubuntu Runner
   + Java 21
        │
        ├──► TestNG Suite
        │      mvn clean test -Dheadless=true
        │
        └──► Cucumber Suite
               mvn test -Dtest=CucumberRunnerTest -Dheadless=true
```

The workflow performs the following validations:

- Checks out the repository.
- Configures Java 21 using Eclipse Temurin.
- Uses Maven dependency caching to improve build efficiency.
- Executes the parallel TestNG suite in headless mode.
- Executes the parallel Cucumber scenarios in headless mode.

This ensures that both execution approaches are automatically validated whenever changes are pushed to the repository or included in a pull request.

## Configuration

Aria uses configuration files and Java system properties to separate test settings from the framework code.

The application URL is defined in:

```text
src/test/resources/config.properties
```

Example:

```properties
baseUrl=https://www.saucedemo.com/
```

### Browser

Chrome is used as the default browser. A different supported browser can be selected through the `browser` system property:

```bash
mvn clean test -Dbrowser=firefox
```

Supported browsers:

- Chrome
- Firefox
- Edge

### Headless Mode

Chrome runs with the browser UI by default. Headless execution can be enabled through the `headless` system property:

```bash
mvn clean test -Dheadless=true
```

This allows the same framework code to run with a visible browser during local development and in headless mode during CI execution.

## Test Reporting

Aria includes Allure integration for test reporting and automatically captures debugging evidence when tests fail.

### Allure Reports

Aria includes Allure integration for generating test reports.

After generating Allure results, the report can be opened with:

```bash
allure serve target/allure-results
```

### Failure Screenshots

For direct TestNG tests, `BaseTest` uses `ScreenshotUtil` during teardown to save a screenshot when a test fails.

For Cucumber scenarios, the `@After` Hook captures a screenshot when a scenario fails and attaches it to the Allure results.

## Current Test Coverage

The current implementation uses SauceDemo to demonstrate the framework across both validation and end-to-end test scenarios.

### Login Validation

Cucumber scenarios cover:

- Successful login with valid credentials
- Missing username validation
- Missing password validation
- Invalid credential combinations using Scenario Outline

### End-to-End Purchase Flow

The TestNG end-to-end tests cover the following user journey:

```text
Login
  │
  ▼
Inventory
  │
  ▼
Add Product to Cart
  │
  ▼
Cart
  │
  ▼
Checkout Information
  │
  ▼
Checkout Overview
  │
  ▼
Complete Purchase
```

This flow exercises multiple Page Objects and demonstrates how the framework reuses the same browser session across different application pages during a test.

## Roadmap

Aria V1 provides the core architecture for reusable and parallel web test automation. Future improvements may include:

- Extend the framework to automate an additional web application and demonstrate reusability across projects
- Add multi-environment configuration for QA, staging, and other execution environments
- Improve test data management, including data isolation and cleanup for parallel execution
- Integrate secure secrets management for credentials and sensitive configuration
- Expand test coverage with additional scenarios and edge cases
- Improve reporting, execution diagnostics, and flaky test monitoring
- Continue improving CI/CD capabilities with reporting, artifacts, and additional execution strategies
- Explore distributed execution for larger test suites and remote browser infrastructure
- Add API test automation as an additional framework capability