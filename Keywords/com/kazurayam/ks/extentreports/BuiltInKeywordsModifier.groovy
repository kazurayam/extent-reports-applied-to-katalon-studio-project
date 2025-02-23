package com.kazurayam.ks.extentreports

import com.kms.katalon.core.keyword.BuiltinKeywords
import com.kazurayam.ks.extentreports.ReportBuilderKzImpl
import com.kms.katalon.core.keyword.internal.KeywordExecutor

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
	}
}
