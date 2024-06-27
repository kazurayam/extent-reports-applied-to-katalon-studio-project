import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.maximizeWindow()

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('Test Starting')

WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('Browser opened https://katalon-demo-cura.herokuapp.com/')

CustomKeywords.'com.katalon.extent.report.ExtentReport.addScreenshot'()

String title = WebUI.getWindowTitle()

CustomKeywords.'com.katalon.extent.report.ExtentReport.attachLog'('Application Title is:  ' + title)

