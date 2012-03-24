package com.hx.engine;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.Message;

public interface IMessageEngine {

	@Transactional
	void saveOrUpdate(final Message message);

	@Transactional
	com.hx.engine.pojo.Message getById(Integer id);

	@Transactional
	void delete(Message message);

	@Transactional
	Collection<com.hx.engine.pojo.Message> findAll();

}
