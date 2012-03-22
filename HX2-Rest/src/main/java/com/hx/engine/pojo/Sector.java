package com.hx.engine.pojo;

/**
 * Simple representation of the {@link com.hx.model.dto.Sector}
 * @author Luis Ollero
 *
 */
public class Sector implements Pojo {
	
	private Integer id;
	private Integer coordX;
	private Integer coordY;

	public Sector(int id) {
		this.id = id;
	}

	public Sector(com.hx.model.dto.Sector auxSector) {
		this.id = auxSector.getId();
		this.coordX = auxSector.getCoordX();
		this.coordY = auxSector.getCoordY();
	}

	public Object toDTO() {
		com.hx.model.dto.Sector sector = new com.hx.model.dto.Sector();
		sector.setId(id);
		sector.setCoordX(coordX);
		sector.setCoordY(coordY);
		return sector;
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
	
}
