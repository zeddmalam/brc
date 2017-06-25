package com.zeddmalam.brc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 * DirectResponse entity is used for rendering response for /api/direct.
 */
public class DirectResponse implements Serializable {

	@JsonProperty("dep_sid")
	public Integer depSid;

	@JsonProperty("arr_sid")
	public Integer arrSid;

	@JsonProperty("direct_bus_route")
	public Boolean directBusRoute;

	public Integer getDepSid() {
		return depSid;
	}

	public DirectResponse setDepSid(Integer dep_sid) {
		this.depSid = dep_sid;
		return this;
	}

	public Integer getArrSid() {
		return arrSid;
	}

	public DirectResponse setArrSid(Integer arr_sid) {
		this.arrSid = arr_sid;
		return this;
	}

	public Boolean getDirectBusRoute() {
		return directBusRoute;
	}

	public DirectResponse setDirectBusRoute(Boolean direct_bus_route) {
		this.directBusRoute = direct_bus_route;
		return this;
	}
}
