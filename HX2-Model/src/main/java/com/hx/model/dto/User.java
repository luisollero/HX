package com.hx.model.dto;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Application users.
 * 
 * @author Luis Ollero
 * 
 */
@Entity
@Table(name = "hx_user", catalog = "hx")
public class User {

	@Id
	@Column(name = "hx_user_id")
	private String id;
	@Column(name = "hx_user_pass")
	private String pass;
	@Column(name = "hx_user_mail")
	private String mail;
	@Column(name = "hx_user_name")
	private String name;
	@Column(name = "hx_user_application")
	private String application;

	@Column(name = "hx_user_status")
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@OneToMany(mappedBy="user")
	private Set<Personality> personalities;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Set<Personality> getPersonalities() {
		return personalities;
	}

	public void setPersonalities(Set<Personality> personalities) {
		this.personalities = personalities;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String eMail) {
		this.mail = eMail;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((User) obj).id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pass=" + pass + ", mail=" + mail
				+ ", name=" + name + ", application=" + application
				+ ", status=" + status + ", personalities=" + personalities
				+ "]";
	}

	
}