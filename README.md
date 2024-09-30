# Katalon Studio Extent Report Plugin

This plugin integrates Extent Reports with Katalon Studio, providing detailed and customizable test execution reports.

## Features

- Generates HTML reports with detailed test execution results.
- Captures screenshots on test failure.
- Provides system information in the report.
- Supports categorization of tests by browser, profile, and group.

## Installation

1. Clone the repository or download the source code.
2. Open Katalon Studio and import the project.
3. Ensure all dependencies are included in the `Drivers` folder.

## Usage

### Setting Up the Report

1. Add the `ExtentReportsListener` to your test suite.
2. Configure the report title and document title in the `attachEReport` method.

### Running Tests

- Execute your test suite in Katalon Studio.
- The report will be generated in the `Extent` folder with a timestamp.

### Customizing the Report

- Modify the `ExtentReport.groovy` file to change report settings such as theme and timestamp format.

## Keywords

- `attachEReport`: Initializes the report.
- `startEReport`: Starts logging for a test case.
- `flushEReport`: Finalizes the report.
- `takeScreenshotFailure`: Captures a screenshot on test failure.
- `deleteFolderContents`: Cleans up old reports.

## License

This project is licensed under the MIT License.
