package com.kazurayam.ks.extentreports

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.kazurayam.ks.extentreports.ERAgent

@RunWith(JUnit4.class)
public class ERAgentTest {

	@Test
	void test_getInstance() {
		ERAgent instance = ERAgent.getInstance()
		assertNotNull(instance)
	}
}
