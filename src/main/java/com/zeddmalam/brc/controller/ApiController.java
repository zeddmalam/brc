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
 * API controller.
 */
@Controller("apiController")
@RequestMapping(value = "/api")
public class ApiController {

	@Autowired
	RouteDao routeDao;
	
	/**
	 * this API call determines if direct route is possible between departure
	 * and arrival bus stations for single transportation provider
	 * 
	 * @param depSid departure bus station id
	 * @param arrSid arrival bus station id
	 * @return JSON object
	 */
	@RequestMapping(value = "/direct", method = RequestMethod.GET)
	@ResponseBody
	public DirectResponse direct(@RequestParam("dep_sid") Integer depSid, @RequestParam("arr_sid") Integer arrSid) {
		Route route = routeDao.findDirect(depSid, arrSid);
		
		DirectResponse response = new DirectResponse()
			.setDirectBusRoute(false)
			.setArrSid(arrSid)
			.setDepSid(depSid);
		
		if(null != route){
			response.setDirectBusRoute(Boolean.TRUE);
		}
		return response;
	}

}
