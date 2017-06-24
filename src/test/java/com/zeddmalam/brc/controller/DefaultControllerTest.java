/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author zedd
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class DefaultControllerTest {
	
	@Autowired
	@Qualifier("defaultController")
    DefaultController defaultController;
	
	public DefaultControllerTest() {
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
	 * Test of direct method, of class DefaultController.
	 */
	@org.junit.Test
	public void testDirect() {
		System.out.println("direct");
		/**
		 * both route ids are null
		 */
		Integer dep_sid = null;
		Integer arr_sid = null;
		
		DirectResponse result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(false, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());
		
		/**
		 * existing direct route with correct direction
		 */
		dep_sid = 5;
		arr_sid = 138;
		
		result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(true, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());

		/**
		 * existing direct route with incorrect direction
		 */
		dep_sid = 138;
		arr_sid = 5;
		
		result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(false, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());

		/**
		 * not existing direct route
		 */
		dep_sid = 5;
		arr_sid = 17;
		
		result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(false, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());

		/**
		 * arrival stop id does not exists
		 */
		dep_sid = 5;
		arr_sid = 17000;
		
		result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(false, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());

		/**
		 * departure stop id does not exists
		 */
		dep_sid = 50000;
		arr_sid = 17;
		
		result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(false, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());

		/**
		 * both stop ids does not exists
		 */
		dep_sid = 50000;
		arr_sid = 17000;
		
		result = defaultController.direct(dep_sid, arr_sid);
		assertEquals(false, result.getDirect_bus_route());
		assertEquals(dep_sid, result.getDep_sid());
		assertEquals(arr_sid, result.getArr_sid());
	}
	
}
