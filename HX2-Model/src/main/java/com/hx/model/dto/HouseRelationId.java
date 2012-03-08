package com.hx.model.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Id para la relación entre casas.
 * 
 * @author kineas
 */
@Embeddable
public class HouseRelationId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "hx_house_relation_reference", nullable = false, length=23)
	private String houseReferenceId; // Casa referencia
	
	@Column(name = "hx_house_relation_destiny", nullable = false, length=23)
	private String houseDestinyId; // Casa destino

	public String getHouseReferenceId() {
		return houseReferenceId;
	}

	public void setHouseReferenceId(String houseReferenceId) {
		this.houseReferenceId = houseReferenceId;
	}

	public String getHouseDestinyId() {
		return houseDestinyId;
	}

	public void setHouseDestinyId(String houseDestinyId) {
		this.houseDestinyId = houseDestinyId;
	}
	
}
