package com.hx.persistence.ddg;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.hx.persistence.ddg.impl.DaoClassGenerator;
import com.hx.persistence.tx.TransactionManager;

public class DaoFactoryBean<P, D> implements FactoryBean, InitializingBean {

	private Class<P> pojoClass;
	private Class<D> daoClass;
	private TransactionManager transactionManager;
	private Class<D> generateDaoClass;
	private List<Method> writeMethods;

	public DaoFactoryBean() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		generateDaoClass = DaoClassGenerator.generateDaoClass(pojoClass,
				daoClass, transactionManager);

		writeMethods = new LinkedList<Method>();
		for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(
				generateDaoClass).getPropertyDescriptors()) {
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			if (propertyType != null
					&& TransactionManager.class.isAssignableFrom(propertyType)
					&& propertyType.isAssignableFrom(transactionManager
							.getClass())) {
				writeMethods.add(propertyDescriptor.getWriteMethod());
			}
		}
	}

	@Override
	public Object getObject() throws Exception {

		D dao = generateDaoClass.newInstance();

		for (Method method : writeMethods) {
			method.invoke(dao, transactionManager);
		}

		return dao;
	}

	@Override
	public Class<D> getObjectType() {
		return generateDaoClass;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Class<P> getPojoClass() {
		return pojoClass;
	}

	public void setPojoClass(Class<P> pojoClass) {
		this.pojoClass = pojoClass;
	}

	public Class<D> getDaoClass() {
		return daoClass;
	}

	public void setDaoClass(Class<D> daoClass) {
		this.daoClass = daoClass;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
