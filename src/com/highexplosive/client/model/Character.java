package com.highexplosive.client.model;

public class Character {

	private int characterId;
	private int house;
	private int userId;
	private int positionInHouse;

	private String name;
	private String bio;
	private int karma;
	private int numberOfDeclarations;
	private int creationDate;
	private boolean turnEnded;

	public int getCharacterId() {
		return characterId;
	}

	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public int getPositionInHouse() {
		return positionInHouse;
	}

	public void setPositionInHouse(int positionInHouse) {
		this.positionInHouse = positionInHouse;
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

	public int getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(int creationDate) {
		this.creationDate = creationDate;
	}

}
