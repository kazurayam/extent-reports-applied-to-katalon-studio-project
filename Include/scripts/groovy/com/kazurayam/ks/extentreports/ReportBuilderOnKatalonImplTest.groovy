package com.kazurayam.ks.extentreports

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.extentreports.ReportBuilder

@RunWith(JUnit4.class)
public class ReportBuilderOnKatalonImplTest {

	@Test
	void test_getInstance() {
		ReportBuilder instance = ReportBuilderOnKatalonImpl.getInstance()
		assertNotNull(instance)
	}
}
