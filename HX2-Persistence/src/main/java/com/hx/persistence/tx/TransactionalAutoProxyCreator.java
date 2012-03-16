package com.hx.persistence.tx;

import java.lang.reflect.Method;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.transaction.annotation.Transactional;

import com.hx.persistence.tx.impl.TransactionalInterceptor;

public class TransactionalAutoProxyCreator extends AbstractAutoProxyCreator {
	private static final long serialVersionUID = 1L;

	private TransactionalInterceptor transactionalInterceptor;

	public TransactionalAutoProxyCreator() {
		super();
	}

	@Override
	protected Object[] getAdvicesAndAdvisorsForBean(Class clazz, String arg1,
			TargetSource arg2) throws BeansException {

		Class superClazz = clazz;

		while (superClazz != null) {
			for (Class interf : superClazz.getInterfaces()) {
				for (Method method : interf.getMethods()) {
					Transactional annotation = method
							.getAnnotation(Transactional.class);
					if (annotation != null) {
						return new Object[] { transactionalInterceptor, };
					}
				}
			}
			superClazz = superClazz.getSuperclass();
		}

		return DO_NOT_PROXY;
	}

	public TransactionalInterceptor getTransactionalInterceptor() {
		return transactionalInterceptor;
	}

	public void setTransactionalInterceptor(
			TransactionalInterceptor transactionalInterceptor) {
		this.transactionalInterceptor = transactionalInterceptor;
	}
}
