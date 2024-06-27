
import java.nio.file.Path
import java.nio.file.Paths;

import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext


class ExtentReportsListenres {

	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		//CustomKeywords.'com.katalon.extent.report.ExtentReport.deleteFolderContents'()
		CustomKeywords.'com.katalon.extent.report.ExtentReport.attachEReport'(testSuiteContext, "Extent Report", "KSE QA Test Report")
	}


	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		CustomKeywords.'com.katalon.extent.report.ExtentReport.startEReport'(testCaseContext)
	}


	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) throws IOException {
		CustomKeywords.'com.katalon.extent.report.ExtentReport.takeScreenshotFailure'(testCaseContext)
	}

	@AfterTestSuite
	def sampleAfterTestSuite() {
		CustomKeywords.'com.katalon.extent.report.ExtentReport.flushEReport'()
	}
}