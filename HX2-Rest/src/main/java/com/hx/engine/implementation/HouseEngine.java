package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.hx.engine.IHouseEngine;
import com.hx.model.dao.ethereal.IDAOHouse;
import com.hx.model.dto.House;

public class HouseEngine implements IHouseEngine {

	private IDAOHouse daoHouse;
	
	public Collection<com.hx.engine.pojo.House> findAll() {
		Collection<House> list = daoHouse.find();
		ArrayList<com.hx.engine.pojo.House> returnList = new ArrayList<com.hx.engine.pojo.House>();
		com.hx.engine.pojo.House pojo;
		for (House house : list) {
			pojo = new com.hx.engine.pojo.House();
			pojo.setName(house.getName());
			pojo.setId(house.getId());
			returnList.add(pojo);
		}
		return returnList;
	}

	public House getById(String id) {
		return daoHouse.getById(id);
	}

	public void saveOrUpdate(House house) {
		daoHouse.saveOrUpdate(house);
	}

	public void delete(House house) {
		daoHouse.delete(house);
	}

	public IDAOHouse getDaoHouse() {
		return daoHouse;
	}

	public void setDaoHouse(IDAOHouse daoHouse) {
		this.daoHouse = daoHouse;
	}


}
