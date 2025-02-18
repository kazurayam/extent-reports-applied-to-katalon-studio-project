import com.kazurayam.ks.extentreports.ReportBuilder
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class ExtentReportsListener {

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		//ExtentReportsKeyword.getInstance().deleteFolderContents()
		ReportBuilder.getInstance().attachEReport(testSuiteContext, "Extent Report", "KSE QA Test Report")
	}

	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		ReportBuilder.getInstance().startEReport(testCaseContext)
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) throws IOException {
		ReportBuilder.getInstance().takeScreenshotFailure(testCaseContext)
	}

	@AfterTestSuite
	def afterTestSuite() {
		ReportBuilder.getInstance().flushEReport()
	}
	
}