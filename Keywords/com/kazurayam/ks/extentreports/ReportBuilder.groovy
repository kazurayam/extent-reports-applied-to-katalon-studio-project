package com.kazurayam.ks.extentreports

import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

abstract class ReportBuilder {

	abstract void attachEReport(TestSuiteContext testSuiteContext, String setDocumentTitle, String setReportTitle, String projectDir)

	abstract void startEReport(TestCaseContext testCaseContext)

	abstract void flushEReport()

	abstract String getScreenshot() throws IOException

	abstract void takeScreenshotFailure(TestCaseContext testCaseContext) throws IOException

	abstract void deleteFolderContents() throws IOException

	abstract void addScreenshot(String s)

	private boolean disable
	
	// method to get the instance of ReportBuilderOnKatalonImpl
	public static ReportBuilder getInstance() {
		// this instance will be instantiated at the initial call only.
		return ReportBuilderOnKatalonImpl.getInstance()
	}
	
	
}
