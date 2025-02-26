package com.kazurayam.ks.extentreports

import com.kms.katalon.core.keyword.BuiltinKeywords
import com.kazurayam.ks.extentreports.ReportBuilderKzImpl
import com.kms.katalon.core.keyword.internal.KeywordExecutor
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling

public class BuiltInKeywordsModifier {

	private BuiltInKeywordsModifier() {}

	public static apply() {
		modifyPlatformBuiltin()
	}

	private static void modifyPlatformBuiltin() {
		BuiltinKeywords.metaClass.'static'.comment = { String message ->
			KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_BUILT_IN, "comment", message)
			ReportBuilderKzImpl.getInstance().attachLog(message)
		}
		BuiltinKeywords.metaClass.'static'.verifyEqual = { Object actualObject, Object expectedObject, FailureHandling flowControl ->
			try {
				return (boolean) KeywordExecutor.executeKeywordForPlatform(KeywordExecutor.PLATFORM_BUILT_IN, "verifyEqual", actualObject, expectedObject)
			} catch (Exception e) {
				ReportBuilderKzImpl.getInstance().attachLog(e.getMessage())
				throw e
			}
			
		}
	}
}
