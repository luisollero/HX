package com.hx.persistence.usage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;

import com.hx.persistence.CommonTezt;

public abstract class UsageTest extends CommonTezt {

	protected static interface Transaction<T> {
		T run(Session session);
	}

	private SessionFactory sessionFactory;

	@Before
	public void setUp() {

		this.configureLog4j();

		AnnotationConfiguration configuration = this.createAnnotationConfiguration(this.getUrl());
		Class<?>[] classes = getClasses();
		for (Class<?> clazz : classes) {
			configuration.addAnnotatedClass(clazz);
		}

		SchemaExport schemaExport = new SchemaExport(configuration);
		boolean showScript = false;
		schemaExport.create(showScript, true);

		this.sessionFactory = configuration.buildSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		shutdown(this.getUrl());
	}

	private String getUrl() {
		return "jdbc:hsqldb:mem:" + this.getClass().getName();
	}

	protected Class<?>[] getClasses() {
		return new Class<?>[] {};
	}

	protected <T> T execute(Transaction<T> transaction) {

		Session session = this.sessionFactory.openSession();
		try {

			org.hibernate.Transaction tx = session.beginTransaction();
			try {

				T t = transaction.run(session);

				tx.commit();
				tx = null;
				return t;

			} finally {
				if (tx != null) {
					tx.rollback();
				}
			}

		} finally {
			session.close();
		}
	}
}
