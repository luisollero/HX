package com.hx.model.dto;

public enum Ability {

	NONE(0), MEDIUM(1), ELITE(2), MASTER(3);
	
	private int value;

	Ability(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
