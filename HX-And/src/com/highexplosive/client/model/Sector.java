package com.highexplosive.client.model;

/**
 * Sector of the map
 * @author Luis Ollero
 * 
 */
public class Sector {

	private String name;
	private Integer id;
	private Integer coordX;
	private Integer coordY;
	private String faction;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

}
