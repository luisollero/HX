package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.ISectorEngine;
import com.hx.engine.pojo.Sector;
import com.hx.model.dto.House;
import com.opensymphony.xwork2.ModelDriven;

public class SectorController implements ModelDriven<Object> {

	private String id;
	private String token;
	private String houseId;
	private Sector sector = new Sector();
	private Collection<Sector> list;

	private ISectorEngine sectorEngine;

	// GET /sector/id
	public HttpHeaders show() {
		sector = sectorEngine.getById(Integer.valueOf(id));
		return new DefaultHttpHeaders("show");
	}
	
	// GET /sector/id/retrieve
	public HttpHeaders retrieve() {
		return new DefaultHttpHeaders("show");
	}

	// GET /sector.json || /sector.json?houseId=COMSTAR
	public HttpHeaders index() {
		if (houseId == null) {
			list = sectorEngine.findAll();
		} else {
			list = sectorEngine.findByHouse(new House(houseId));
		}
		return new DefaultHttpHeaders("index");
	}

	public Object getModel() {
		return (list != null ? list : sector);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	@Autowired
	public void setSectorEngine(ISectorEngine sectorEngine) {
		this.sectorEngine = sectorEngine;
	}

}
