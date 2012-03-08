package com.hx.persistence.dsg.impl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.hx.persistence.dsg.DsMethodConfiguration;

public class DsMethodInterceptor<B> implements MethodInterceptor {

	private final DsMethodConfiguration<B> dsMethodConfiguration;
	private final Object dao;
	private final Method daoMethod;

	public DsMethodInterceptor(DsMethodConfiguration<B> dsMethodConfiguration,
			Object dao, Method dsMethod) throws Exception {

		this.dsMethodConfiguration = dsMethodConfiguration;
		this.dao = dao;
		this.daoMethod = dsMethodConfiguration.getDaoMethod(dao, dsMethod);
	}

	@Override
	public Object intercept(final Object object, final Method method,
			final Object[] args, final MethodProxy methodProxy)
			throws Throwable {

		B before = dsMethodConfiguration.beforeCallDao(args);

		Object value = dsMethodConfiguration.callDao(args, before, dao,
				daoMethod);

		return dsMethodConfiguration.afterCallDao(args, before, value);
	}
}
