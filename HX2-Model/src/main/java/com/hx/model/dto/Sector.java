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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Sector. Minimum geographic entity.
 * @author Luis Ollero
 *
 */
@Entity
@Table(name="hx_sector", catalog="hx")
public class Sector {

	@Id
	@Column(name = "sector_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="sector_name")
	private String name;
	
	@Column(name="sector_coord_x")
	private Integer coordX;		// Axis X
	
	@Column(name="sector_coord_y")
	private Integer coordY;		// Axis Y
	
	@Column(name="sector_depth")
	private Integer depth;		// Density of planets in the sector. Number of successful battles to win the sector.
	
	@Column(name="sector_disputed")
	private Boolean disputed;	// Disputed sector

	@Column(name="sector_production")
	private Integer production; // Planet production
	
	@Column(name="sector_defense")
	private Integer defenseBonus;	// Defensive bonus
	
	@Column(name="sector_happiness")
	private Integer happiness;	// Empathy of the population with the current dominant house
	
	@Column(name = "orography")
	@Enumerated(EnumType.STRING)
	private Orography orography; // Orography of the planet, the worst the better for light units
	
	@OneToOne(optional=true)
	@JoinColumn(name="sector_lord")
	private Personality lord;	// Direct owner of the sector
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="sector_loyalty", referencedColumnName="hx_house_id")
	private House loyalty;		// House to which the sector is loyal

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="sector_house", referencedColumnName="hx_house_id")
	private House house;		// Dominant house

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="sector_personality", referencedColumnName="personality_id")
	private Personality personality;		// Who rules the sector
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="sector_suplyline", referencedColumnName="suplyline_id")
	private SuplyLine suplyLine;		// Is part of a supply line
	
	public Sector(Integer homeSectorId) {
		this.id = homeSectorId;
	}
	
	public Sector() {
	}

	//Setters and getters
	public Integer getId() {
		return id;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProduction() {
		return production;
	}

	public void setProduction(Integer production) {
		this.production = production;
	}

	public Integer getCoordX() {
		return coordX;
	}

	public void setCoordX(Integer coordX) {
		this.coordX = coordX;
	}

	public Integer getCoordY() {
		return coordY;
	}

	public void setCoordY(Integer coordY) {
		this.coordY = coordY;
	}

	public Integer getDefenseBonus() {
		return defenseBonus;
	}

	public void setDefenseBonus(Integer defenseBonus) {
		this.defenseBonus = defenseBonus;
	}

	public Integer getHappiness() {
		return happiness;
	}

	public void setHappiness(Integer happiness) {
		this.happiness = happiness;
	}

	public House getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(House loyalty) {
		this.loyalty = loyalty;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Boolean getDisputed() {
		return disputed;
	}

	public void setDisputed(Boolean disputed) {
		this.disputed = disputed;
	}

	public SuplyLine getSuplyLine() {
		return suplyLine;
	}

	public void setSuplyLine(SuplyLine suplyLine) {
		this.suplyLine = suplyLine;
	}

	public Personality getPersonality() {
		return personality;
	}

	public void setPersonality(Personality personality) {
		this.personality = personality;
	}

	public Personality getLord() {
		return lord;
	}

	public void setLord(Personality lord) {
		this.lord = lord;
	}
	
	public Orography getOrography() {
		return orography;
	}

	public void setOrography(Orography orography) {
		this.orography = orography;
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
		Sector other = (Sector) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", name=" + name + ", coordX=" + coordX
				+ ", coordY=" + coordY + ", depth=" + depth + ", disputed="
				+ disputed + ", production=" + production + ", defenseBonus="
				+ defenseBonus + ", happiness=" + happiness + ", lord=" + lord
				+ ", loyalty=" + loyalty + ", house=" + house
				+ ", personality=" + personality + ", suplyLine=" + suplyLine
				+ "]";
	}

	
}
