package com.hx.engine.pojo;

import java.util.Set;

import com.hx.model.dto.House;
import com.hx.model.dto.Role;
import com.hx.model.dto.Sector;

public class Personality implements Pojo {

	private Integer id;
	private Role role;
	private Integer userId;
	private Integer homeSectorId;
	private Integer influence;
	private String factionId;
	private String name;
	private String completeName;
	private String biography;
	private Set<com.hx.engine.pojo.Sector> sectorsRuled;
	
	public Personality(com.hx.model.dto.Personality anotherPersonality) {
		this.id = anotherPersonality.getId();
		this.completeName = anotherPersonality.getCompleteName();
		this.factionId = anotherPersonality.getHouse().getId();
		this.influence = anotherPersonality.getInfluence();
		this.name = anotherPersonality.getName();
		this.role = anotherPersonality.getRole();
		this.biography = anotherPersonality.getBiography();
		this.userId = anotherPersonality.getUser().getId();
		
		for (com.hx.model.dto.Sector sector : anotherPersonality.getSectors()) {
			this.sectorsRuled.add(new com.hx.engine.pojo.Sector(sector));
		}
	}
	
	public Personality() {
	}

	public Personality(Integer i) {
		this.id = i;
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
		anotherPersonality.setBiography(this.biography);
		// TODO Sets!
		return anotherPersonality;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getHomeSectorId() {
		return homeSectorId;
	}

	public void setHomeSectorId(Integer homeSectorId) {
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

	public Set<com.hx.engine.pojo.Sector> getSectorsRuled() {
		return sectorsRuled;
	}

	public void setSectorsRuled(Set<com.hx.engine.pojo.Sector> sectorsRuled) {
		this.sectorsRuled = sectorsRuled;
	}

	public String getFactionId() {
		return factionId;
	}

	public void setFactionId(String factionId) {
		this.factionId = factionId;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

}
