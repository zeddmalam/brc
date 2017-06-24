/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zeddmalam.brc.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author zedd
 */
@Entity
public class Route implements Serializable {
	@Id
	public Integer Id;
	public ArrayList<Integer> sids = new ArrayList<Integer>();

	public Integer getId() {
		return Id;
	}

	public Route setId(Integer Id) {
		this.Id = Id;
		return this;
	}
	
	public ArrayList<Integer> getSids(){
		return this.sids;
	}
}
