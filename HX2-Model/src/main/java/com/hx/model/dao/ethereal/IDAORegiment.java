package com.hx.model.dao.ethereal;

import java.util.Collection;

import com.hx.model.dao.IDAOCommon;
import com.hx.model.dto.House;
import com.hx.model.dto.Regiment;
import com.hx.model.dto.Sector;


public interface IDAORegiment extends IDAOCommon<Regiment> {

	Collection<Regiment> findByHouse(House house);

	Collection<Regiment> findByHouseAndSector(House house, Sector sector);

}
