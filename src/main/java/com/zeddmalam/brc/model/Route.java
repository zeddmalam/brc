package com.zeddmalam.brc.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Route entity.
 */
@Entity
public class Route implements Serializable {

	@Id
	public Integer Id;
	public ArrayList<Integer> sids = new ArrayList<>();

	public Integer getId() {
		return Id;
	}

	public Route setId(Integer Id) {
		this.Id = Id;
		return this;
	}

	public ArrayList<Integer> getSids() {
		return this.sids;
	}
}
