package com.highexplosive.client.model;

import java.util.ArrayList;

public class User {

	private int userId;
	private String name;
	private String password;
	private String email;
	private String bio;

	// List of characters associated with this user
	private ArrayList<Integer> characterListIds;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String userName) {
		this.name = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public ArrayList<Integer> getCharacterListIds() {
		return characterListIds;
	}

	public void setCharacterListIds(ArrayList<Integer> characterListIds) {
		this.characterListIds = characterListIds;
	}

}
