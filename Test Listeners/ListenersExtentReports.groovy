
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


class ExtentReportsListenres {

	@BeforeTestSuite
	def aaadeleteHtmlReport() {
		Path folderPath = Paths.get(RunConfiguration.getProjectDir()+"/Extent/");
		try {
			// Delete everything within the folder
			CustomKeywords.'com.katalon.extent.report.ExtentReport.deleteFolderContents'(folderPath)
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		CustomKeywords.'com.katalon.extent.report.ExtentReport.attachReport'(testSuiteContext, "Extent Report", "PWC QA Test Report")
	}


	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {

		CustomKeywords.'com.katalon.extent.report.ExtentReport.startReport'(testCaseContext)
	}


	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) throws IOException {
		CustomKeywords.'com.katalon.extent.report.ExtentReport.takeScreenshotFailure'(testCaseContext)
	}

	@AfterTestSuite
	def sampleAfterTestSuite() {
		CustomKeywords.'com.katalon.extent.report.ExtentReport.flushReport'()
	}
}