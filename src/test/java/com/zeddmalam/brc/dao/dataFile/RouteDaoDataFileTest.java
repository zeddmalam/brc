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
	 */
	@Test
	public void testFindDirect() {
		/**
		 * both route ids are null
		 */
		Integer dep_sid = null;
		Integer arr_sid = null;
		
		Route result = routeDao.findDirect(dep_sid, arr_sid);
		assertNull(result);
		
		/**
		 * existing direct route with correct direction
		 */
		dep_sid = 5;
		arr_sid = 138;
		
		result = routeDao.findDirect(dep_sid, arr_sid);
		assertNotNull(result);
		assertTrue(result.getSids().indexOf(dep_sid) > -1);
		assertTrue(result.getSids().indexOf(arr_sid) > -1);
		assertTrue(result.getSids().indexOf(dep_sid) < result.getSids().indexOf(arr_sid));

		/**
		 * existing direct route with incorrect direction
		 */
		dep_sid = 138;
		arr_sid = 5;
		
		result = routeDao.findDirect(dep_sid, arr_sid);
		assertNull(result);

		/**
		 * not existing direct route
		 */
		dep_sid = 5;
		arr_sid = 17;
		
		result = routeDao.findDirect(dep_sid, arr_sid);
		assertNull(result);

		/**
		 * arrival stop id does not exists
		 */
		dep_sid = 5;
		arr_sid = 17000;
		
		result = routeDao.findDirect(dep_sid, arr_sid);
		assertNull(result);

		/**
		 * departure stop id does not exists
		 */
		dep_sid = 50000;
		arr_sid = 17;
		
		result = routeDao.findDirect(dep_sid, arr_sid);
		assertNull(result);

		/**
		 * both stop ids does not exists
		 */
		dep_sid = 50000;
		arr_sid = 17000;
		
		result = routeDao.findDirect(dep_sid, arr_sid);
		assertNull(result);
	}
	
	@Test
	public void testToRoute(){
		
		/**
		 * Normal route dump.
		 */
		String dump = "100 1 2 3 4 5";
		Integer id = 100;
		Route result = routeDao.toRoute(dump);
		assertEquals(id, result.getId());
		for(Integer x = 1; x < 6; x++){
			assertEquals(x, result.getSids().get(x - 1));
		}
		
		/**
		 * Empty route. This route can not be really used because there is not 
		 * bus stop ids assigned to it. but it can be used like reservation of
		 * route id.
		 */
		dump = "100";
		id = 100;
		
		result = routeDao.toRoute(dump);
		assertEquals(id, result.getId());
		assertTrue(result.getSids().isEmpty());
		
		/**
		 * Route dump with not numeric route id.
		 */
		dump = "hundred 1 2 3 4 5";
		try{
			result = routeDao.toRoute(dump);
		}catch(NumberFormatException ex){
			String expectedMessage = "For input string: \"hundred\"";
			assertEquals(expectedMessage, ex.getMessage());
		}
		
		/**
		 * Route dump with not numeric bus stop id.
		 */
		dump = "100 one 2 3 4 5";
		try{
			result = routeDao.toRoute(dump);
		}catch(NumberFormatException ex){
			String expectedMessage = "For input string: \"one\"";
			assertEquals(expectedMessage, ex.getMessage());
		}
	}
}
