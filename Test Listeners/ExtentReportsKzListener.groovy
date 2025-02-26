import com.kazurayam.ks.extentreports.ReportBuilderKzImpl
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kazurayam.ks.extentreports.BuiltInKeywordsModifier
import com.kazurayam.ks.extentreports.KeywordMainModifier

class ExtentReportsKzListener {
	
	boolean runAsTestSuite = false

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		ReportBuilderKzImpl.getInstance().attachEReport(testSuiteContext, 
			/* documentTitle: */ "Extent Report", 
			/* reportTitle: */ "KSE QA Test Report")
		KeywordMainModifier.apply()
		BuiltInKeywordsModifier.apply()
		runAsTestSuite = true
	}

	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		if (!runAsTestSuite) {
			ReportBuilderKzImpl.getInstance().attachEReport(null, 
				/* documentTitle: */ "Extent Report", 
				/* reportTitle: */ "KSE QA Test Report")
		}
		ReportBuilderKzImpl.getInstance().startEReport(testCaseContext)
		KeywordMainModifier.apply()
		BuiltInKeywordsModifier.apply()
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) throws IOException {
		//ReportBuilderKzImpl.getInstance().takeScreenshotFailure(testCaseContext)   // may throw com.kms.katalon.core.webui.exception.BrowserNotOpenedException
		ReportBuilderKzImpl.getInstance().flushEReport()
	}

	@AfterTestSuite
	def afterTestSuite() {
		ReportBuilderKzImpl.getInstance().flushEReport()
	}

}