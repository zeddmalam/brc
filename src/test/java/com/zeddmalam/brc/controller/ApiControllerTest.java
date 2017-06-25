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
	 * Test of direct method, of class ApiController.
	 * Both route ids are null
	 */
	@Test
	public void testDirectIdsAreNull() {
		Integer depSid = null;
		Integer arrSid = null;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}

	/**
	 * Test of direct method, of class ApiController.
	 * Existing direct route with correct direction
	 */
	@Test
	public void testDirectCorrectRoute() {
		Integer depSid = 5;
		Integer arrSid = 138;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(true, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}

	/**
	 * Test of direct method, of class ApiController.
	 * Not existing direct route.
	 */
	@Test
	public void testDirectNoDirectRoute() {
		Integer depSid = 5;
		Integer arrSid = 17;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}

	/**
	 * Test of direct method, of class ApiController.
	 * Arrival stop id does not exists.
	 */
	@Test
	public void testDirectNoArrival() {
		Integer depSid = 5;
		Integer arrSid = 17000;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}
	
	/**
	 * Test of direct method, of class ApiController.
	 * Departure stop id does not exists.
	 */
	@Test
	public void testDirectNoDeparture() {
		Integer depSid = 50000;
		Integer arrSid = 17;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}
	
	/**
	 * Test of direct method, of class ApiController.
	 * Both stop ids does not exists.
	 */
	@Test
	public void testDirectNoDepartureNoArrival() {
		Integer depSid = 50000;
		Integer arrSid = 17000;
		
		DirectResponse result = apiController.direct(depSid, arrSid);
		assertEquals(false, result.getDirectBusRoute());
		assertEquals(depSid, result.getDepSid());
		assertEquals(arrSid, result.getArrSid());
	}
	
}
