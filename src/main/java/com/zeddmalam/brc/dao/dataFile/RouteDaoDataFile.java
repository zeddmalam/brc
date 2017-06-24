/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeddmalam.brc.dao.dataFile;

import com.zeddmalam.brc.model.Route;
import com.zeddmalam.brc.dao.RouteDao;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author zedd
 */
@Service
public class RouteDaoDataFile implements RouteDao{
	@Value("${route.data.file.path}")
	private String dataFilePath;
	
	/**
	 * list of routes.
	 */
	private final HashMap<Integer, Route> routes = new HashMap<>();
	
	/**
	 * indexing of bus stop ids for faster finding of routes
	 * 
	 * sid -> array of route ids
	 */
	private final HashMap<Integer, ArrayList<Integer>> indexes = new HashMap<>();
	
	/**
	 * Read data from file into memory to optimize performance.
	 */
	@Override
	@PostConstruct
	public void importRoutes() throws IllegalStateException{
		try {
			File dataFile = new File(this.dataFilePath);
			Scanner scanner = new Scanner(dataFile);
			String line = scanner.nextLine().trim();
			Integer recordNumber = Integer.parseInt(line);
			System.out.println("Going to  read records: " + recordNumber);
			for(Integer x = 0; x < recordNumber; x++){
				Route route = this.toRoute(scanner.nextLine());
				routes.put(route.getId(), route);
				this.indexRoute(route);
			}
			
			System.out.println("Data import is finished");
		} catch (FileNotFoundException ex) {
			Logger.getLogger(RouteDaoDataFile.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex.getMessage());
		}
	}

	/**
	 * Finds first direct route between departure and arrival bus stops. All
	 * the routes are uni-directional.
	 * 
	 * @param dep_sid departure bus station id
	 * @param arr_sid arrival bus station id
	 * @return route containing required bus stations or null
	 */
	@Override
	public Route findDirect(Integer dep_sid, Integer arr_sid) {
		ArrayList<Integer> dep_index = indexes.get(dep_sid);
		ArrayList<Integer> arr_index = indexes.get(arr_sid);
		
		if(null == dep_index || null == arr_index){
			return null;
		}
		
		for(Integer routeId: dep_index){
			Route route = routes.get(routeId);
			if(arr_index.contains(routeId) && route.sids.indexOf(dep_sid) < route.sids.indexOf(arr_sid)){
				return route;
			}
		}
		return null;
	}
	
	/**
	 * Converts string with route description to Route object
	 * 
	 * @param data string which represents route
	 * @return Route object
	 */
	@Override
	public Route toRoute(String data) throws NumberFormatException{
		String[] split = data.split("\\s+");
		Route route = new Route().setId(Integer.parseInt(split[0].trim()));
		split = Arrays.copyOfRange(split, 1, split.length);
		for(String sid: split){
			route.getSids().add(Integer.parseInt(sid.trim()));
		}
		return route;
	};

	/**
	 * creates route indexes
	 * 
	 * @param route 
	 */
	private void indexRoute(Route route) {
		for(Integer sid: route.getSids()){
			ArrayList<Integer> index = indexes.get(sid);
			if(null == index){
				index = new ArrayList<>();
				indexes.put(sid, index);
			}
			indexes.get(sid).add(route.getId());
		}
	}
	
}
