package com.hx.model.dto;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Relaciones entre las casas. Una relación es conmutativa, es decir, da igual a
 * qué lado de ella se encuentre una casa, significaría exactamente lo mismo si
 * estuviese como referencia en vez de como destino y viceversa.
 * 
 * Por lo dicho antes, cuando algo afecte a una relación debería, de hecho,
 * afectar siempre a dos.
 * 
 * @author kineas
 * 
 */
@Entity
@Table(name="hx_house_relation", schema="hx")
public class HouseRelation {

	@EmbeddedId
	private HouseRelationId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hx_house_relation_reference", nullable = false, insertable = false, updatable = false)
	private House houseReference; // Casa referencia
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hx_house_relation_destiny", nullable = false, insertable = false, updatable = false)
	private House houseDestiny; // Casa destino
	
	@Column(name="hx_house_relation_status", nullable=false)
	private Integer relationStatus; // Valor entero que representa el estado amistoso entre las casas.

	// GETTERS AND SETTERS
	
	public HouseRelationId getId() {
		return id;
	}
	
	public void setId(HouseRelationId id) {
		this.id = id;
	}
	
	public House getHouseReference() {
		return houseReference;
	}
	
	public void setHouseReference(House houseReference) {
		this.houseReference = houseReference;
	}
	
	public House getHouseDestiny() {
		return houseDestiny;
	}
	
	public void setHouseDestiny(House houseDestiny) {
		this.houseDestiny = houseDestiny;
	}
	
	public Integer getRelationStatus() {
		return relationStatus;
	}
	
	public void setRelationStatus(Integer relationStatus) {
		this.relationStatus = relationStatus;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((houseDestiny == null) ? 0 : houseDestiny.hashCode());
		result = prime * result
				+ ((houseReference == null) ? 0 : houseReference.hashCode());
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
		HouseRelation other = (HouseRelation) obj;

		if (houseDestiny != null && houseReference != null) {
			if (houseDestiny.equals(other.getHouseDestiny())
					&& houseReference.equals(other.getHouseReference()))
				return true;
			if (houseDestiny.equals(other.getHouseReference())
					&& houseReference.equals(other.getHouseDestiny()))
				return true;
		}
		return false;
	}

}
