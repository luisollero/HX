package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.engine.IUserEngine;
import com.hx.engine.pojo.User;
import com.hx.model.dto.House;
import com.hx.model.dto.Role;
import com.opensymphony.xwork2.ModelDriven;

public class UserController implements ModelDriven<Object> {

	private String id;
	private String mail;
	private String motivation;
	private String name;
	private String faction;
	private String role;
	
	private User user;
	private Collection<User> list;

	private IUserEngine userEngine;
	private IHouseEngine houseEngine;

	// GET /user/user_id
	public HttpHeaders show() {
		user = userEngine.getById(Integer.valueOf(id));
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
		user.setApplication(motivation);
		user.setMail(mail);
		user.setName(name);
		
		com.hx.model.dto.User newUser = (com.hx.model.dto.User) user.toDTO();
		newUser.setFavoriteHouse(new House(faction));
		newUser.setFavoriteRole(Role.valueOf(role));
		
		System.out.println(user.toString());

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

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
