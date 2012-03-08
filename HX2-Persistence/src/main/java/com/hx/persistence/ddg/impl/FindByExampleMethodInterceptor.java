package com.hx.persistence.ddg.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.hx.persistence.tx.TransactionManager;

public class FindByExampleMethodInterceptor extends FindMethodInterceptor {

	public FindByExampleMethodInterceptor(
			TransactionManager transactionManager, Class<?> clazz,
			FindType findType, boolean withLazies,
			List<Boolean> ascOrderProperties, List<String> orderProperties,
			boolean limited) {

		super(transactionManager, clazz, findType, withLazies,
				ascOrderProperties, orderProperties, limited);
	}

	@Override
	protected void configure(Criteria criteria, Object[] args) {

		criteria.add(Example.create(args[0]));

	}
}
