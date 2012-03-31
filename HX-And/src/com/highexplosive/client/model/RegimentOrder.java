package com.highexplosive.client.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "regiment_orders")
public class RegimentOrder {
	
	@DatabaseField(id = true)
	private Integer regimentId;
	
	@DatabaseField
	private Integer destinationSectorId;
	
	@DatabaseField
	private AttackOrder attackOrder;

	// Needed by the ORM
	public RegimentOrder() {
	}

	public Integer getRegimentId() {
		return regimentId;
	}

	public void setRegimentId(Integer regimentId) {
		this.regimentId = regimentId;
	}

	public Integer getDestinationSectorId() {
		return destinationSectorId;
	}

	public void setDestinationSectorId(Integer destinationSectorId) {
		this.destinationSectorId = destinationSectorId;
	}

	public AttackOrder getAttackOrder() {
		return attackOrder;
	}

	public void setAttackOrder(AttackOrder attackOrder) {
		this.attackOrder = attackOrder;
	}

}
