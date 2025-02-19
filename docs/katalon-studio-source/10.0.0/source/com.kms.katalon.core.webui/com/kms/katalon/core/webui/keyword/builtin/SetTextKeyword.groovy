package com.kms.katalon.core.webui.keyword.builtin

import java.text.MessageFormat

import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.internal.Action
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.keyword.internal.SupportLevel
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.constants.StringConstants
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.webui.keyword.internal.WebUIKeywordMain
import com.kms.katalon.selenium.util.SeleniumKeysUtil;

import groovy.transform.CompileStatic

@Action(value = "setText")
public class SetTextKeyword extends WebUIAbstractKeyword {

    @CompileStatic
    @Override
    public SupportLevel getSupportLevel(Object ...params) {
        return super.getSupportLevel(params)
    }

    @CompileStatic
    @Override
    public Object execute(Object ...params) {
        TestObject to = getTestObject(params[0])
        String text = (String) params[1]
        FailureHandling flowControl = (FailureHandling)(params.length > 2 && params[2] instanceof FailureHandling ? params[2] : RunConfiguration.getDefaultFailureHandling())
        setText(to,text,flowControl)
    }

    @CompileStatic
    public void setText(TestObject to, String text, FailureHandling flowControl) throws StepFailedException {
        WebUIKeywordMain.runKeyword({
            boolean isSwitchIntoFrame = false
            try {
                WebUiCommonHelper.checkTestObjectParameter(to)
                logger.logDebug(StringConstants.KW_LOG_INFO_CHECKING_TXT)
                if (text == null) {
                    throw new IllegalArgumentException(StringConstants.KW_EXC_TXT_IS_NULL)
                }
                isSwitchIntoFrame = WebUiCommonHelper.switchToParentFrame(to)
                WebElement webElement = WebUIAbstractKeyword.findWebElement(to)
                logger.logDebug(MessageFormat.format(StringConstants.KW_LOG_INFO_CLEARING_OBJ_TXT, to.getObjectId()))
                webElement.clear()
                // Since WebElement.clear() may trigger side effect that makes the element become stale, find it again
                webElement = WebUIAbstractKeyword.findWebElement(to)

                boolean isRichTextEditor = false;
                if(StringUtils.isNotEmpty(webElement.getAttribute("contenteditable"))) {
                    isRichTextEditor = Boolean.valueOf(webElement.getAttribute("contenteditable"))
                }

                String readableText = SeleniumKeysUtil.getReadableText(text)

                logger.logDebug(MessageFormat.format(StringConstants.KW_LOG_INFO_SETTING_OBJ_TXT_TO_VAL, [
                    to.getObjectId(),
                    readableText
                ] as Object[]))
                if(!isRichTextEditor) {
                    webElement.sendKeys(text)
                } else {
                    if (DriverFactory.getWebDriver() instanceof JavascriptExecutor) {
                        ((JavascriptExecutor)DriverFactory.getWebDriver()).executeScript('arguments[0].innerHTML = arguments[1];', webElement, text)
                    }
                }
                logger.logPassed(MessageFormat.format(StringConstants.KW_LOG_PASSED_TXT_IS_SET_ON_OBJ, [
                    readableText,
                    to.getObjectId()
                ] as Object[]))
            } finally {
                if (isSwitchIntoFrame) {
                    WebUiCommonHelper.switchToDefaultContent()
                }
            }
        }, flowControl, RunConfiguration.getTakeScreenshotOption(), (to != null) ? MessageFormat.format(StringConstants.KW_MSG_CANNOT_SET_TXT_X_OF_OBJ_Y, [text, to.getObjectId()] as Object[])
        : StringConstants.KW_MSG_CANNOT_SET_TXT)
    }
}
