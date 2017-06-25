package com.zeddmalam.brc.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @DirectResponse entity is used for rendering responce for /api/direct
 */
public class DirectResponse implements Serializable {
	public Integer dep_sid;
	public Integer arr_sid;
	public Boolean direct_bus_route;

	public Integer getDep_sid() {
		return dep_sid;
	}

	public DirectResponse setDep_sid(Integer dep_sid) {
		this.dep_sid = dep_sid;
		return this;
	}

	public Integer getArr_sid() {
		return arr_sid;
	}

	public DirectResponse setArr_sid(Integer arr_sid) {
		this.arr_sid = arr_sid;
		return this;
	}

	public Boolean getDirect_bus_route() {
		return direct_bus_route;
	}

	public DirectResponse setDirect_bus_route(Boolean direct_bus_route) {
		this.direct_bus_route = direct_bus_route;
		return this;
	}
}
