package com.hx.persistence.tx.impl;

import java.util.concurrent.Callable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalInterceptor implements MethodInterceptor {

	private TransactionManagerImpl transactionManager;

	public TransactionalInterceptor() {
		super();
	}

	@Override
	public Object invoke(final MethodInvocation methodInvocation)
			throws Throwable {

		Transactional transactional = methodInvocation.getMethod()
				.getAnnotation(Transactional.class);
		if (transactional == null) {
			return methodInvocation.proceed();
		}

		return this.transactionManager.runInTransaction(
				transactional.readOnly(), transactional.propagation(),
				new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						try {
							return methodInvocation.proceed();
						} catch (Exception e) {
							throw e;
						} catch (Throwable t) {
							throw new Error(t);
						}
					}
				});
	}

	public TransactionManagerImpl getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManagerImpl transactionManager) {
		this.transactionManager = transactionManager;
	}
}
