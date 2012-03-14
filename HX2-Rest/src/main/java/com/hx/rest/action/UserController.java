package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IUserEngine;
import com.hx.engine.pojo.User;
import com.opensymphony.xwork2.ModelDriven;

public class UserController implements ModelDriven<Object> {

	private String id;
	private User user;
	private Collection<User> list;

	private IUserEngine userEngine;

	// GET /house/House_id
	public HttpHeaders show() {
		user = userEngine.getById(id);
		return new DefaultHttpHeaders("show");
	}

	// GET /house
	public HttpHeaders index() {
		list = userEngine.findAll();
		return new DefaultHttpHeaders("index");
	}

	public Object getModel() {
		return (list != null ? list : user);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Autowired
	public void setUserEngine(IUserEngine userEngine) {
		this.userEngine = userEngine;
	}

}
