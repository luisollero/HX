package com.hx.engine.pojo;


public class User implements Pojo {

	private Integer id;
	private String pass;
	private String mail;
	private String name;
	private String application;
	private String favoriteHouseId;
	private String favoriteRole;

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(com.hx.model.dto.User user) {
		setApplication(user.getApplication());
		setId(user.getId());
		setMail(user.getMail());
		setPass(user.getPass());
		setName(user.getName());
	}

	public User(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getFavoriteHouseId() {
		return favoriteHouseId;
	}

	public void setFavoriteHouseId(String favoriteHouseId) {
		this.favoriteHouseId = favoriteHouseId;
	}

	public String getFavoriteRole() {
		return favoriteRole;
	}

	public void setFavoriteRole(String favoriteRole) {
		this.favoriteRole = favoriteRole;
	}

	public Object toDTO() {
		com.hx.model.dto.User user = new com.hx.model.dto.User();
		user.setApplication(application);
		user.setId(id);
		user.setMail(mail);
		user.setPass(pass);
		user.setName(name);
		return user;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pass=" + pass + ", mail=" + mail
				+ ", name=" + name + ", application=" + application + "]";
	}
	
}
