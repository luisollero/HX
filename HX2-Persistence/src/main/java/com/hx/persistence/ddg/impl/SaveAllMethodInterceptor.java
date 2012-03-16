package com.hx.persistence.ddg.impl;

import java.lang.reflect.Method;
import java.util.Collection;

import net.sf.cglib.proxy.MethodProxy;

import com.hx.persistence.tx.TransactionManager;

public class SaveAllMethodInterceptor extends TxMethodInterceptor {

	public SaveAllMethodInterceptor(TransactionManager transactionManager) {
		super(transactionManager);
	}

	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {

		Collection<?> collection = (Collection<?>) args[0];
		for (Object item : collection) {
			this.getCurrentSession().save(item);
		}
		return null;
	}
}