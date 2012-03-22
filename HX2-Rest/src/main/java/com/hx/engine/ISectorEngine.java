package com.hx.engine;

import java.util.ArrayList;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.House;
import com.hx.model.dto.Sector;

public interface ISectorEngine {

	@Transactional
	void saveOrUpdate(final Sector sector);

	@Transactional
	com.hx.engine.pojo.Sector getById(Integer id);

	@Transactional
	void delete(Sector sector);

	@Transactional
	ArrayList<com.hx.engine.pojo.Sector> findAll();

	/**
	 * Returns an {@link ArrayList} with the neighboring sectors.
	 * 
	 * @param central - Central {@link Sector}
	 * @return
	 */
	@Transactional
	ArrayList<Sector> getNeighbors(Sector central);

	/**
	 * Return all the {@link Sector} .
	 * @param ownerHouse - {@link House} owner of the sectors.
	 * @return
	 */
	@Transactional
	ArrayList<com.hx.engine.pojo.Sector> findByHouse(House ownerHouse);

	/**
	 * Devuelve todos los {@link Sector} un nombre.
	 * @return
	 */
	@Transactional
	ArrayList<com.hx.engine.pojo.Sector> findByName(String name);

}
