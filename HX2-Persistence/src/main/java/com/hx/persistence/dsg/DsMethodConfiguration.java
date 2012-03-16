package com.hx.persistence.dsg;

import java.lang.reflect.Method;

public interface DsMethodConfiguration<B> {

	Method getDaoMethod(Object dao, Method dsMethod) throws Exception;

	B beforeCallDao(Object[] args) throws Exception;

	Object callDao(Object[] args, B beforeValue, Object dao, Method daoMethod)
			throws Exception;

	Object afterCallDao(Object[] args, B beforeValue, Object daoValue)
			throws Exception;
}
