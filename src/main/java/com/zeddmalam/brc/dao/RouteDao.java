/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeddmalam.brc.dao;

import com.zeddmalam.brc.model.Route;
import java.io.FileNotFoundException;

/**
 *
 * @author zedd
 */
public interface RouteDao {
	public void importRoutes() throws FileNotFoundException;
	public Route findDirect(Integer dep_sid, Integer arr_sid);
	public Route toRoute(String data) throws NumberFormatException;
}
