package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IHouseEngine;
import com.hx.engine.IPersonalityEngine;
import com.hx.engine.ISectorEngine;
import com.hx.engine.IUserEngine;
import com.hx.engine.pojo.Personality;
import com.hx.model.dto.Role;
import com.hx.model.dto.Sector;
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
	private ISectorEngine sectorEngine;
	
	// GET /personality/personality_id
	public HttpHeaders show() {
		personality = personalityEngine.getById(Integer.valueOf(id));
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
		personality.setInfluence(0);
		
		com.hx.model.dto.Personality newPersonality = (com.hx.model.dto.Personality) personality.toDTO();
		newPersonality.setHouse(houseEngine.getById(houseId));
		newPersonality.setRole(Role.valueOf(role));
		newPersonality.setUser((User) userEngine.getById(Integer.valueOf(userId)).toDTO());
		newPersonality.setHome((Sector) sectorEngine.getById(Integer.valueOf(homeSectorId)).toDTO());
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

	public String getHomeSectorId() {
		return homeSectorId;
	}

	public void setHomeSectorId(String homeSectorId) {
		this.homeSectorId = homeSectorId;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Autowired
	public void setUserEngine(IUserEngine userEngine) {
		this.userEngine = userEngine;
	}
	
	@Autowired
	public void setHouseEngine(IHouseEngine houseEngine) {
		this.houseEngine = houseEngine;
	}
	
	@Autowired
	public void setPersonalityEngine(IPersonalityEngine personalityEngine) {
		this.personalityEngine = personalityEngine;
	}
	
	@Autowired
	public void setSectorEngine(ISectorEngine sectorEngine) {
		this.sectorEngine = sectorEngine;
	}

}
