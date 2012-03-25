package com.highexplosive.client.model;

import java.util.ArrayList;

public class Character {

	private Integer characterId;
	private String house;
	private Integer userId;
	private String role;
	private Integer sectorId;

	private String name;
	private String completeName;
	private String bio;
	private int influence;
	private int numberOfDeclarations;
	private long creationDate;
	private boolean endedTurn;
	private ArrayList<Sector> sectorRuled = new ArrayList<Sector>();

	public Integer getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Integer characterId) {
		this.characterId = characterId;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getInfluence() {
		return influence;
	}

	public void setInfluence(int influence) {
		this.influence = influence;
	}

	public boolean isEndedTurn() {
		return endedTurn;
	}

	public void setEndedTurn(boolean endedTurn) {
		this.endedTurn = endedTurn;
	}

	public int getNumberOfDeclarations() {
		return numberOfDeclarations;
	}

	public void setNumberOfDeclarations(int numberOfDeclarations) {
		this.numberOfDeclarations = numberOfDeclarations;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public Integer getSectorId() {
		return sectorId;
	}

	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}

	public ArrayList<Sector> getSectorRuled() {
		return sectorRuled;
	}

	public void setSectorRuled(ArrayList<Sector> sectorRuled) {
		this.sectorRuled = sectorRuled;
	}

	@Override
	public String toString() {
		return "Character [characterId=" + characterId + ", house=" + house
				+ ", userId=" + userId + ", positionInHouse=" + role
				+ ", name=" + name + ", bio=" + bio + ", karma=" + influence
				+ ", numberOfDeclarations=" + numberOfDeclarations
				+ ", creationDate=" + creationDate + ", turnEnded=" + endedTurn
				+ "]";
	}
	
}
