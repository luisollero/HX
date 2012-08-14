package com.hx.model.dto;

public enum Morale {
	
	DEMORALIZED(0), NORMAL(1), SUPERB(2);
	
	private int value;

	Morale(int i) {
		this.value = i;
	}

	public int getValue() {
		return value;
	}
	
}
