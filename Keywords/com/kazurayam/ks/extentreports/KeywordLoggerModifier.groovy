package com.kazurayam.ks.extentreports

import java.text.MessageFormat

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.constants.StringConstants
import com.kms.katalon.core.logging.KeywordLogger

/**
 * 
 * @author kazurayam
 */
class KeywordLoggerModifier {

	/**
	 * The constructor is declared private to avoid being called by "new KeywordLoggerModifier()"
	 */
	private KeywordLoggerModifier() {
		println "KeywordLoggerModifier#constructor was called"
		KeywordLogger.metaClass.invokeMethod = { String name, args ->
			println "KeywordLogger#${name} was called"
			if (name.equals("logInfo")) {
				def result
				try {
					result = delegate.metaClass.getMetaMethod(name, args).invoke(delegate, args)
				} catch (Exception e) {
					e.printStackTrace()
				}
				return result
			}
		}
	}

	/**
	 * The "Bill Pugh Singleton" pattern is applied.
	 * See https://www.baeldung.com/java-bill-pugh-singleton-implementation for detail.
	 */
	private static class SingletonHolder {
		private static final KeywordLoggerModifier INSTANCE =
			new KeywordLoggerModifier()
	}

	public static KeywordLoggerModifier getInstance() {
		return SingletonHolder.INSTANCE
	}

}