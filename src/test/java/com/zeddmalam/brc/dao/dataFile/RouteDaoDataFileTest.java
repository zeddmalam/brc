package com.zeddmalam.brc.dao.dataFile;

import com.zeddmalam.brc.config.AppConfig;
import com.zeddmalam.brc.model.DirectResponse;
import com.zeddmalam.brc.model.Route;
import com.zeddmalam.brc.dao.RouteDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test for @RouteDaoDataFile
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class RouteDaoDataFileTest {
	
	@Autowired
	RouteDao routeDao;
	
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
	 * Test of findDirect method, of class RouteDaoDataFile. 
	 * Both route ids are null
	 */
	@Test
	public void testFindDirectIdsAreNull() {
		Integer depSid = null;
		Integer arrSid = null;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
	}
	
	/**
	 * Test of findDirect method, of class RouteDaoDataFile.
	 * Existing direct route with correct direction.
	 */
	@Test
	public void testFindDirectCorrectRoute() {
		Integer depSid = 5;
		Integer arrSid = 138;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNotNull(result);
		assertTrue(result.getSids().indexOf(depSid) > -1);
		assertTrue(result.getSids().indexOf(arrSid) > -1);
		assertTrue(result.getSids().indexOf(depSid) < result.getSids().indexOf(arrSid));
	}
	
	/**
	 * Test of findDirect method, of class RouteDaoDataFile.
	 * Existing direct route with incorrect direction
	 */
	@Test
	public void testFindDirectWrongDirection() {
		Integer depSid = 138;
		Integer arrSid = 5;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
	}
	
	/**
	 * Test of findDirect method, of class RouteDaoDataFile.
	 * Not existing direct route.
	 */
	@Test
	public void testFindDirectNotExistingRoute() {
		Integer depSid = 5;
		Integer arrSid = 17;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
		/**
		 * not existing direct route
		 */
		depSid = 5;
		arrSid = 17;
		
		result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);

		/**
		 * arrival stop id does not exists
		 */
		depSid = 5;
		arrSid = 17000;
		
		result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);

		/**
		 * departure stop id does not exists
		 */
		depSid = 50000;
		arrSid = 17;
		
		result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);

		/**
		 * both stop ids does not exists
		 */
		depSid = 50000;
		arrSid = 17000;
		
		result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
	}
	
	/**
	 * Test of findDirect method, of class RouteDaoDataFile.
	 * Arrival stop id does not exists.
	 */
	@Test
	public void testFindDirectNoArrival() {
		Integer depSid = 5;
		Integer arrSid = 17000;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
	}
	
	/**
	 * Test of findDirect method, of class RouteDaoDataFile.
	 * Departure stop id does not exists.
	 */
	@Test
	public void testFindDirectNoDeparture() {
		Integer depSid = 50000;
		Integer arrSid = 17;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
	}
	
	/**
	 * Test of findDirect method, of class RouteDaoDataFile.
	 * Both stop ids does not exists.
	 */
	@Test
	public void testFindDirectNoArrivalNoDeparture() {
		Integer depSid = 50000;
		Integer arrSid = 17000;
		
		Route result = routeDao.findDirect(depSid, arrSid);
		assertNull(result);
	}
	
	/**
	 * Normal route dump.
	 */
	@Test
	public void testToRouteNormal(){
		String dump = "100 1 2 3 4 5";
		Integer id = 100;
		Route result = routeDao.toRoute(dump);
		assertEquals(id, result.getId());
		for(Integer x = 1; x < 6; x++){
			assertEquals(x, result.getSids().get(x - 1));
		}
	}
	
	/**
	 * Empty route. This route can not be really used because there is not 
	 * bus stop ids assigned to it. but it can be used like reservation of
	 * route id.
	 */
	@Test
	public void testToRouteIdOnly(){
		String dump = "100";
		Integer id = 100;
		Route result = routeDao.toRoute(dump);
		assertEquals(id, result.getId());
		assertTrue(result.getSids().isEmpty());
	}
	
	/**
	 * Route dump with not numeric route id.
	 */
	@Test
	public void testToRouteInvalidId(){
		String dump = "hundred 1 2 3 4 5";
		try{
			Route result = routeDao.toRoute(dump);
		}catch(NumberFormatException ex){
			String expectedMessage = "For input string: \"hundred\"";
			assertEquals(expectedMessage, ex.getMessage());
		}
	}
	
	/**
	 * Route dump with not numeric bus stop id.
	 */
	@Test
	public void testToRouteInvalidRouteId(){
		String dump = "100 one 2 3 4 5";
		try{
			Route result = routeDao.toRoute(dump);
		}catch(NumberFormatException ex){
			String expectedMessage = "For input string: \"one\"";
			assertEquals(expectedMessage, ex.getMessage());
		}
	}
}
