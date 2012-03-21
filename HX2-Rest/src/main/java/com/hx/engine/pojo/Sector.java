package com.hx.engine.pojo;

public class Sector implements Pojo {
	
	private Integer id;
	private Integer coordX;
	private Integer coordY;

	public Sector(int i) {
		this.id = i;
	}

	public Sector(com.hx.model.dto.Sector auxSector) {
		this.id = auxSector.getId();
		this.coordX = auxSector.getCoordX();
		this.coordY = auxSector.getCoordY();
	}

	public Object toDTO() {
		// TODO Auto-generated method stub
		return null;
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
