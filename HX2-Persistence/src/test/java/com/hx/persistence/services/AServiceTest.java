package com.hx.persistence.services;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import java.util.Iterator;
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
import com.hx.persistence.services.a.AService;
import com.hx.persistence.services.b.BService;
import com.hx.persistence.tx.TransactionManager;

public class AServiceTest extends CommonTezt {

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

	private AService getAService() {
		return getBean("aService", AService.class);
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
	public void testAService() throws Exception {

		B b = new B();
		b.name = "foo";
		getBService().save(b);

		A a = new A();
		a.name = "bar";
		a.b = b;
		getAService().save(a);
		System.out.println(a.id);

		List<A> all = getAService().find();
		assertEquals(1, all.size());

		A a2 = all.get(0);
		long aId = a2.id;
		assertEquals("bar", a2.name);
		assertNotNull(a2.b);
		long bId = a2.b.id;
		assertEquals("foo", a2.b.name);
		assertEquals(1, a2.b.aSet.size());
		assertSame(a2, a2.b.aSet.iterator().next());

		A a3 = getAService().getById(aId);
		assertEquals(aId, a3.id);
		assertEquals("bar", a3.name);
		assertNotNull(a3.b);
		assertEquals(bId, a3.b.id);
		assertEquals("foo", a3.b.name);
		assertEquals(1, a3.b.aSet.size());
		assertSame(a3, a3.b.aSet.iterator().next());

		B b2 = getBService().getById(bId);
		assertEquals(bId, b2.id);
		assertEquals("foo", b2.name);
		assertEquals(1, b2.aSet.size());
		A a4 = b2.aSet.iterator().next();
		assertEquals(aId, a4.id);
		assertEquals("bar", a4.name);
		assertSame(b2, a4.b);

		List<A> scroll = getAService().scroll();
		assertEquals(1, scroll.size());

		A a5 = scroll.get(0);
		assertEquals(aId, a5.id);
		assertEquals("bar", a5.name);
		assertNotNull(a5.b);
		assertEquals(bId, a5.b.id);
		assertEquals("foo", a5.b.name);
		assertEquals(1, a5.b.aSet.size());
		assertSame(a5, a5.b.aSet.iterator().next());

		A a6 = new A();
		a6.name = "bar2";
		a6.b = b;
		getAService().save(a6);

		assertEquals(2, getAService().find().size());
		assertEquals(2, getBService().find().get(0).aSet.size());

		Iterator<A> it1 = getAService().findOrderAscByNameLimited(0, 1).iterator();
		assertTrue(it1.hasNext());
		assertEquals("bar", it1.next().name);
		assertFalse(it1.hasNext());

		Iterator<A> it2 = getAService().findOrderAscByNameLimited(1, 1).iterator();
		assertTrue(it2.hasNext());
		assertEquals("bar2", it2.next().name);
		assertFalse(it2.hasNext());
	}
}
