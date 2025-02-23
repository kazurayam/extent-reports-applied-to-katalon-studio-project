package com.kazurayam.ks.extentreports

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.apache.commons.io.FileUtils;
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
 * This class was developed in order to perform unit-tests over 
 *     com.kazurayam.ks.extentreports.ExpandoKeywordLogger class using junit4.
 * 
 * You should use this classjust for testing the ExpandoKeywordLogger.
 * 
 * This class is a mimic of com.katalon.extent.report.ExtentReport class but a slight modification.
 *     The original class works only if it is invoked in a Test Suite contect;
 *     it does not work in a Test Case context. 
 *     Therefore the original class is difficult to use with JUnit4.
 *     I wanted a simpler implementation of ReportBuilder that can work in 
 *     a Test Case Context. So I developed this.
 * 
 * You should prefer ReportBuilderOnKatalonImpl if you want to use
 * the "Extent Reports integration plugin" provided by Katalon.
 * 
 * @author kazurayam
 */
public class ExtentSparkReportBuilder extends ReportBuilder {

	private class SingletonHolder {
		private static final ExtentSparkReportBuilder INSTANCE = new ExtentSparkReportBuilder()
	}

	public static ExtentSparkReportBuilder getInstance() {
		return SingletonHolder.INSTANCE
	}

	private ExtentSparkReportBuilder() {}

	private ExtentSparkReporter sparkReporter
	private ExtentReports extent
	private ExtentTest extentTest
	private String executionSourceName = "TestCase"
	private String reportName
	private String projectPath

	/**
	 * @param testSuiteContext not used
	 */
	@Override
	void attachEReport(TestSuiteContext testSuiteContext,
			String documentTitle, String reportTitle,
			String projectDir = System.getProperty("user.dir")) {
		reportName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
		projectPath = projectDir
		String folderPath = projectPath + "/Extent/" + reportName
		if (!Files.exists(Paths.get(folderPath))) {
			try {
				Files.createDirectories(Paths.get(folderPath));
			} catch (IOException e) {
				System.err.println("Failed to create the folder: " + e.getMessage());
			}
		}
		Path htmlPath = Paths.get(folderPath).resolve(reportTitle.replaceAll(" ", "").toLowerCase() + ".html")
		sparkReporter = new ExtentSparkReporter(htmlPath.toString())
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
		File tempScreenshot = ts.getScreenshotAs(OutputType.FILE)
		File destinationDir = new File(projectPath + "/Extent/" + reportName + "/Screenshots")
		if (!destinationDir.exists()) {
			destinationDir.mkdirs()
		}
		File finalDestination = new File(destinationDir, fileName)
		FileUtils.copyFile(tempScreenshot, finalDestination)
		tempScreenshot.delete()
		return finalDestination.getAbsolutePath()
	}

	@Override
	void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException {
		if (testCaseContext.getTestCaseStatus().equalsIgnoreCase("FAILED")) {
			extentTest.log(Status.FAIL, "Failed Test Case is: " + testCaseContext.getTestCaseId()); // to add name in extent report
			extentTest.log(Status.FAIL, "****Error Message:***** " + testCaseContext.getMessage()); // to add error/exception in

			String screenshotPath = getScreenshot()
			//Add screenhot at the top
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
}
