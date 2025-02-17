package com.kazurayam.ks.extentreports

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.kazurayam.ks.extentreports.WebUiBuiltInKeywordsLiaison as WebUiLiaison

@RunWith(JUnit4.class)
public class WebUiBuiltInKeywordsLiaisonTest {
	
	@Test
	void test_getInstance() {
		WebUiLiaison instance = WebUiLiaison.getInstance()
		assertNotNull(instance)
	}
	
}
