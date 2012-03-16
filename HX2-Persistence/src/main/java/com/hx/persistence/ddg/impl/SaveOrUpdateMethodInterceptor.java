package com.hx.persistence.ddg.impl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import com.hx.persistence.tx.TransactionManager;

public class SaveOrUpdateMethodInterceptor extends TxMethodInterceptor {

	public SaveOrUpdateMethodInterceptor(TransactionManager transactionManager) {
		super(transactionManager);
	}

	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {

		this.getCurrentSession().saveOrUpdate(args[0]);
		return null;
	}
}