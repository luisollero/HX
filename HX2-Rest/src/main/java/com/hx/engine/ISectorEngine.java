package com.hx.engine;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.House;
import com.hx.model.dto.Sector;

public interface ISectorEngine {

	@Transactional
	void saveOrUpdate(final Sector sector);

	@Transactional
	Sector getById(String id);

	@Transactional
	void delete(Sector sector);

	@Transactional
	Collection<Sector> findAll();

	/**
	 * Devuelve un {@link ArrayList} con los sectores vecinos del {@link Sector}
	 * pasado como filtro.
	 * 
	 * @param central
	 * @return
	 */
	@Transactional
	ArrayList<Sector> getNeighbours(Sector central);

	/**
	 * Devuelve todos los {@link Sector} para una casa pasada como filtro.
	 * @param aux
	 * @return
	 */
	@Transactional
	Collection<Sector> findByHouse(House aux);

	/**
	 * Devuelve todos los {@link Sector} un nombre.
	 * @return
	 */
	@Transactional
	Collection<Sector> findByName(String name);

}
