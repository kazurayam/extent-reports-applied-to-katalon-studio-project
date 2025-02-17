package com.kazurayam.ks.extentreports

import com.katalon.extent.report.ExtentReport
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

/**
 * Wraps an instance of com.katalon.extent.report.ExtentReport --- the interface to the Extent Reports framework.
 * Is a Singleton instantiated when the getInstance() is called first.
 * 
 * @author kazuayam
 */
class ERAgent {

	// https://qiita.com/chika_s_it/items/b7a90dd2c262e5ee0ada#bill-pugh-singleton
	// class to hold the instance as final
	private class SingletonHolder {
		private static final ERAgent INSTANCE = new ERAgent()
	}

	// the ExtentReport class was developed and published at https://github.com/coty/extent-report-sample
	private ExtentReport component;

	// method to get the instance
	public static ERAgent getInstance() {
		// this instance will be instantiated at the initial call only.
		return SingletonHolder.INSTANCE
	}

	// private constructor to prevent instantiation from other classes
	private ERAgent() {
		component = new ExtentReport()
	}

	def attachEReport(TestSuiteContext testSuiteContext, String setDocumentTitle, String setReportTitle, String projectDir = System.getProperty("user.dir")) {
		component.attachEReport(testSuiteContext, setDocumentTitle, setReportTitle, projectDir)
	}

	def startEReport(TestCaseContext testCaseContext) {
		component.startEReport(testCaseContext)
		// customize the report!
		if (component.extentTest != null) {
			component.extentTest.assignCategory("GIT BRANCH: " + getGitBranch())
		}
	}

	private String getGitBranch() {
		return "master"
	}

	def flushEReport() {
		component.flushEReport()
	}

	public static String getScreenshot() throws IOException {
		ExtentReport.getScreenshot()
	}

	public void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException {
		component.takeScreenshotFailure(testCaseContext)
	}

	public static void deleteFolderContents() throws IOException {
		ExtentReport.deleteFolderContents()
	}

	public void addScreenshot(String s) {
		component.addScreenshot(s)
	}

	public void attachLog(String details) {
		component.attachLog(details)
	}
}