package com.kazurayam.ks.extentreports

import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.extentreports.ExpandoWebUiBuiltInKeywords as Expando

@RunWith(JUnit4.class)
public class ExpandoWebUiBuiltInKeywordsTest {

	@Test
	void test_getInstance() {
		Expando instance = Expando.getInstance()
		assertNotNull(instance)
	}
}
