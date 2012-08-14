package com.hx.model.dto;

public enum Rank {
	
	MILLITIA(0), GREEN(1), REGULAR(2), VETERAN(3), ELITE(4);
	
	private int value;

	Rank(int i) {
		this.value = i;
	}
	
	public int getValue() {
		return value; 
	}
}
