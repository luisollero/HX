package com.hx.persistence.services.a;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hx.persistence.model.a.A;

public interface AService {

	@Transactional(readOnly = false)
	void save(A a);

	@Transactional(readOnly = true)
	List<A> find();

	@Transactional(readOnly = true)
	A getById(long id);

	@Transactional(readOnly = true)
	List<A> scroll();

	@Transactional(readOnly = true)
	List<A> findOrderAscByNameLimited(int offset, int limit);
}
