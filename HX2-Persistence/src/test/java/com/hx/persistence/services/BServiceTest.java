package com.hx.persistence.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.hx.persistence.CommonTezt;
import com.hx.persistence.model.a.A;
import com.hx.persistence.model.b.B;
import com.hx.persistence.services.b.BService;
import com.hx.persistence.tx.TransactionManager;

public class BServiceTest extends CommonTezt {

	private ClassPathXmlApplicationContext context;

	@Before
	public void setUp() {

		this.configureLog4j();

		context = new ClassPathXmlApplicationContext("Services-context.xml", this.getClass());

		AnnotationConfiguration configuration = this.createAnnotationConfiguration(getUrl());
		configuration.addAnnotatedClass(A.class);
		configuration.addAnnotatedClass(B.class);

		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.create(false, true);
	}

	@After
	public void tearDown() throws Exception {
		if (context != null) {
			getTransactionManager().close();
			shutdown(getUrl());
		}
	}

	private String getUrl() {
		return getBean("dataSource", DriverManagerDataSource.class).getUrl();
	}

	private TransactionManager getTransactionManager() {
		return getBean("transactionManager", TransactionManager.class);
	}

	private BService getBService() {
		return getBean("bService", BService.class);
	}

	private <T> T getBean(String name, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T t = (T) context.getBean(name, clazz);
		return t;
	}

	@Test
	public void testBService() throws Exception {

		B b = new B();
		b.name = "foo";

		getBService().save(b);

		List<B> all = getBService().find();
		assertEquals(1, all.size());

		B b2 = all.get(0);
		long id = b2.id;
		assertEquals("foo", b2.name);
		assertTrue(b2.aSet.isEmpty());

		B b3 = getBService().getById(id);
		assertEquals(id, b3.id);
		assertEquals("foo", b3.name);
		assertTrue(b3.aSet.isEmpty());

		List<B> all2 = getBService().findByNameLessThan("a");
		assertEquals(0, all2.size());

		List<B> all3 = getBService().findByNameLessThan("g");
		assertEquals(1, all3.size());
		B b4 = all3.get(0);

		assertEquals(id, b4.id);
		assertEquals("foo", b4.name);
		assertTrue(b4.aSet.isEmpty());

		List<B> scroll = getBService().scroll();
		assertEquals(1, scroll.size());

		B b5 = all.get(0);
		assertEquals(id, b5.id);
		assertEquals("foo", b5.name);
		assertTrue(b5.aSet.isEmpty());
	}
}
