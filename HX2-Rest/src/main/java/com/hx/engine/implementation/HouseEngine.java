package com.hx.engine.implementation;

import java.util.Collection;

import com.hx.engine.IHouseEngine;
import com.hx.model.dao.ethereal.IDAOHouse;
import com.hx.model.dto.House;

public class HouseEngine implements IHouseEngine {

	private IDAOHouse daoHouse;
	
	public Collection<House> findAll() {
		return daoHouse.find();
	}

	public House getById(String id) {
		if (id != null) {
			return daoHouse.getById(id);
		}
		return new House();
	}

	public void saveOrUpdate(House house) {
		daoHouse.saveOrUpdate(house);
	}

	public IDAOHouse getDaoHouse() {
		return daoHouse;
	}

	public void setDaoHouse(IDAOHouse daoHouse) {
		this.daoHouse = daoHouse;
	}

	public void delete(House house) {
		daoHouse.delete(house);
	}

}
