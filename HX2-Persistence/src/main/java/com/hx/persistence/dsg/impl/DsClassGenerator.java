package com.hx.persistence.dsg.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import com.hx.persistence.dsg.DsClassConfigurer;
import com.hx.persistence.dsg.DsMethodConfiguration;
import com.hx.persistence.dsg.NoDynamicDsGeneration;

public class DsClassGenerator {

	private DsClassGenerator() {
		super();
	}

	public static <P, S> Class<S> generateDsClass(Object dao, Class<S> dsClass,
			DsClassConfigurer dsConfigurer) throws Exception {

		final Map<Method, Integer> map = new HashMap<Method, Integer>();
		List<MethodInterceptor> interceptors = new LinkedList<MethodInterceptor>();
		interceptors.add(new InvokeSuperMethodInterceptor());
		for (Method dsMethod : dsClass.getMethods()) {

			if (dsMethod.getAnnotation(NoDynamicDsGeneration.class) == null) {
				DsMethodConfiguration<?> dsMethodConfiguration = dsConfigurer
						.configureMethod(dsMethod);
				DsMethodInterceptor<?> interceptor = createMethodInterceptor(
						dsMethodConfiguration, dao, dsMethod);
				if (interceptor != null) {
					map.put(dsMethod, new Integer(interceptors.size()));
					interceptors.add(interceptor);
				}
			}
		}

		Enhancer enhancer = new Enhancer();
		if (dsClass.isInterface()) {
			enhancer.setInterfaces(new Class[] { dsClass });
		} else {
			enhancer.setSuperclass(dsClass);
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
		Class<S> dynamicDsClass = enhancer.createClass();

		Callback[] callbacks = interceptors.toArray(new Callback[0]);
		Enhancer.registerStaticCallbacks(dynamicDsClass, callbacks);

		return dynamicDsClass;
	}

	private static <B> DsMethodInterceptor<B> createMethodInterceptor(

	DsMethodConfiguration<B> dsMethodConfiguration, Object dao, Method dsMethod)
			throws Exception {

		return new DsMethodInterceptor<B>(dsMethodConfiguration, dao, dsMethod);
	}
}
