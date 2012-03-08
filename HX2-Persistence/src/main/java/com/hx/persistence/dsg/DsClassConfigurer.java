package com.hx.persistence.dsg;

import java.lang.reflect.Method;

public interface DsClassConfigurer {

	DsMethodConfiguration<?> configureMethod(Method method) throws Exception;
}
