package com.hx.engine;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.House;

public interface IHouseEngine {

	@Transactional
	void saveOrUpdate(final House house);

	@Transactional
	House getById(String id);

	@Transactional
	void delete(House house);

	@Transactional
	Collection<House> findAll();

}
