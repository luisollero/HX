package com.hx.engine.pojo;


/**
 * Serializable class for REST server.
 * 
 * @author Luis Ollero
 *
 */
public class House implements Pojo {

	private String id;
	private String name;
	private String alternateName;

	public House() {}
	
	public House(com.hx.model.dto.House anotherHouse) {
		this.id = anotherHouse.getId();
		this.name = anotherHouse.getName();
		this.alternateName = anotherHouse.getAlternateName();
		// this. = anotherHouse.getSectors();
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

	public Object toDTO() {
		com.hx.model.dto.House house = new com.hx.model.dto.House();
		house.setId(this.id);
		house.setName(this.name);
		house.setAlternateName(this.alternateName);
		return house;
	}

}
