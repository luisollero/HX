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
	com.hx.engine.pojo.Sector getById(String id);

	@Transactional
	void delete(Sector sector);

	@Transactional
	Collection<Sector> findAll();

	/**
	 * Returns an {@link ArrayList} with the neighboring sectors.
	 * 
	 * @param central - Central {@link Sector}
	 * @return
	 */
	@Transactional
	ArrayList<Sector> getNeighbours(Sector central);

	/**
	 * Return all the {@link Sector} .
	 * @param ownerHouse - {@link House} owner of the sectors.
	 * @return
	 */
	@Transactional
	Collection<Sector> findByHouse(House ownerHouse);

	/**
	 * Devuelve todos los {@link Sector} un nombre.
	 * @return
	 */
	@Transactional
	Collection<Sector> findByName(String name);

}
