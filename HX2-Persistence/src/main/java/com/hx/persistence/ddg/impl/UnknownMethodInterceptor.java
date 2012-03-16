package com.hx.persistence.ddg.impl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class UnknownMethodInterceptor implements MethodInterceptor {

	public UnknownMethodInterceptor() {
		super();
	}

	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {

		throw new RuntimeException("Unknown method: " + method);
	}
}