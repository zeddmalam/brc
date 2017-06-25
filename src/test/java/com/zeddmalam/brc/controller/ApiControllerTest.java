package com.zeddmalam.brc.controller;

import com.zeddmalam.brc.config.AppConfig;
import com.zeddmalam.brc.model.DirectResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test for @ApiController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class ApiControllerTest {
	
	@Autowired
	@Qualifier("apiController")
    ApiController apiController;
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of direct method, of class DefaultController.
	 */
	@Test
	public void testDirect() {
		/**
		 * both route ids are null
		 */
		Integer depSid = null;
		Integer arrSid = null;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
		
		/**
		 * existing direct route with correct direction
		 */
		depSid = 5;
		arrSid = 138;
		
		result = apiController.direct(depSid, arrSid);
		assertEquals(true, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());

		/**
		 * existing direct route with incorrect direction
		 */
		depSid = 138;
		arrSid = 5;
		
		result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());

		/**
		 * not existing direct route
		 */
		depSid = 5;
		arrSid = 17;
		
		result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());

		/**
		 * arrival stop id does not exists
		 */
		depSid = 5;
		arrSid = 17000;
		
		result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());

		/**
		 * departure stop id does not exists
		 */
		depSid = 50000;
		arrSid = 17;
		
		result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());

		/**
		 * both stop ids does not exists
		 */
		depSid = 50000;
		arrSid = 17000;
		
		result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}
	
}
