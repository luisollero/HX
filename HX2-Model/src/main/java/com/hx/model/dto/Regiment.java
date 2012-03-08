package com.hx.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Regimientos. Unidades mínimas de combate.
 * @author kineas
 *
 */
@Entity
@Table(name="hx_regiment",catalog="hx")
public class Regiment {

	@Id
	@Column(name="hx_regiment_id")
	private String id;			//Identificados unívoco del regimiento.
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_regiment_sector", referencedColumnName="hx_sector_id")
	private Sector sector;		// Sector en el que está estacionado.
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_regiment_house", referencedColumnName="hx_house_id")
	private House house; 		// Casa a la que debe obediencia el regimiento.
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_regiment_home", referencedColumnName="hx_sector_id")
	private Sector homeSector; // Sector "hogar" para el regimiento.
	
	@Column(name="hx_regiment_experience", length=20)
	@Enumerated(EnumType.STRING)
	private Experience experience; // Experiencia en combate de la unidad.

	@Column(name="hx_regiment_name",nullable=false)
	private String name;			// Nombre del regimiento. Identificador para el usuario.
	@Column(name="hx_regiment_maneuver")
	private Integer maneuver;		// Capacidad de maniobra.
	@Column(name="hx_regiment_attack")
	private Integer attack;			// Capacidad ofensiva.
	@Column(name="hx_regiment_defense")
	private Integer defense;		// Capacidad defensiva.
	@Column(name="hx_regiment_resistance")
	private Integer resistance;		// Resistencia actual.
	@Column(name="hx_regiment_total_resistance")
	private Integer totalResistence; // Resistencia máxima.
	@Column(name="hx_regiment_price")
	private Integer price;			// Precio orientativo del regimiento.
	@Column(name="hx_regiment_upkeep")
	private Integer upkeep;			// Coste de mantenimiento.

	//Getters and setters
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Sector getSector() {
		return sector;
	}
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	public House getHouse() {
		return house;
	}
	public void setHouse(House house) {
		this.house = house;
	}
	public Sector getHomeSector() {
		return homeSector;
	}
	public void setHomeSector(Sector homeSector) {
		this.homeSector = homeSector;
	}
	public Experience getExperience() {
		return experience;
	}
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getManeuver() {
		return maneuver;
	}
	public void setManeuver(Integer maneuver) {
		this.maneuver = maneuver;
	}
	public Integer getAttack() {
		return attack;
	}
	public void setAttack(Integer attack) {
		this.attack = attack;
	}
	public Integer getDefense() {
		return defense;
	}
	public void setDefense(Integer defense) {
		this.defense = defense;
	}
	public Integer getResistance() {
		return resistance;
	}
	public void setResistance(Integer resistance) {
		this.resistance = resistance;
	}
	public Integer getTotalResistence() {
		return totalResistence;
	}
	public void setTotalResistence(Integer totalResistence) {
		this.totalResistence = totalResistence;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getUpkeep() {
		return upkeep;
	}
	public void setUpkeep(Integer upkeep) {
		this.upkeep = upkeep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Regiment other = (Regiment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + " || " + name;
	}
	
}
