package com.kazurayam.ks.extentreports

import java.text.MessageFormat

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.constants.StringConstants
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

class WebUiBuiltInKeywordsLiaison {

	private class SingletonHolder {
		private static final WebUiBuiltInKeywordsLiaison INSTANCE =
		new WebUiBuiltInKeywordsLiaison()
	}

	public static WebUiBuiltInKeywordsLiaison getInstance() {
		return SingletonHolder.INSTANCE
	}

	private WebUiBuiltingKeywordsExtentReportsLiaison() {
		WebUiBuiltInKeywords.metaClass.'static'.invokeMethod = { String name, args ->
			if (name in liaisedKeywords) {
				Closure cls = liaisedKeywords.get(name)
				cls.call(args)
			}
		}
	}

	private static Map<String, Closure> liaisedKeywords = [
		'click': { args ->
			TestObject to = (TestObject)args[0]
			ERAgent.getInstance().attachLog(
					MessageFormat.format(StringConstants.KW_LOG_INFO_CLICKING_ON_OBJ, to.getObjectId()))
		},
		'setText': { args ->
			TestObject to = (TestObject)args[0]
			String readableText = (String)args[1]
			ERAgent.getInstance().attachLog(
					MessageFormat.format(StringConstants.KW_LOG_INFO_CLEARING_OBJ_TXT, to.getObjectId()))
			ERAgent.getInstance().attachLog(
					MessageFormat.format(StringConstants.KW_LOG_INFO_SETTING_OBJ_TXT_TO_VAL, [
						to.getObjectId(),
						readableText
					] as Object[]))
		}
	]

	public static void liaise(Map<String, Closure> liaisons) {
		liaisedKeywords.putAll(liaisons)
	}

	public static void liaise() {
		liaise([:])
	}
}
