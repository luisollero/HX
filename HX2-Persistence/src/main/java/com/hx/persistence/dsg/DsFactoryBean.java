package com.hx.persistence.dsg;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.hx.persistence.dsg.impl.DsClassGenerator;

public class DsFactoryBean<P, S> implements FactoryBean, InitializingBean {

	private Object dao;
	private Class<S> dsClass;
	private DsClassConfigurer dsClassConfigurer;
	private Class<S> generateDsClass;

	public DsFactoryBean() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		DsClassConfigurer currentDsClassConfigurer;
		if (this.dsClassConfigurer != null) {
			currentDsClassConfigurer = this.dsClassConfigurer;
		} else {
			currentDsClassConfigurer = new DefaultDsClassConfigurer();
		}

		generateDsClass = DsClassGenerator.generateDsClass(dao, dsClass,
				currentDsClassConfigurer);
	}

	@Override
	public Object getObject() throws Exception {

		return generateDsClass.newInstance();
	}

	@Override
	public Class<S> getObjectType() {
		return generateDsClass;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public Object getDao() {
		return this.dao;
	}

	public void setDao(Object dao) {
		this.dao = dao;
	}

	public Class<S> getDsClass() {
		return this.dsClass;
	}

	public void setDsClass(Class<S> dsClass) {
		this.dsClass = dsClass;
	}

	public DsClassConfigurer getDsClassConfigurer() {
		return this.dsClassConfigurer;
	}

	public void setDsClassConfigurer(DsClassConfigurer dsClassConfigurer) {
		this.dsClassConfigurer = dsClassConfigurer;
	}
}
