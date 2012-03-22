package com.hx.model;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Interceptor extends EmptyInterceptor implements
		ApplicationContextAware {

	private static final long serialVersionUID = 5744311113375362230L;
	private Logger _log = Logger.getLogger(Interceptor.class);

	protected ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext arg0)
	throws BeansException {
		this.applicationContext = arg0;
	}
	
	// Before deleting an entity
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		
		String message = "'onDelete' for the object "
			+ entity.getClass().getName() + " with id = '" + id + "'";

		_log.debug(message);
		super.onDelete(entity, id, state, propertyNames, types);
	}
	
	// Before updating an entity
	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		
		String message = "'onFlushDirty' for the object "
			+ entity.getClass().getName() + " with toString = '" + entity.toString() + "'";

		_log.debug(message);
		return super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
	}
	
	// Before saving an entity
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		
		String message = "'onFlushDirty' for the object "
			+ entity.getClass().getName() + " with toString = '" + entity.toString() + "'";

		_log.debug(message);
		return super.onSave(entity, id, state, propertyNames, types);
	}
	
}
