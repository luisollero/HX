package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.engine.IUserEngine;
import com.hx.engine.pojo.User;
import com.hx.model.dto.Role;
import com.opensymphony.xwork2.ModelDriven;

public class UserController implements ModelDriven<Object> {

	private String id;
	private String mail;
	private String application;
	private String name;
	private String favoriteHouseId;
	private String favoriteRole;
	
	private User user;
	private Collection<User> list;

	private IUserEngine userEngine;
	private IHouseEngine houseEngine;

	// GET /user/user_id
	public HttpHeaders show() {
		user = userEngine.getById(id);
		return new DefaultHttpHeaders("show");
	}

	// GET /user
	public HttpHeaders index() {
		list = userEngine.findAll();
		return new DefaultHttpHeaders("index");
	}
	
	// POST /user
	public HttpHeaders create() throws Exception {
		user = new User();
		user.setApplication(application);
		user.setMail(mail);
		user.setName(name);
		
		com.hx.model.dto.User newUser = (com.hx.model.dto.User) user.toDTO();
		newUser.setFavoriteHouse(houseEngine.getById(favoriteHouseId));
		newUser.setFavoriteRole(Role.valueOf(favoriteRole));
		userEngine.saveOrUpdate(newUser);
		
		return new DefaultHttpHeaders("success");
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFavoriteRole() {
		return favoriteRole;
	}

	public void setFavoriteRole(String favoriteRole) {
		this.favoriteRole = favoriteRole;
	}

	public String getFavoriteHouseId() {
		return favoriteHouseId;
	}

	public void setFavoriteHouseId(String favoriteHouseId) {
		this.favoriteHouseId = favoriteHouseId;
	}

	@Autowired
	public void setUserEngine(IUserEngine userEngine) {
		this.userEngine = userEngine;
	}
	
	@Autowired
	public void setHouseEngine(IHouseEngine houseEngine) {
		this.houseEngine = houseEngine;
	}

}
