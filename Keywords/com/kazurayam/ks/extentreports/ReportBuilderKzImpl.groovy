package com.kazurayam.ks.extentreports

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import com.aventstack.extentreports.reporter.configuration.Theme
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.webui.driver.DriverFactory

/**
 * 
 * @author kazurayam
 */
public class ReportBuilderKzImpl extends ReportBuilder {

	// The "Bill Pugh Singleton" pattern is applied
	// See https://www.baeldung.com/java-bill-pugh-singleton-implementation
	private class SingletonHolder {
		private static final ReportBuilderKzImpl INSTANCE = new ReportBuilderKzImpl()
	}

	public static ReportBuilderKzImpl getInstance() {
		return SingletonHolder.INSTANCE
	}

	private ReportBuilderKzImpl() {}

	private ExtentSparkReporter sparkReporter
	private ExtentReports extent
	private ExtentTest extentTest
	private String executionSourceName = "TestCase"
	private String reportName
	private Path projectPath
	private Path reportPath

	/**
	 * @param testSuiteContext not used
	 */
	@Override
	void attachEReport(TestSuiteContext testSuiteContext,
			String documentTitle, String reportTitle,
			String projectDir = System.getProperty("user.dir")) {
		reportName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
		projectPath = Paths.get(projectDir)
		Path folderPath = projectPath.resolve("ExtentKz").resolve(reportName)
		if (!Files.exists(folderPath)) {
			try {
				Files.createDirectories(folderPath);
			} catch (IOException e) {
				System.err.println("Failed to create the folder: " + e.getMessage());
			}
		}
		reportPath = folderPath.resolve(reportTitle.replaceAll(" ", "_") + ".html")
		sparkReporter = new ExtentSparkReporter(reportPath.toString())
		sparkReporter.config().setDocumentTitle(documentTitle)
		sparkReporter.config().setReportName(reportTitle)
		sparkReporter.config().setTheme(Theme.STANDARD)
		sparkReporter.config().setTimelineEnabled(true)
		sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'")

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Operating System: ", System.getProperty("os.name"))
		extent.setSystemInfo("Java Version: ", System.getProperty("java.version"))
		extent.setSystemInfo("Host Address: ", RunConfiguration.getHostAddress())
		extent.setSystemInfo("Host Name: ", RunConfiguration.getHostName())
	}

	@Override
	void startEReport(TestCaseContext testCaseContext) {
		String browser = DriverFactory.getExecutedBrowser().getName()
		String execID = RunConfiguration.getExecutionSourceName()
		String testCaseName = testCaseContext.getTestCaseId()
				.substring(testCaseContext.getTestCaseId().lastIndexOf('/') + 1)
		extentTest = extent.createTest(execID + " : " + testCaseName, "Test Execution: " + testCaseContext.getTestCaseId())
		extentTest.assignAuthor("HOST: " + RunConfiguration.getHostName())
		extentTest.assignCategory("Browser: " + browser)
		extentTest.assignCategory("PROFILE: " + RunConfiguration.getExecutionProfile().toUpperCase())
		extentTest.assignCategory("GROUP: " + TestCaseFactory.findTestCase(testCaseContext.getTestCaseId()).getTag().toUpperCase())
	}

	@Override
	void flushEReport() {
		extent.flush()
	}

	@Override
	String getScreenshot() throws IOException {
		WebDriver driver = DriverFactory.getWebDriver()
		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null")
		}
		String date = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date())
		String fileName = "Screenshot_" + date + ".png"

		TakesScreenshot ts = (TakesScreenshot)driver
		Path tempScreenshot = ts.getScreenshotAs(OutputType.FILE).toPath()

		Path destinationDir = projectPath.resolve("ExtentKz").resolve(reportName).resolve("Screenshots")
		if (!Files.exists(destinationDir)) {
			Files.createDirectories(destinationDir)
		}
		Path finalDestination = destinationDir.resolve(fileName)
		Files.copy(tempScreenshot, finalDestination, StandardCopyOption.REPLACE_EXISTING)
		Files.delete(tempScreenshot)
		return finalDestination.toAbsolutePath().toString()
	}

	@Override
	void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException {
		if (testCaseContext.getTestCaseStatus().equalsIgnoreCase("FAILED")) {
			extentTest.log(Status.FAIL, "Failed Test Case is: " + testCaseContext.getTestCaseId()); // to add name in extent report
			extentTest.log(Status.FAIL, "****Error Message:***** " + testCaseContext.getMessage()); // to add error/exception in

			String screenshotPath = getScreenshot()
			//Add screenshot at the top
			//extentTest.log(Status.FAIL,"Screenshot Attached: " + testCaseContext.getTestCaseId()).addScreenCaptureFromPath(screenshotPath);
			extentTest.log(Status.FAIL, 'Failed Screenshot added.', MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build())
		} else if (testCaseContext.getTestCaseStatus() == ITestResult.SKIP) {
			extentTest.log(Status.SKIP, "Skiped Test Case is: " + testCaseContext.getTestCaseId());
		} else if (testCaseContext.getTestCaseStatus() == ITestResult.SUCCESS) {
			extentTest.log(Status.PASS, "Passed Test Case is: " + testCaseContext.getTestCaseId());
		}
	}

	@Override
	void addScreenshot(String s) {
		String screenshotpathLaunched = getScreenshot()
		extentTest.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(screenshotpathLaunched).build())
	}

	@Override
	void attachLog(String details) {
		extentTest.log(Status.PASS, details)
	}
	
	@Override
	Path getReportPath() {
		return reportPath
	}
	
	@Override
	String getReportContent() {
		
	}
}
