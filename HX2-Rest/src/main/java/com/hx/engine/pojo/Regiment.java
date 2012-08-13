package com.hx.engine.pojo;


/**
 * Serializable class for REST server.
 * 
 * @author Luis Ollero
 *
 */
public class Regiment implements Pojo {

	private String name;

	public Regiment() {}
	
	public Regiment(com.hx.model.dto.Regiment anotherHouse) {
		this.name = anotherHouse.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object toDTO() {
		com.hx.model.dto.Regiment regiment = new com.hx.model.dto.Regiment();
		regiment.setName(this.name);
		return regiment;
	}

}
