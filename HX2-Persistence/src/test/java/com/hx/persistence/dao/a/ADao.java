package com.hx.persistence.dao.a;

import java.util.Enumeration;
import java.util.List;

import com.hx.persistence.ddg.CommonDao;
import com.hx.persistence.model.a.A;
import com.hx.persistence.model.b.B;

public interface ADao extends CommonDao<A> {

	A getById(long id);

	A notNullGetById(long id);

	A getByName(String name);

	A notNullGetByName(String name);

	List<A> findByName(String name);

	A getByB(B b);

	A notNullGetByB(B b);

	List<A> findByB(B b);

	Enumeration<A> scroll();

	List<A> findOrderAscByNameLimited(int offset, int limit);
}
