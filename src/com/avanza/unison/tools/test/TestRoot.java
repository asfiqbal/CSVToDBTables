package com.avanza.unison.tools.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.avanza.unison.tools.Root;

class TestRoot {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

//	@Test
//	void testShowLicense() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testProcessInputFile() {
//		fail("Not yet implemented");
//	}

	@Test
	void testMain() {
	    System.out.println("main");
	    String[] args = new String[] {"D:\\Work\\CRM-OB\\Tools\\Import.csv", "D:\\Work\\CRM-OB\\Tools\\out.sql"};
	    Root.main(args);
	}

}
