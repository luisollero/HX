/**
 * 
 */
package com.hx.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Luis Ollero
 *
 */
@Entity
@Table(name="hx_personality", catalog="hx")
public class Personality {
	
	@Id
	@Column(name="hx_personality_id")
	private String id;
	
	@Column(name="hx_personality_role", length=20)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToOne(mappedBy="personality")
	private User user;
	
	@OneToOne(mappedBy="lord")
	@JoinColumn(name="hx_personality_home")
	private Sector home;
	
	@Column(name="hx_personality_status")
	private Integer status;
	@Column(name="hx_personality_influence")
	private Integer influence;
	@Column(name="hx_personality_name")
	private String name;
	@Column(name="hx_personality_complete_name")
	private String completeName;
	
	@OneToMany(mappedBy="personality")
	private Set<Sector> sectors = new HashSet<Sector>();  // Conjunto de sectores controlados por el pj
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_personality_house", referencedColumnName="hx_house_id")
	private House house;
	

	//GETTERS AND SETTERS
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public House getHouse() {
		return house;
	}
	public void setHouse(House house) {
		this.house = house;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getInfluence() {
		return influence;
	}
	public void setInfluence(Integer influence) {
		this.influence = influence;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Sector> getSectors() {
		return sectors;
	}
	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}
	public Sector getHome() {
		return home;
	}
	public void setHome(Sector home) {
		this.home = home;
	}
	@Override
	public String toString() {
		return this.completeName + " || " + this.user.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj!=null)
			return this.id.equals(((Personality)obj).id);
		return false;
	}
	
}
