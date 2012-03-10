package com.hx.model.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Application users.
 * @author Luis Ollero
 *
 */
@Entity
@Table(name="hx_user", catalog="hx")
public class User {
	
	@Id
	@Column(name="hx_user_id")
	private String id;
	@Column(name="hx_user_pass")
	private String pass;
	@Column(name="hx_user_mail")
	private String eMail;
	@Column(name="hx_user_application")
	private String application;
	
	@Column(name="hx_user_status")
	@Enumerated(EnumType.STRING)
	private UserStatus status;	
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="hx_user_personality")
	private Personality personality;

	
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

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String mail) {
		eMail = mail;
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

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((User) obj).id);
	}
	
	@Override
	public String toString() {
		return this.id + " || " + this.eMail;
	}

}
