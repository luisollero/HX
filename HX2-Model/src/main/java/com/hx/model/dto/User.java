package com.hx.model.dto;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	@Column(name = "hx_user_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "hx_user_pass")
	private String pass;
	@Column(name = "hx_user_mail")
	private String mail;
	@Column(name = "hx_user_name")
	private String name;
	@Column(name = "hx_user_application")
	private String application;
	
	@Column(name = "hx_user_favrole")
	@Enumerated(EnumType.STRING)
	private Role favoriteRole;
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "hx_user_favhouse", referencedColumnName = "hx_house_id")
	private House favoriteHouse;

	@OneToMany(mappedBy="user")
	private Set<Personality> personalities;

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

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
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
	
	public Role getFavoriteRole() {
		return favoriteRole;
	}

	public void setFavoriteRole(Role favoriteRole) {
		this.favoriteRole = favoriteRole;
	}

	public House getFavoriteHouse() {
		return favoriteHouse;
	}

	public void setFavoriteHouse(House favoriteHouse) {
		this.favoriteHouse = favoriteHouse;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((User) obj).id);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pass=" + pass + ", mail=" + mail
				+ ", name=" + name + ", application=" + application
				+ ", favoriteRole=" + favoriteRole + ", favoriteHouse="
				+ favoriteHouse + ", personalities=" + personalities + "]";
	}

	
}