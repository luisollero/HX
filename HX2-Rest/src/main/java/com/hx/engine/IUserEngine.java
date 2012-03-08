package com.hx.engine;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.model.dto.Personality;
import com.hx.model.dto.User;


public interface IUserEngine {

	@Transactional
	void saveOrUpdate(final User user);
	
	@Transactional
	User getById(String id);
	
	@Transactional
	void delete(User user);
	
	@Transactional
	Collection<User> findAll();

	@Transactional
	ArrayList<User> findByPersonality(Personality personality);
	
}
