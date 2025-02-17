import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.kazurayam.ks.extentreports.ERAgent
import com.kazurayam.ks.extentreports.WebUiBuiltInKeywordsLiaison as WebUiLiaison
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class ExtentReportsListener {

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		initializeLiaisons()
		//ExtentReportsKeyword.getInstance().deleteFolderContents()
		ERAgent.getInstance().attachEReport(testSuiteContext, "Extent Report", "KSE QA Test Report")
	}

	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		initializeLiaisons()
		ERAgent.getInstance().startEReport(testCaseContext)
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) throws IOException {
		ERAgent.getInstance().takeScreenshotFailure(testCaseContext)
	}

	@AfterTestSuite
	def afterTestSuite() {
		ERAgent.getInstance().flushEReport()
	}
	
	private void initializeLiaisons() {
		WebUiLiaison.liaise([:])
		WebUiLiaison INSTANCE = WebUiLiaison.getInstance()  
	}
}