package com.hx.engine;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.Communication;

public interface ICommunicationEngine {

	@Transactional
	void saveOrUpdate(final Communication communication);

	@Transactional
	Communication getById(String id);

	@Transactional
	void delete(Communication communication);

	@Transactional
	Collection<com.hx.engine.pojo.Communication> findAll();

}
