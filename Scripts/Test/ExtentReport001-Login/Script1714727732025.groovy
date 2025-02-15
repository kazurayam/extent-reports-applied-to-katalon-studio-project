import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/a_Make Appointment'))

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('a_Make Appointment')

CustomKeywords.'com.katalon.extent.report.ExtentReport.addScreenshot'()

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/span_Demo account_demo_username_label'))

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('span_Demo account_demo_username_label')

WebUI.setText(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/input_Username_username'), user)

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('User name : John Doe')

WebUI.setText(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/input_Password_password'), pass)

WebUI.click(findTestObject('OR Web/Page_CURA Healthcare Service/button_Login'))

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('Login')

