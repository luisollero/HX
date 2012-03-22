package com.hx.engine;


import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.Personality;

public interface IPersonalityEngine {

	@Transactional
	void saveOrUpdate(final Personality user);
	
	@Transactional
	com.hx.engine.pojo.Personality getById(Integer id);
	
	@Transactional
	void delete(Personality user);
	
	@Transactional
	Collection<Personality> findAll();
	
}
