package com.hx.persistence.ddg;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.hx.persistence.CommonTezt;
import com.hx.persistence.tx.TransactionManager;

public class DDGTest extends CommonTezt {

	private ClassPathXmlApplicationContext context;

	@Before
	public void setUp() {

		this.configureLog4j();

		context = new ClassPathXmlApplicationContext("DDGTest-context.xml", this.getClass());

		AnnotationConfiguration configuration = this.createAnnotationConfiguration(getUrl());
		configuration.addAnnotatedClass(User.class);

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

	private UserDao getUserDao() {
		return getBean("userDao", UserDao.class);
	}

	private TransactionManager getTransactionManager() {
		return getBean("transactionManager", TransactionManager.class);
	}

	private <T> T getBean(String name, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T t = (T) context.getBean(name, clazz);
		return t;
	}

	@Test
	public void testFind() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());
				return null;
			}
		});
	}

	@Test
	public void testSave() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());
				User user2 = all.get(0);

				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);
				return null;
			}
		});
	}

	@Test
	public void testUpdate() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);
				user1.name = "bar";
				userDao.update(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());
				User user2 = all.get(0);

				assertEquals("bar", user2.name);
				assertEquals(user1.birthday, user2.birthday);
				return null;
			}
		});
	}

	@Test
	public void testDelete() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				userDao.delete(user1);
				assertTrue(userDao.find().isEmpty());
				return null;
			}
		});
	}

	@Test
	public void testGetById() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				User user2 = userDao.getById(user1.id);
				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);

				User user3 = userDao.getById(user1.id + 1);
				assertNull(user3);
				return null;
			}
		});
	}

	@Test
	public void testNotNullGetById() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				User user2 = userDao.notNullGetById(user1.id);
				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);

				Exception exception;
				try {
					userDao.notNullGetById(user1.id + 1);
					exception = null;
				} catch (Exception e) {
					exception = e;
				}
				assertNotNull(exception);
				assertEquals("java.lang.RuntimeException: User not found when notNullGetById()", exception == null ? null
						: exception.toString());
				return null;
			}
		});
	}

	@Test
	public void testGetByName() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				User user2 = userDao.getByName("foo");
				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);

				User user3 = userDao.getByName("bar");
				assertNull(user3);
				return null;
			}
		});
	}

	@Test
	public void testNotNullGetByName() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				User user2 = userDao.notNullGetByName("foo");
				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);

				Exception exception;
				try {
					userDao.notNullGetByName("bar");
					exception = null;
				} catch (Exception e) {
					exception = e;
				}
				assertNotNull(exception);
				assertEquals("java.lang.RuntimeException: User not found when notNullGetByName()", exception == null ? null
						: exception.toString());
				return null;
			}
		});
	}

	@Test
	public void testGet() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				assertNull(userDao.get());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				User user2 = userDao.get();
				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);
				return null;
			}
		});
	}

	@Test
	public void testNotNullGet() throws Exception {

		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				UserDao userDao = DDGTest.this.getUserDao();
				assertTrue(userDao.find().isEmpty());

				Exception exception;
				try {
					userDao.notNullGet();
					exception = null;
				} catch (Exception e) {
					exception = e;
				}
				assertNotNull(exception);
				assertEquals("java.lang.RuntimeException: User not found when notNullGet()", exception == null ? null : exception
						.toString());

				User user1 = new User();
				user1.name = "foo";
				user1.birthday = new Date();
				userDao.save(user1);

				List<User> all = userDao.find();
				assertEquals(1, all.size());

				User user2 = userDao.notNullGet();
				assertEquals("foo", user2.name);
				assertEquals(user1.birthday, user2.birthday);
				return null;
			}
		});
	}
}
