package com.hx.persistence.services.b;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hx.persistence.model.b.B;

public interface BService {

	@Transactional(readOnly = false)
	void save(B b);

	@Transactional(readOnly = true)
	List<B> find();

	@Transactional(readOnly = true)
	B getById(long id);

	@Transactional(readOnly = true)
	List<B> findByNameLessThan(String name);

	@Transactional(readOnly = true)
	List<B> scroll();
}
