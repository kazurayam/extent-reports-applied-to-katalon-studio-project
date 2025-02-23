import com.kazurayam.ks.extentreports.KeywordLoggerModifier
import com.kazurayam.ks.extentreports.ExtentSparkReportBuilder
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kazurayam.ks.extentreports.BuiltinKeywordsModifier

class ExtentReportsListener {
	
	boolean runAsTestSuite = false

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		ExtentSparkReportBuilder.getInstance().attachEReport(testSuiteContext, "Extent Report", "KSE QA Test Report")
		BuiltinKeywordsModifier.apply()
		runAsTestSuite = true
	}

	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		if (!runAsTestSuite) {
			ExtentSparkReportBuilder.getInstance().attachEReport(null, "Extent Report", "KSE QA Test Report")
		}
		ExtentSparkReportBuilder.getInstance().startEReport(testCaseContext)
		BuiltinKeywordsModifier.apply()
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) throws IOException {
		ExtentSparkReportBuilder.getInstance().takeScreenshotFailure(testCaseContext)
		ExtentSparkReportBuilder.getInstance().flushEReport()
	}

	@AfterTestSuite
	def afterTestSuite() {
		ExtentSparkReportBuilder.getInstance().flushEReport()
	}
	
}