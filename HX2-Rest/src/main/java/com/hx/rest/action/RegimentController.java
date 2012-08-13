package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.engine.IRegimentEngine;
import com.hx.engine.pojo.Regiment;
import com.opensymphony.xwork2.ModelDriven;

public class RegimentController implements ModelDriven<Object> {

	private String id;
	private Regiment house = new Regiment();
	private Collection<Regiment> list;

	private IRegimentEngine regimentEngine;

	// GET /regiment/regiment_id
	public HttpHeaders show() {
		house = new Regiment(regimentEngine.getById(id));
		return new DefaultHttpHeaders("show");
	}

	// GET /house
	public HttpHeaders index() {
//		list = regimentEngine.findAll();
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
	public void setHouseEngine(IRegimentEngine regimentEngine) {
		this.regimentEngine = regimentEngine;
	}

}
