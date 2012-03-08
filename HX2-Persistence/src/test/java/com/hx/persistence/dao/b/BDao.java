package com.hx.persistence.dao.b;

import java.util.Enumeration;
import java.util.List;

import com.hx.persistence.ddg.CommonDao;
import com.hx.persistence.model.b.B;

public interface BDao extends CommonDao<B> {

	B getById(long id);

	B notNullGetById(long id);

	B getByName(String name);

	B notNullGetByName(String name);

	List<B> findByName(String name);

	List<B> findByNameLessThan(String name);

	Enumeration<B> scroll();
}
