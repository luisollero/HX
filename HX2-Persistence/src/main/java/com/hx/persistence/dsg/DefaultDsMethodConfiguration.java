package com.hx.persistence.dsg;

import java.lang.reflect.Method;

public class DefaultDsMethodConfiguration<B> implements
		DsMethodConfiguration<B> {

	@Override
	public B beforeCallDao(Object[] args) throws Exception {
		return null;
	}

	@Override
	public Object callDao(Object[] args, B beforeValue, Object dao,
			Method daoMethod) throws Exception {

		return daoMethod.invoke(dao, args);
	}

	@Override
	public Object afterCallDao(Object[] args, B beforeValue, Object daoValue)
			throws Exception {

		return daoValue;
	}

	public Method getDaoMethod(Object dao, Method dsMethod) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
