import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.maximizeWindow()

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('Test Starting')

WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'("Browser opened https://katalon-demo-cura.herokuapp.com/")

CustomKeywords.'com.katalon.extent.report.ExtentReport.addScreenshot'()

String title = WebUI.getWindowTitle()

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('Application Title is:  ' + title)

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/a_Make Appointment'))

WebUI.verifyMatch(title, 'hello', false)

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/span_Demo account_demo_username_label'))

WebUI.setText(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/input_Username_username'), 'John Doe')

WebUI.setEncryptedText(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/input_Password_password'), 
    'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/button_Login'))

WebUI.selectOptionByValue(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/select_Tokyo CURA Healthcare Center        _5b4107'), 
    'Hongkong CURA Healthcare Center', true)

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/input_Apply for hospital readmission_hospit_63901f'))

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/input_Visit Date (Required)_visit_date'))

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/td_30'))

WebUI.setText(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/textarea_Comment_comment'), 'test demo')

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/button_Book Appointment'))

WebUI.click(findTestObject('Object Repository/OR Web/Page_CURA Healthcare Service/a_Go to Homepage'))

WebUI.closeBrowser()