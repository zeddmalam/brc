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
 * Implementation for route data access object. Uses text file with route data.
 */
@Service
public class RouteDaoDataFile implements RouteDao {

	@Value("${route.data.file.path}")
	private String dataFilePath;

	/**
	 * list of routes.
	 */
	private final HashMap<Integer, Route> ROUTES = new HashMap<>();

	/**
	 * indexing of bus stop ids for faster finding of routes
	 *
	 * sid -> array of route ids
	 */
	private final HashMap<Integer, ArrayList<Integer>> INDEXES = new HashMap<>();

	/**
	 * Read data from file into memory to optimize performance.
	 */
	@Override
	@PostConstruct
	public void importRoutes() throws IllegalStateException {
		try {
			File dataFile = new File(this.dataFilePath);
			Scanner scanner = new Scanner(dataFile);
			String line = scanner.nextLine().trim();
			Integer recordNumber = Integer.parseInt(line);
			for (int x = 0; x < recordNumber; x++) {
				Route route = this.toRoute(scanner.nextLine());
				ROUTES.put(route.getId(), route);
				this.indexRoute(route);
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(RouteDaoDataFile.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex.getMessage());
		}
	}

	/**
	 * Finds first direct route between departure and arrival bus stops. All the
	 * routes are uni-directional.
	 *
	 * @param depSid departure bus station id
	 * @param arrSid arrival bus station id
	 * @return route containing required bus stations or null
	 */
	@Override
	public Route findDirect(Integer depSid, Integer arrSid) {
		ArrayList<Integer> depIndex = INDEXES.get(depSid);
		ArrayList<Integer> arrIndex = INDEXES.get(arrSid);

		if (depIndex == null || arrIndex == null) {
			return null;
		}

		for (Integer routeId : depIndex) {
			Route route = ROUTES.get(routeId);
			if (arrIndex.contains(routeId) && route.sids.indexOf(depSid) < route.sids.indexOf(arrSid)) {
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
	public Route toRoute(String data) throws NumberFormatException {
		String[] split = data.split("\\s+");
		Route route = new Route().setId(Integer.parseInt(split[0].trim()));
		split = Arrays.copyOfRange(split, 1, split.length);
		for (String sid : split) {
			route.getSids().add(Integer.parseInt(sid.trim()));
		}
		return route;
	}

	/**
	 * creates route indexes
	 * 
	 * @param route 
	 */
	private void indexRoute(Route route) {
		for (Integer sid : route.getSids()) {
			ArrayList<Integer> index = INDEXES.get(sid);
			if (null == index) {
				index = new ArrayList<>();
				INDEXES.put(sid, index);
			}
			INDEXES.get(sid).add(route.getId());
		}
	}

}
