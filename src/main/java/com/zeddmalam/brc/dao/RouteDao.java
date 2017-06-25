package com.zeddmalam.brc.dao;

import com.zeddmalam.brc.model.Route;
import java.io.FileNotFoundException;

/**
 * Route data access interface.
 */
public interface RouteDao {

	public void importRoutes() throws FileNotFoundException;

	public Route findDirect(Integer dep_sid, Integer arr_sid);

	public Route toRoute(String data) throws NumberFormatException;
}
