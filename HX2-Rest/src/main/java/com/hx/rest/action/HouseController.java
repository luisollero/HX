package com.hx.rest.action;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.model.dto.House;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;

public class HouseController extends ValidationAwareSupport implements
		ModelDriven<House>, Validateable {

	private static final long serialVersionUID = 6134808879242484608L;

	private String id;
	private House house = new House();
	
	private IHouseEngine houseEngine;

	// GET /test/1
	public HttpHeaders show() {
		house = houseEngine.getById(id);
		System.out.println(id + "");
		return new DefaultHttpHeaders("show");
	}

	public void validate() {
		// TODO: Nothing here
	}

	public House getModel() {
		return house;
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
