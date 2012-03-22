package com.hx.model.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Faction in the game.
 * @author Luis Ollero
 *
 */
@Entity
@Table(name="hx_house", catalog="hx")
public class House {

	@Id
	@Column(name="hx_house_id", nullable=false)
	private String id;
	@Column(name="hx_house_name", nullable=false)
	private String name;
	@Column(name="hx_house_alternate_name")
	private String alternateName; // Alternative (long) name for the house
	
	@OneToMany(mappedBy="house")
	private Set<Sector> sectors = new HashSet<Sector>();  // Sectors owned by the house
	
	@OneToMany(mappedBy="houseReference", cascade=CascadeType.ALL)
	private Set<HouseRelation>	houseRelationsReference = new HashSet<HouseRelation>();
	@OneToMany(mappedBy="houseDestiny", cascade=CascadeType.ALL)
	private Set<HouseRelation>	houseRelationsDestiny = new HashSet<HouseRelation>();

	public House() {
		// TODO Auto-generated constructor stub
	}
	
	public House(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
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
	
	public String getAlternateName() {
		return alternateName;
	}
	
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

	public Set<HouseRelation> getHouseRelationsReference() {
		return houseRelationsReference;
	}

	public void setHouseRelationsReference(Set<HouseRelation> houseRelations) {
		this.houseRelationsReference = houseRelations;
	}

	public Set<HouseRelation> getHouseRelationsDestiny() {
		return houseRelationsDestiny;
	}

	public void setHouseRelationsDestiny(Set<HouseRelation> houseRelationsDestiny) {
		this.houseRelationsDestiny = houseRelationsDestiny;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		House other = (House) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "House [id=" + id + ", name=" + name + ", alternateName="
				+ alternateName + ", sectors=" + sectors
				+ ", houseRelationsReference=" + houseRelationsReference
				+ ", houseRelationsDestiny=" + houseRelationsDestiny + "]";
	}

}
