package com.kazurayam.ks.extentreports

import com.kms.katalon.core.keyword.BuiltinKeywords
import com.kazurayam.ks.extentreports.ExtentSparkReportBuilder
import com.kms.katalon.core.keyword.internal.KeywordExecutor

public class BuiltinKeywordsModifier {

	private BuiltinKeywordsModifier() {}

	public static apply() {
		modifyPlatformBuiltin()
	}
	
	private static void modifyPlatformBuiltin() {
		BuiltinKeywords.metaClass.'static'.comment = { String message ->
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_BUILT_IN, "comment", message)
			ExtentSparkReportBuilder.getInstance().attachLog(message)
		}
	}
}
