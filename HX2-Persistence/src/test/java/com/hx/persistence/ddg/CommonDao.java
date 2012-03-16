package com.hx.persistence.ddg;

import java.util.List;

public interface CommonDao<P> {

	void save(P t);

	void update(P t);

	void delete(P t);

	P get();

	P notNullGet();

	List<P> find();
}
