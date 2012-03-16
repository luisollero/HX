package com.hx.engine.pojo;

import java.util.Set;

import com.hx.model.dto.House;
import com.hx.model.dto.Role;
import com.hx.model.dto.Sector;

public class Personality implements Pojo {

	private String id;
	private Role role;
	private String userId;
	private String homeSectorId;
	private Integer influence;
	private String factionId;
	private String name;
	private String completeName;
	private Set<Integer> sectorsRuled;
	
	public Personality(com.hx.model.dto.Personality anotherPersonality) {
		this.id = anotherPersonality.getId();
		this.completeName = anotherPersonality.getCompleteName();
		this.factionId = anotherPersonality.getHouse().getId();
		this.homeSectorId = anotherPersonality.getHome().getId();
		this.influence = anotherPersonality.getInfluence();
		this.name = anotherPersonality.getName();
		this.role = anotherPersonality.getRole();
		this.userId = anotherPersonality.getUser().getId();
		// TODO Sets!
	}
	
	public Object toDTO() {
		com.hx.model.dto.Personality anotherPersonality = new com.hx.model.dto.Personality();
		anotherPersonality.setId(this.id);
		anotherPersonality.setName(this.name);
		anotherPersonality.setCompleteName(this.completeName);
		anotherPersonality.setHome(new Sector(this.homeSectorId));
		anotherPersonality.setInfluence(this.influence);
		anotherPersonality.setRole(this.role);
		anotherPersonality.setHouse(new House(this.factionId));
		// TODO Sets!
		return anotherPersonality;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHomeSectorId() {
		return homeSectorId;
	}

	public void setHomeSectorId(String homeSectorId) {
		this.homeSectorId = homeSectorId;
	}

	public Integer getInfluence() {
		return influence;
	}

	public void setInfluence(Integer influence) {
		this.influence = influence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public Set<Integer> getSectorsRuled() {
		return sectorsRuled;
	}

	public void setSectorsRuled(Set<Integer> sectorsRuled) {
		this.sectorsRuled = sectorsRuled;
	}

	public String getFactionId() {
		return factionId;
	}

	public void setFactionId(String factionId) {
		this.factionId = factionId;
	}

}
