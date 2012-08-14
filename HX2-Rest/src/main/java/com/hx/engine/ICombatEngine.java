package com.hx.engine;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.Combat;

public interface ICombatEngine {

	@Transactional
	void saveOrUpdate(final Combat combat);

	@Transactional
	Combat getById(Integer id);

	@Transactional
	void delete(Combat combat);

	@Transactional
	Collection<Combat> findAll();

}
