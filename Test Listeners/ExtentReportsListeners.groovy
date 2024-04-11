
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.text.SimpleDateFormat;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver


class ExtentReportsListeners {

	/*
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest extentTest;

	@BeforeTestSuite
	def aaadeleteHtmlReport() {
		Path folderPath = Paths.get(RunConfiguration.getProjectDir()+"/Extent/");
		try {
			// Delete everything within the folder
			deleteFolderContents(folderPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()

		sparkReporter = new ExtentSparkReporter(RunConfiguration.getProjectDir()+"/Extent/extentReport.html");
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Test Execution Report");
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setTimelineEnabled(true)
		sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'")

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Operating System: ", System.getProperty("os.name"))
		extent.setSystemInfo("Java Version: ", System.getProperty("java.version"))
		extent.setSystemInfo("HostAddress: ", RunConfiguration.getHostAddress())
		extent.setSystemInfo("HostName: ", RunConfiguration.getHostName())
	}

	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		String driverString = DriverFactory.getExecutedBrowser().getName()
		String execID = RunConfiguration.getExecutionSourceName()
		String testcasename = testCaseContext.getTestCaseId().substring(testCaseContext.getTestCaseId().lastIndexOf('/') + 1)
		extentTest = extent.createTest(execID+" : "+testcasename, "Test Execution: "+testCaseContext.getTestCaseId());
		extentTest.assignAuthor("HOST: "+RunConfiguration.getHostName().toUpperCase())
		extentTest.assignCategory("BROWSER: "+driverString.substring(0, driverString.indexOf("_")))
		extentTest.assignCategory("PROFILE: "+RunConfiguration.getExecutionProfile().toUpperCase())
		extentTest.assignCategory("GROUP: "+TestCaseFactory.findTestCase(testCaseContext.getTestCaseId()).getTag().toUpperCase())
	}


	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) throws IOException {
		tearDownTakeScreenshotFailure(testCaseContext);
	}

	@AfterTestSuite
	def sampleAfterTestSuite() {
		extent.flush();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		// Ensure a valid WebDriver instance is provided
		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}
		String dateName = new SimpleDateFormat("yyMMddHHmm").format(new Date());
		String fileName = screenshotName + "_" + dateName + ".png";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File tempScreenshot = ts.getScreenshotAs(OutputType.FILE);
		File destinationDir = new File(RunConfiguration.getProjectDir() + "/Extent/Screenshots");
		if (!destinationDir.exists()) {
			destinationDir.mkdirs(); // Create directories recursively
		}

		File finalDestination = new File(destinationDir, fileName);

		FileUtils.copyFile(tempScreenshot, finalDestination);

		tempScreenshot.delete();
		return finalDestination.getAbsolutePath();
	}

	public void tearDownTakeScreenshotFailure(TestCaseContext testCaseContext) throws IOException {
		WebDriver driver = ((SmartWaitWebDriver)DriverFactory.getWebDriver()).getWrappedDriver()
		if (testCaseContext.getTestCaseStatus().equalsIgnoreCase("FAILED")) {
			extentTest.log(Status.FAIL, "Failed Test Case is: " + testCaseContext.getTestCaseId()); // to add name in extent report
			extentTest.log(Status.FAIL, "****Error Message:***** " + testCaseContext.getMessage()); // to add error/exception in

			String screenshotPath = getScreenshot(driver, testCaseContext.getTestCaseId());
			//Add screenhot at the top
			//extentTest.log(Status.FAIL,"Screenshot Attached: " + testCaseContext.getTestCaseId()).addScreenCaptureFromPath(screenshotPath);
			extentTest.log(Status.FAIL, 'Screenshot Attached: ', MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build())
		} else if (testCaseContext.getTestCaseStatus() == ITestResult.SKIP) {
			extentTest.log(Status.SKIP, "Skiped Test Case is: " + testCaseContext.getTestCaseId());
		} else if (testCaseContext.getTestCaseStatus() == ITestResult.SUCCESS) {
			extentTest.log(Status.PASS, "Passed Test Case is: " + testCaseContext.getTestCaseId());
		}

		//extent.endTest(extentTest); // ending test and ends the current test and prepare to create html report
		//driver.quit();
	}

	public static void deleteFolderContents(Path folderPath) throws IOException {
		Files.walkFileTree(folderPath, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						Files.delete(file); // Delete file
						return FileVisitResult.CONTINUE;
					}
					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						if (exc == null) {
							Files.delete(dir); // Delete directories
							return FileVisitResult.CONTINUE;
						} else {
							throw exc;
						}
					}
				});
	}
	*/
}