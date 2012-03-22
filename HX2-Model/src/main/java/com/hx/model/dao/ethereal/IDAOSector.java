package com.hx.model.dao.ethereal;

import java.util.Collection;

import com.hx.model.dao.IDAOCommon;
import com.hx.model.dto.House;
import com.hx.model.dto.Sector;


public interface IDAOSector extends IDAOCommon<Sector> {

	Collection<Sector> findByHouse(House house);

	Collection<Sector> findByName(String name);
	
	Collection<Sector> findByCoordXAndCoordY(Integer coordX, Integer coordY);

}
