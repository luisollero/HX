package com.highexplosive.client.model;

public class Character {

	private String characterId;
	private String house;
	private String userId;
	private int role;

	private String name;
	private String bio;
	private int karma;
	private int numberOfDeclarations;
	private long creationDate;
	private boolean turnEnded;

	public String getCharacterId() {
		return characterId;
	}

	public void setCharacterId(String characterId) {
		this.characterId = characterId;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getKarma() {
		return karma;
	}

	public void setKarma(int karma) {
		this.karma = karma;
	}

	public boolean isTurnEnded() {
		return turnEnded;
	}

	public void setTurnEnded(boolean turnEnded) {
		this.turnEnded = turnEnded;
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

	@Override
	public String toString() {
		return "Character [characterId=" + characterId + ", house=" + house
				+ ", userId=" + userId + ", positionInHouse=" + role
				+ ", name=" + name + ", bio=" + bio + ", karma=" + karma
				+ ", numberOfDeclarations=" + numberOfDeclarations
				+ ", creationDate=" + creationDate + ", turnEnded=" + turnEnded
				+ "]";
	}
	
}
