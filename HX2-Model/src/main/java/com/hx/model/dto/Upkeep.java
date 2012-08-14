package com.hx.model.dto;

public enum Upkeep {

	NONE(0), MAINTENANCE(1), FULL(2);
	
	private int value;
	
	Upkeep(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
