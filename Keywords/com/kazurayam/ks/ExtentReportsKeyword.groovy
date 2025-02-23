package com.kazurayam.ks

import com.katalon.extent.report.ExtentReport
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class ExtentReportsKeyword {

	// the original is found at https://github.com/coty/extent-report-sample
	private ExtentReport component;

	ExtentReportsKeyword() {
		component = new ExtentReport()
	}

	@Keyword
	def attachEReport(TestSuiteContext testSuiteContext, String setDocumentTitle, String setReportTitle, String projectDir = System.getProperty("user.dir")) {
		component.attachEReport(testSuiteContext, setDocumentTitle, setReportTitle, projectDir)
	}

	@Keyword
	def startEReport(TestCaseContext testCaseContext) {
		component.startEReport(testCaseContext)
		// customize the report!
		component.extentTest.assignCategory("GIT BRANCH: " + getGitBranch())
	}

	private String getGitBranch() {
		return "master"
	}

	@Keyword
	def flushEReport() {
		component.flushEReport()
	}

	@Keyword
	public static String getScreenshot() throws IOException {
		ExtentReport.getScreenshot()
	}

	@Keyword
	public void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException {
		component.takeScreenshotFailure(testCaseContext)
	}

	@Keyword
	public static void deleteFolderContents() throws IOException {
		ExtentReport.deleteFolderContents()
	}

	@Keyword
	public void addScreenshot(String s) {
		component.addScreenshot(s)
	}

	@Keyword
	public void attachLog(String details) {
		component.attachLog(details)
	}
}