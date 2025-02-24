package com.kazurayam.ks.extentreports

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.extentreports.ReportBuilderKzImpl

@RunWith(JUnit4.class)
public class ReportBuilderKzImplTest {

	@Test
	void test_getInstance() {
		ReportBuilder instance = ReportBuilderKzImpl.getInstance()
		assertNotNull(instance)
	}
	
}
