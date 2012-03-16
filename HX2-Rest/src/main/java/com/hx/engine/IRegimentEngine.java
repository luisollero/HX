package com.hx.engine;


import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.House;
import com.hx.model.dto.Regiment;
import com.hx.model.dto.Sector;

public interface IRegimentEngine {

	
	@Transactional
	void saveOrUpdate(final Regiment user);
	
	@Transactional
	Regiment getById(String id);
	
	@Transactional
	void delete(Regiment user);
	
	@Transactional
	Collection<Regiment> findAll();

	@Transactional
	Collection<Regiment> findByHouse(House house);

	@Transactional
	Collection<Regiment> findByHouseAndSector(House house, Sector sector);
}
