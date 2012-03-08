package com.hx.model;

import java.util.List;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SchemaCreator implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public SchemaCreator() {
		super();
	}

	public void create() {

		AnnotationConfiguration configuration = new AnnotationConfiguration();

		AnnotationSessionFactoryBean factoryBean = (AnnotationSessionFactoryBean) 
			applicationContext.getBean("&sessionFactory", AnnotationSessionFactoryBean.class);
		configuration.setProperties(factoryBean.getHibernateProperties());
		
		ComboPooledDataSource dataSource = (ComboPooledDataSource) factoryBean.getDataSource();
		configuration.setProperty("hibernate.connection.driver_class", dataSource.getDriverClass());
		configuration.setProperty("hibernate.connection.url", dataSource.getJdbcUrl());
		configuration.setProperty("hibernate.connection.username", dataSource.getUser());
		configuration.setProperty("hibernate.connection.password", dataSource.getPassword());

		
		
		@SuppressWarnings("unchecked")
		List<Class<?>> annotatedClasses = (List<Class<?>>) applicationContext
				.getBean("annotatedClasses", List.class);

		for (Class<?> clazz : annotatedClasses) {
			configuration.addAnnotatedClass(clazz);
		}

		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.create(false, true);
//		TODO generar un script SQL (generate schema)
//		Problemas con el ";" después de cada linea
//		configuration.generateSchemaCreationScript(new MySQL5InnoDBDialect());
//		SchemaExport schemaExport = new SchemaExport(configuration);
//		schemaExport.setOutputFile(
//			"/home/agarcia/workspace/model/src/main/databases/global/create_DIXIRED-schema.sql");
//		schemaExport.setFormat(true);
//		schemaExport.create(true, true);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
