package com.kazurayam.ks.extentreports

import com.kms.katalon.core.logging.KeywordLogger

public class ExtentReportsAwareKeywordLogger extends KeywordLogger {

	ExtentReportsAwareKeywordLogger() {
		super()
	}

	@Override
	public void logInfo(String message, Map<String, String> attributes) {
		println "ExtentReportsAwareKeywordLogger#logInfo(\"${message}\")"
		logger.info(message)
		xmlKeywordLogger.logInfo(this, message, attributes)
		ExtentSparkReportBuilder.getInstance().attachLog(message)
	}
}
