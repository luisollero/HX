package com.hx.persistence.dsg;

import java.lang.reflect.Method;

public class DefaultDsClassConfigurer implements DsClassConfigurer {

	@Override
	public DsMethodConfiguration<?> configureMethod(Method method) {

		return new DefaultDsMethodConfiguration<Object>();
	}
}
