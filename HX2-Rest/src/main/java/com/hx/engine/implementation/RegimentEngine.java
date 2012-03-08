package com.hx.engine.implementation;

import java.util.Collection;

import com.hx.engine.IRegimentEngine;
import com.hx.model.dao.ethereal.IDAORegiment;
import com.hx.model.dto.House;
import com.hx.model.dto.Regiment;
import com.hx.model.dto.Sector;


/**
 * Clase de utilidad para facilitar el control de transacciones en el trabajo
 * con regimientos.
 * 
 * @author kineas
 * 
 */
public class RegimentEngine implements IRegimentEngine {
	
	private IDAORegiment daoRegiment = null;
	

	public IDAORegiment getDaoRegiment() {
		return daoRegiment;
	}

	public void setDaoRegiment(IDAORegiment daoRegiment) {
		this.daoRegiment = daoRegiment;
	}

	public void saveOrUpdate(final Regiment regiment) {
		daoRegiment.saveOrUpdate(regiment);
	}
	
	public void delete(Regiment regiment) {
		daoRegiment.delete(regiment);
	}
	
	public Collection<Regiment> findAll() {
		return daoRegiment.find();
	}
	
	public Regiment getById(String id) {
		return daoRegiment.getById(id);
	}

	public Collection<Regiment> findByHouse(House house) {
		return daoRegiment.findByHouse(house);
	}

	public Collection<Regiment> findByHouseAndSector(House house, Sector sector) {
		return daoRegiment.findByHouseAndSector(house,sector);
	}
	
}
