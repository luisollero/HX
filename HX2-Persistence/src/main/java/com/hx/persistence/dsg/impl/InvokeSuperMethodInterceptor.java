package com.hx.persistence.dsg.impl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class InvokeSuperMethodInterceptor implements MethodInterceptor {

	public InvokeSuperMethodInterceptor() {
		super();
	}

	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {

		return methodProxy.invokeSuper(object, args);
	}
}