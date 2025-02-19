package com.kms.katalon.core.keyword.internal;

import org.apache.commons.lang.ObjectUtils

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject

public abstract class AbstractKeyword implements IKeyword {

    protected final KeywordLogger logger = KeywordLogger.getInstance(this.getClass());

    protected TestObject getTestObject(Object param) {
        if (param instanceof TestObject) {
            return (TestObject) param
        }
        return ObjectRepository.findTestObject(ObjectUtils.toString(param))
    }

    protected FailureHandling getFailureHandling(Object[] params, int index) {
        Object param = getParam(params, index)
        if (param instanceof FailureHandling) {
            return (FailureHandling) param
        }
        return RunConfiguration.getDefaultFailureHandling()
    }

    protected FailureHandling getFailureHandling(Object[] params) {
        for (Object paramI : params) {
            if (paramI instanceof FailureHandling) {
                return (FailureHandling) paramI;
            }
        }
        return RunConfiguration.getDefaultFailureHandling();
    }

    protected <Type> Type getOptionalParam(Object[] params, int index) {
        return getOptionalParam(params, index, null);
    }

    protected <Type> Type getOptionalParam(Object[] params, int index, Type defaultValue) {
        return getOptionalParam(params, index, defaultValue, null);
    }

    protected <Type> Type getOptionalParam(Object[] params, int index, Type defaultValue, Class<Type> clazz) {
        if (index >= params.length) {
            return defaultValue;
        }
        Object param = params[index];
        try {
            Class<Type> type = clazz == null && defaultValue != null ? defaultValue.getClass() : clazz;
            return type == null || type.isInstance(param) ? param : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected boolean getBooleanValue(Object[] params, int index) {
        Object param = getParam(params, index)
        return param instanceof Boolean && (boolean) param
    }

    protected Object getParam(Object[] params, int index) {
        try {
            return params[index]
        } catch(Exception e) {
            return null
        }
    }
}
