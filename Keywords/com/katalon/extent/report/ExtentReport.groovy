package com.katalon.extent.report

import java.nio.file.FileVisitResult;
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.ITestResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.SmartWaitWebDriver
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


public class ExtentReport {
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static String getExecutionSourceName="TestCase"
	public static String reportName;
	public static String projectPath;

	@Keyword
	def attachEReport(TestSuiteContext testSuiteContext, String setDocumentTitle, String setReportTitle, String projectDir = System.getProperty("user.dir")) {
		getExecutionSourceName = RunConfiguration.getExecutionSourceId()
		if(getExecutionSourceName.startsWith("Test Suite")) {
			reportName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			projectPath =projectDir
			String folderPath = projectPath + "/Extent/"+reportName;
			if (!Files.exists(Paths.get(folderPath))) {
				try {

					Files.createDirectories(Paths.get(folderPath));
				} catch (IOException e) {
					System.err.println("Failed to create the folder: " + e.getMessage());
				}
			}
			sparkReporter = new ExtentSparkReporter(folderPath.toString()+"/"+setReportTitle.replaceAll(" ", "").toLowerCase()+".html");
			sparkReporter.config().setDocumentTitle(setDocumentTitle);
			sparkReporter.config().setReportName(setReportTitle);
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setTimelineEnabled(true)
			sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'")

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Operating System: ", System.getProperty("os.name"))
			extent.setSystemInfo("Java Version: ", System.getProperty("java.version"))
			extent.setSystemInfo("Host Address: ", RunConfiguration.getHostAddress())
			extent.setSystemInfo("Host Name: ", RunConfiguration.getHostName())
		}
	}

	@Keyword
	def startEReport(TestCaseContext testCaseContext) {
		if(getExecutionSourceName.startsWith("Test Suite")) {
			String driverString = DriverFactory.getExecutedBrowser().getName()
			String execID = RunConfiguration.getExecutionSourceName()
			String testcasename = testCaseContext.getTestCaseId().substring(testCaseContext.getTestCaseId().lastIndexOf('/') + 1)
			extentTest = extent.createTest(execID+" : "+testcasename, "Test Execution: "+testCaseContext.getTestCaseId());
			extentTest.assignAuthor("HOST: "+RunConfiguration.getHostName().toUpperCase())
			extentTest.assignCategory("BROWSER: "+driverString.substring(0, driverString.indexOf("_")))
			extentTest.assignCategory("PROFILE: "+RunConfiguration.getExecutionProfile().toUpperCase())
			extentTest.assignCategory("GROUP: "+TestCaseFactory.findTestCase(testCaseContext.getTestCaseId()).getTag().toUpperCase())
		}
		else {
			WebUI.comment("To Generate an Extent Report execute tests from the Test Suite.")
		}
	}

	@Keyword
	def flushEReport() {
		if(getExecutionSourceName.startsWith("Test Suite")) {
			println(getExecutionSourceName)
			extent.flush();
		}
	}

	@Keyword
	public static String getScreenshot() throws IOException {
		if(getExecutionSourceName.startsWith("Test Suite")) {
			WebDriver driver = ((SmartWaitWebDriver)DriverFactory.getWebDriver()).getWrappedDriver()
			if (driver == null) {
				throw new IllegalArgumentException("WebDriver instance cannot be null");
			}
			String dateName = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
			String fileName = "Screenshot_" + dateName + ".png";
			TakesScreenshot ts = (TakesScreenshot) driver;
			File tempScreenshot = ts.getScreenshotAs(OutputType.FILE);
			File destinationDir = new File(projectPath + "/Extent/"+reportName+"/Screenshots");
			if (!destinationDir.exists()) {
				destinationDir.mkdirs(); // Create directories recursively
			}

			File finalDestination = new File(destinationDir, fileName);

			FileUtils.copyFile(tempScreenshot, finalDestination);

			tempScreenshot.delete();
			return finalDestination.getAbsolutePath();
		}
	}

	@Keyword
	public void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException {
		if(getExecutionSourceName.startsWith("Test Suite")) {
			if (testCaseContext.getTestCaseStatus().equalsIgnoreCase("FAILED")) {
				extentTest.log(Status.FAIL, "Failed Test Case is: " + testCaseContext.getTestCaseId()); // to add name in extent report
				extentTest.log(Status.FAIL, "****Error Message:***** " + testCaseContext.getMessage()); // to add error/exception in

				String screenshotPath = getScreenshot();
				//Add screenhot at the top
				//extentTest.log(Status.FAIL,"Screenshot Attached: " + testCaseContext.getTestCaseId()).addScreenCaptureFromPath(screenshotPath);
				extentTest.log(Status.FAIL, 'Failed Screenshot added.', MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build())
			} else if (testCaseContext.getTestCaseStatus() == ITestResult.SKIP) {
				extentTest.log(Status.SKIP, "Skiped Test Case is: " + testCaseContext.getTestCaseId());
			} else if (testCaseContext.getTestCaseStatus() == ITestResult.SUCCESS) {
				extentTest.log(Status.PASS, "Passed Test Case is: " + testCaseContext.getTestCaseId());
			}

			//extent.endTest(extentTest); // ending test and ends the current test and prepare to create html report
			//driver.quit();
		}
		else {
			WebUI.comment("To Generate an Extent Report execute tests from the Test Suite.")
		}
	}

	@Keyword
	public static void deleteFolderContents() throws IOException {
		getExecutionSourceName = RunConfiguration.getExecutionSourceId()
		Path folderPath = Paths.get(projectPath+"/Extent/");
		if(getExecutionSourceName.startsWith("Test Suite")) {
			if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
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
		}
	}

	@Keyword
	public void addScreenshot(String) {
		if(getExecutionSourceName.startsWith("Test Suite")) {
			String screenshotpathLaunched = ExtentReport.getScreenshot()
			extentTest.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(screenshotpathLaunched).build())
		}
	}

	@Keyword
	public void attachLog(String details) {
		if(getExecutionSourceName.startsWith("Test Suite")) {
			extentTest.log(Status.PASS, details)
		}
	}
}