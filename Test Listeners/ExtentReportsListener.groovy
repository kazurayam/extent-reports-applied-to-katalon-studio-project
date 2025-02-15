import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class ExtentReportsListener {

	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		//CustomKeywords.'com.kazurayam.ks.ExtentReportsKeyword.deleteFolderContents'()
		CustomKeywords.'com.kazurayam.ks.ExtentReportsKeyword.attachEReport'(testSuiteContext, "Extent Report", "KSE QA Test Report")
	}

	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		CustomKeywords.'com.kazurayam.ks.ExtentReportsKeyword.startEReport'(testCaseContext)
	}

	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) throws IOException {
		CustomKeywords.'com.kazurayam.ks.ExtentReportsKeyword.takeScreenshotFailure'(testCaseContext)
	}

	@AfterTestSuite
	def sampleAfterTestSuite() {
		CustomKeywords.'com.kazurayam.ks.ExtentReportsKeyword.flushEReport'()
	}
}