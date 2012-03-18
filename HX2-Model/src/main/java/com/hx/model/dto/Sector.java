package com.hx.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name="hx_sector_id")
	private String id; 			// Compuesta por las coordenadas (x,y) con su signo. ("-1+1" por ejemplo)
	
	@Column(name="hx_sector_name")
	private String name;		// Nombre del sector
	
	@Column(name="hx_sector_coord_x")
	private Integer coordX;		// Eje X
	@Column(name="hx_sector_coord_y")
	private Integer coordY;		// Eje Y
	
	@Column(name="hx_sector_depth")
	private Integer depth;		// Profundidad del sector, (i.e. densidad de planetas)
	
	@Column(name="hx_sector_disputed")
	private Boolean disputed;	// Sector en disputa

	@Column(name="hx_sector_production")
	private Integer production; // Producción del planeta
	
	@Column(name="hx_sector_defense")
	private Integer defenseBonus;	// Bonificador para las tropas que defiendan el planeta
	
	@Column(name="hx_sector_happiness")
	private Integer happiness;	// Empatía de la población local con la casa gobernante
	
	@OneToOne(optional=true)
	@JoinColumn(name="hx_sector_lord")
	private Personality lord;	// Personaje que se encuentra en el sector. (Puede ser un un noble menor o un duque)
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_sector_loyalty", referencedColumnName="hx_house_id")
	private House loyalty;		// Casa a la que el sector es leal

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_sector_house", referencedColumnName="hx_house_id")
	private House house;		// Casa gobernante

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_sector_personality", referencedColumnName="hx_personality_id")
	private Personality personality;		// Who rules de sector
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_sector_suplyline", referencedColumnName="hx_suplyline_id")
	private SuplyLine suplyLine;		// Pertenece o no a una línea de suministros
	
	public Sector(String homeSectorId) {
		this.id = homeSectorId;
	}
	
	public Sector() {
		// TODO Auto-generated constructor stub
	}

	//Setters and getters
	public String getId() {
		return id;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public void setId(String id) {
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
		return id + " || " + name;
	}
	
}