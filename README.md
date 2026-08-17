# SQE UI and API Automation Framework

A Maven-based automation framework for **UI and API testing** using Java. The UI automation targets **SauceDemo** with Selenium WebDriver, while API automation targets the **Swagger Petstore API** with REST Assured.

The framework supports TestNG, JUnit 4, Cucumber BDD, API schema validation, data-driven testing, and HTML reporting.

## Technology Stack

| Technology | Version / Details |
|---|---|
| Java | 21 |
| Maven | Build and dependency management |
| Selenium | 4.46.0 |
| WebDriverManager | 6.3.3 |
| TestNG | 7.11.0 |
| JUnit | 4.13.2 |
| Cucumber Java / JUnit | 7.18.0 |
| Cucumber TestNG | 7.33.0 |
| REST Assured | 5.5.6 |
| JSON Schema Validator | 5.5.6 |
| Allure | 2.32.0 BOM |
| Allure Maven Plugin | 2.12.0 |
| Cucumber Reporting | 5.10.1 |
| OpenCSV | 5.12.0 |
| Apache POI | 5.5.1 |
| JSON | 20250517 |
| JSON Simple | 1.1 |
| Kotlin | 1.9.22 |
| Lombok | 1.18.42 |
| IDE | IntelliJ IDEA |

All dependency and plugin versions above are taken from the current `pom.xml`.

## Application Under Test

### UI - SauceDemo

- URL: `https://www.saucedemo.com/`
- Username: `standard_user`
- Password: `secret_sauce`
- Configured browser: Firefox

### API - Swagger Petstore

- Base URL: `https://petstore.swagger.io/v2`

The UI and API environment values are defined in `configuration.json`.

## Configuration

### `configuration.properties`

```properties
browser=firefox
testEnvironment=UI
loginCredentials_CSVFilePath:src/test/resources/ui_TestData/LoginCredentials_TestData.csv
```

Set `testEnvironment` to `UI` or `API` depending on the test suite you want to execute. The currently provided configuration is set to `UI`. fileciteturn0file1

### `configuration.json`

The JSON configuration contains separate environment definitions for UI and API execution:

```json
[
  {
    "environment": {
      "environmentName": "UI",
      "standardUsername": "standard_user",
      "commanPassword": "secret_sauce",
      "url": "https://www.saucedemo.com/"
    }
  },
  {
    "environment": {
      "environmentName": "API",
      "apiBaseUrl": "https://petstore.swagger.io/v2"
    }
  }
]
```



## Project Structure

The framework uses reusable utilities, configuration files, test resources, and Maven-managed dependencies.

- `utilities` - reusable utility classes, including `ConfigurationReader.java` for reading configuration values.
- `src/test/resources` - test resources and test data.
- `src/test/resources/ui_TestData/LoginCredentials_TestData.csv` - CSV file referenced by the current UI configuration.
- `configuration.properties` - browser, execution environment, and test-data configuration.
- `configuration.json` - UI and API environment configuration.
- `pom.xml` - Maven dependencies, Java/Kotlin compiler configuration, and reporting/build plugins.

The exact test-class and resource inventory is intentionally not listed here because it was not provided in the uploaded project files.

## Test Coverage Approach

The framework is intended to support:

- UI functional testing with Selenium WebDriver
- API functional testing with REST Assured
- Positive and negative scenarios
- API response validation
- JSON Schema validation
- Cucumber BDD scenarios
- TestNG execution
- JUnit 4 execution
- Data-driven UI testing using CSV data
- Test-data processing with Apache POI where required
- Reusable configuration and utility components
- HTMLreporting

## Prerequisites

Install and configure:

1. JDK 21
2. Maven
3. IntelliJ IDEA or another Java IDE
4. Firefox for the current UI configuration

## Running Tests

### 1. Select the Environment for UI

Open `configuration.properties` and choose the execution environment:

For UI tests:

```properties
testEnvironment=UI
```
The current configuration uses:

```properties
browser=firefox
testEnvironment=UI
```
### Run from IntelliJ IDEA

Test can be run thru CukesRunner after applting tags annotation


### Cucumber Reporting

The project also includes Masterthought Cucumber Reporting `5.10.1` for Cucumber report generation.

### Allure Report

After running the Cucumber tests, Allure result files are generated in the `allure-results` directory.

To view the Allure report:

1. Open the terminal in IntelliJ IDEA.
2. Ensure you are in the project root directory.
3. Execute the following command:

```bash
allure serve allure-results
```

## Maven Build Configuration

The project is configured to compile with Java 21:

## Future Improvements

Potential improvements for the framework include:

- Add UI/API integration checks to verify that API data is correctly represented in the UI.
- Add CI/CD execution through a pipeline such as Jenkins or GitHub Actions.
- Expand negative, boundary, and error-handling coverage.
- Add parallel execution where the test design permits it.
- Improve environment separation for different test environments.



