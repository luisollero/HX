package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.engine.pojo.House;
import com.opensymphony.xwork2.ModelDriven;

public class HouseController implements ModelDriven<Object> {

	private String id;
	private House house = new House();
	private Collection<House> list;

	private IHouseEngine houseEngine;

	// GET /house/House_id : {/house/liao} and .json/.xml
	public HttpHeaders show() {
		house = new House(houseEngine.getById(id));
		return new DefaultHttpHeaders("show");
	}

	// GET /house
	public HttpHeaders index() {
		list = houseEngine.findAll();
		return new DefaultHttpHeaders("index");
	}

	public Object getModel() {
		return (list != null ? list : house);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Autowired
	public void setHouseEngine(IHouseEngine houseEngine) {
		this.houseEngine = houseEngine;
	}

}
