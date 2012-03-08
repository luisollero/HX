package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.hx.engine.ISectorEngine;
import com.hx.model.dao.ethereal.IDAOSector;
import com.hx.model.dto.House;
import com.hx.model.dto.Sector;

/**
 * Clase de utilidad para facilitar el control de transacciones en el trabajo
 * con sectores.
 * 
 * @author kineas
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

	public Collection<Sector> findAll() {
		return daoSector.find();
	}

	public Sector getById(String id) {
		return daoSector.getById(id);
	}

	public ArrayList<Sector> getNeighbours(Sector central) {
		ArrayList<Sector> result = new ArrayList<Sector>();
		Integer x = central.getCoordX();
		Integer y = central.getCoordY();

		// Posiciones de los vecinos
		// (x-1,y-1),(x-1,y+1),(x,y+2),(x+1,y+1),(x+1,y-1),(x,y-2)

		result.add(daoSector.getById((x - 1) + "," + (y - 1)));
		result.add(daoSector.getById((x - 1) + "," + (y + 1)));
		result.add(daoSector.getById((x) + "," + (y + 2)));
		result.add(daoSector.getById((x + 1) + "," + (y + 1)));
		result.add(daoSector.getById((x + 1) + "," + (y - 1)));
		result.add(daoSector.getById((x) + "," + (y - 2)));

		return result;
	}

	public Collection<Sector> findByHouse(House house) {
		return daoSector.findByHouse(house);
	}

	public Collection<Sector> findByName(String name) {
		return daoSector.findByName(name);
	}

}
