package com.hx.persistence.ddg.impl;

import net.sf.cglib.proxy.MethodInterceptor;

import org.hibernate.Session;

import com.hx.persistence.tx.TransactionManager;

public abstract class TxMethodInterceptor implements MethodInterceptor {

	public final TransactionManager transactionManager;

	public TxMethodInterceptor(TransactionManager transactionManager) {
		super();
		this.transactionManager = transactionManager;
	}

	protected Session getCurrentSession() {
		return this.transactionManager.getCurrentSession();
	}
}
