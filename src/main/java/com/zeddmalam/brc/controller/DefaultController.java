/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeddmalam.brc.controller;

import com.zeddmalam.brc.model.DirectResponse;
import com.zeddmalam.brc.model.Route;
import com.zeddmalam.brc.dao.RouteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author zedd
 */
@Controller("defaultController")
@RequestMapping(value = "/api")
public class DefaultController {

	@Autowired
	RouteDao routeDao;
	
	/**
	 * this API call determines if direct route is possible between departure
	 * and arrival bus stations for single transportation provider
	 * 
	 * @param dep_sid departure bus station id
	 * @param arr_sid arrival bus station id
	 * @return JSON object
	 */
	@RequestMapping(value = "/direct", method = RequestMethod.GET)
	@ResponseBody
	public DirectResponse direct(@RequestParam("dep_sid") Integer dep_sid, @RequestParam("arr_sid") Integer arr_sid) {
		Route route = routeDao.findDirect(dep_sid, arr_sid);
		
		DirectResponse response = new DirectResponse()
			.setDirect_bus_route(false)
			.setArr_sid(arr_sid)
			.setDep_sid(dep_sid);
		
		if(null != route){
			response.setDirect_bus_route(Boolean.TRUE);
		}
		return response;
	}

}
