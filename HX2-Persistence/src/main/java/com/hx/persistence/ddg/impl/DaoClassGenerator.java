package com.hx.persistence.ddg.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import com.hx.persistence.ddg.NoDynamicDaoGeneration;
import com.hx.persistence.tx.TransactionManager;

public class DaoClassGenerator {

	private DaoClassGenerator() {
		super();
	}

	public static <P, D> Class<D> generateDaoClass(Class<P> pojoClass,
			Class<D> daoClass, TransactionManager transactionManager) {

		final Map<Method, Integer> map = new HashMap<Method, Integer>();
		List<MethodInterceptor> interceptors = new LinkedList<MethodInterceptor>();
		interceptors.add(new InvokeSuperMethodInterceptor());
		for (Method method : daoClass.getMethods()) {

			if (method.getAnnotation(NoDynamicDaoGeneration.class) == null) {
				MethodInterceptor interceptor = MethodParser.parse(pojoClass,
						method.getName(), transactionManager);
				if (interceptor != null) {
					map.put(method, new Integer(interceptors.size()));
					interceptors.add(interceptor);
				}
			}

		}

		Enhancer enhancer = new Enhancer();
		if (daoClass.isInterface()) {
			enhancer.setInterfaces(new Class[] { daoClass });
		} else {
			enhancer.setSuperclass(daoClass);
		}
		enhancer.setCallbackFilter(new CallbackFilter() {

			@Override
			public int accept(Method method) {
				Integer integer = map.get(method);
				if (integer == null) {
					return 0;
				}
				return integer.intValue();
			}
		});

		Class<?>[] callbackTypes = new Class<?>[interceptors.size()];
		for (int i = 0; i < callbackTypes.length; i++) {
			callbackTypes[i] = MethodInterceptor.class;
		}
		enhancer.setCallbackTypes(callbackTypes);

		@SuppressWarnings("unchecked")
		Class<D> dynamicDaoClass = enhancer.createClass();

		Callback[] callbacks = interceptors.toArray(new Callback[0]);
		Enhancer.registerStaticCallbacks(dynamicDaoClass, callbacks);

		return dynamicDaoClass;
	}
}
