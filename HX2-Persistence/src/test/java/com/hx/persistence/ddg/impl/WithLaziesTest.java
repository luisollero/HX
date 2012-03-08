package com.hx.persistence.ddg.impl;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Test;

import com.hx.persistence.CommonTezt;
import com.hx.persistence.ddg.impl.WithLazies;

public class WithLaziesTest extends CommonTezt {

	@Entity
	public static class Foo {

		private String foo;

		private Collection<Bar> bars;

		@Id
		public String getFoo() {
			return foo;
		}

		public void setFoo(String foo) {
			this.foo = foo;
		}

		@OneToMany(mappedBy = "foo", fetch = FetchType.LAZY)
		@LazyCollection(LazyCollectionOption.TRUE)
		public Collection<Bar> getBars() {
			return bars;
		}

		public void setBars(Collection<Bar> bars) {
			this.bars = bars;
		}
	}

	@Entity
	public static class Bar {

		private String bar;

		private Foo foo;

		@Id
		public String getBar() {
			return bar;
		}

		public void setBar(String bar) {
			this.bar = bar;
		}

		@ManyToOne
		public Foo getFoo() {
			return foo;
		}

		public void setFoo(Foo foo) {
			this.foo = foo;
		}
	}

	@Test
	public void testGetProperties() throws Exception {

		AnnotationConfiguration configuration = createAnnotationConfiguration("foo");
		configuration.addAnnotatedClass(Bar.class);
		configuration.addAnnotatedClass(Foo.class);

		SessionFactory sessionFactory = configuration.buildSessionFactory();

		Collection<String> collection1 = WithLazies.getProperties(
				sessionFactory, Foo.class);
		assertEquals(1, collection1.size());
		assertTrue(collection1.contains("bars"));

		Collection<String> collection2 = WithLazies.getProperties(
				sessionFactory, Bar.class);
		assertTrue(collection2.isEmpty());
	}
}
