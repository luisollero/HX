package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.hx.engine.ISectorEngine;
import com.hx.model.dao.ethereal.IDAOSector;
import com.hx.model.dto.House;
import com.hx.model.dto.Sector;

/**
 * Engine to work with sectors
 * 
 * @author Luis Ollero
 * 
 */
public class SectorEngine implements ISectorEngine {

	private IDAOSector daoSector;

	public IDAOSector getDaoSector() {
		return daoSector;
	}

	public void setDaoSector(IDAOSector daoSector) {
		this.daoSector = daoSector;
	}

	public void saveOrUpdate(final Sector sector) {
		daoSector.saveOrUpdate(sector);
	}

	public void delete(Sector sector) {
		daoSector.delete(sector);
	}

	public ArrayList<com.hx.engine.pojo.Sector> findAll() {
		Collection<Sector> list = daoSector.findWithLazies();
		ArrayList<com.hx.engine.pojo.Sector> returnList = new ArrayList<com.hx.engine.pojo.Sector>();
		com.hx.engine.pojo.Sector pojo;
		for (Sector sector : list) {
			pojo = new com.hx.engine.pojo.Sector(sector);
			returnList.add(pojo);
		}
		return returnList;
	}

	public com.hx.engine.pojo.Sector getById(Integer id) {
		Sector auxSector = daoSector.getById(id);
		if (auxSector != null) {
			return new com.hx.engine.pojo.Sector(auxSector);
		}
		return new com.hx.engine.pojo.Sector(-1);
	}

	public ArrayList<Sector> getNeighbors(Sector central) {
		ArrayList<Sector> result = new ArrayList<Sector>();
		Integer x = central.getCoordX();
		Integer y = central.getCoordY();

		// Posiciones de los vecinos
		// (x-1,y-1),(x-1,y+1),(x,y+2),(x+1,y+1),(x+1,y-1),(x,y-2)

		//FIXME: User coords
		result.addAll(daoSector.findByCoordXAndCoordY(x - 1, y - 1));
		result.addAll(daoSector.findByCoordXAndCoordY(x - 1, y + 1));
		result.addAll(daoSector.findByCoordXAndCoordY(x, y + 2));
		result.addAll(daoSector.findByCoordXAndCoordY(x - 1, y - 1));
		result.addAll(daoSector.findByCoordXAndCoordY(x - 1, y - 1));
		result.addAll(daoSector.findByCoordXAndCoordY(x - 1, y - 1));
//		result.add(daoSector.getById((x - 1) + "," + (y - 1)));
//		result.add(daoSector.getById((x - 1) + "," + (y + 1)));
//		result.add(daoSector.getById((x) + "," + (y + 2)));
//		result.add(daoSector.getById((x + 1) + "," + (y + 1)));
//		result.add(daoSector.getById((x + 1) + "," + (y - 1)));
//		result.add(daoSector.getById((x) + "," + (y - 2)));

		return result;
	}

	public ArrayList<com.hx.engine.pojo.Sector> findByHouse(House house) {
		Collection<Sector> list = daoSector.findByHouse(house);
		ArrayList<com.hx.engine.pojo.Sector> returnList = new ArrayList<com.hx.engine.pojo.Sector>();
		com.hx.engine.pojo.Sector pojo;
		for (Sector sector : list) {
			pojo = new com.hx.engine.pojo.Sector(sector);
			returnList.add(pojo);
		}
		return returnList;
		
	}

	public ArrayList<com.hx.engine.pojo.Sector> findByName(String name) {
		Collection<Sector> list = daoSector.findByName(name);
		ArrayList<com.hx.engine.pojo.Sector> returnList = new ArrayList<com.hx.engine.pojo.Sector>();
		com.hx.engine.pojo.Sector pojo;
		for (Sector sector : list) {
			pojo = new com.hx.engine.pojo.Sector(sector);
			returnList.add(pojo);
		}
		return returnList;
	}

}
