package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.engine.IPersonalityEngine;
import com.hx.engine.IUserEngine;
import com.hx.engine.pojo.Personality;
import com.hx.model.dto.Role;
import com.hx.model.dto.User;
import com.hx.model.dto.UserStatus;
import com.opensymphony.xwork2.ModelDriven;

public class PersonalityController implements ModelDriven<Object> {

	private String id;
	private String homeSectorId;
	private String completeName;
	private String name;
	private String houseId;
	private String userId;
	private String role;
	
	private Personality personality;
	private Collection<Personality> list;

	private IUserEngine userEngine;
	private IPersonalityEngine personalityEngine;
	private IHouseEngine houseEngine;

	// GET /personality/personality_id
	public HttpHeaders show() {
		personality = personalityEngine.getById(id);
		return new DefaultHttpHeaders("show");
	}

	// GET /personality
	public HttpHeaders index() {
//		list = personalityEngine.findAll();
		return new DefaultHttpHeaders("index");
	}
	
	// POST /personality
	public HttpHeaders create() throws Exception {
		personality = new Personality();
		personality.setName(name);
		personality.setCompleteName(completeName);
		personality.setHomeSectorId(homeSectorId);
		personality.setInfluence(0);
		
		com.hx.model.dto.Personality newPersonality = (com.hx.model.dto.Personality) personality.toDTO();
		newPersonality.setHouse(houseEngine.getById(houseId));
		newPersonality.setRole(Role.valueOf(role));
		newPersonality.setUser((User) userEngine.getById(userId).toDTO());
		newPersonality.setStatus(UserStatus.PENDIENT);
		
		personalityEngine.saveOrUpdate(newPersonality);
		
		return new DefaultHttpHeaders("success");
	}

	public Object getModel() {
		return (list != null ? list : personality);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
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
