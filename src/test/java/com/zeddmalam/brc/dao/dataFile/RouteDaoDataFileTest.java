/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author zedd
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class RouteDaoDataFileTest {
	
	@Autowired
	RouteDao routeDao;
	
	public RouteDaoDataFileTest() {
	}
	
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
		System.out.println("findDirect");

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
	
	/**
	 * Testing indexation algorithm with non direct way
	 * 
	 * Indexes
	 * 
	 * 5		2 19 6 7 18
	 * 11		2 13 7 8
	 * 12		13 14 6 8 11
	 * 16		6 11
	 * 17		1 14
	 * 19		6
	 * 20		1
	 * 24		1
	 * 106		1 2 13 8
	 * 114		19 14 7 18 11
	 * 118		6
	 * 121		19 7 11
	 * 138		6 8 18
	 * 140		1
	 * 142		5 14 8
	 * 148		1 13 6 7 8 11
	 * 150		1 19 14 7
	 * 153		1 19 13 18
	 * 155		11
	 * 160		1
	 * 169		13 7 8 11
	 * 174		14 6
	 * 179		14
	 * 184		6
	 * 
	 */
	//@Test
	public void testIndexation(){
		System.out.println("testIndexation");

		/**
		 * Bus stop with id 148 shoul
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
	}
}
