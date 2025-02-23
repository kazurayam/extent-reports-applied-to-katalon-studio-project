import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.authenticate("http://localhost:5000", 'admin', 'password123', 12)