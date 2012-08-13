package com.hx.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Regiments. Minimal combat units
 * 
 * @author Luis Ollero
 * 
 */
@Entity
@Table(name = "hx_regiment", catalog = "hx")
public class Regiment {

	@Id
	@Column(name = "regiment_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "regiment_sector", referencedColumnName = "sector_id")
	private Sector sector; // Sector in which the regiment is

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "regiment_house", referencedColumnName = "hx_house_id")
	private House house; // House that owns the regiment

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "regiment_home", referencedColumnName = "sector_id")
	private Sector homeSector; // Home sector of the regiment

	@Column(name = "rank", length = 20)
	@Enumerated(EnumType.STRING)
	private Rank rank; // Rank of the unit

	@Column(name = "name", nullable = false)
	private String name; // Regiment name. Identifier for the player
	
	@Column(name = "maneuver")
	private Integer maneuver; // Ability to synchronize and move of the unit
	
	@Column(name = "attack")
	private Integer attack; // Fire power of the unit
	
	@Column(name = "resistance")
	private Integer resistance; // current resistance
	
	@Column(name = "total_resistance")
	private Integer totalResistence; // maximum resistance
	
	@Column(name = "price")
	private Integer price; // Current cost of the regiment
	
	@Column(name = "upkeep_cost")
	private Integer upkeepCost; // Upkeep cost
	
	@Column(name = "upkeep_type", length = 10)
	@Enumerated(EnumType.STRING)
	private Upkeep upkeep; // Paid upkeep this turn

	// Getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
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

	public Integer getUpkeepCost() {
		return upkeepCost;
	}

	public void setUpkeepCost(Integer upkeep) {
		this.upkeepCost = upkeep;
	}

	public Upkeep getUpkeep() {
		return upkeep;
	}

	public void setUpkeep(Upkeep upkeep) {
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
