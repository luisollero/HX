package com.hx.model.test;

import static junit.framework.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hx.model.dao.ethereal.IDAOUser;
import com.hx.persistence.tx.TransactionManager;

public class HXTestContext {

	@Test
	public void testSumzin() throws Exception {
		
		this.getTransactionManager().runInReadWriteTransaction(new Callable<Object>() {

			@Override
			public Object call() throws Exception {

				try {
					assertTrue(getDaoUser().find().isEmpty());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});
		

	}
	
	
	protected void configureLog4j() {
		BasicConfigurator.resetConfiguration();
		ConsoleAppender consoleAppender = new ConsoleAppender(
				new SimpleLayout());
		Logger.getRootLogger().addAppender(consoleAppender);
		Logger.getLogger("org.hibernate").setLevel(Level.WARN);
	}

	private ClassPathXmlApplicationContext context;

	@Before
	public void setUp() {

		this.configureLog4j();

		context = new ClassPathXmlApplicationContext("context.xml", this.getClass());
		
	}

	@After
	public void tearDown() throws Exception {
		if (context != null) {
			getTransactionManager().close();
		}
	}

	private IDAOUser getDaoUser() {
		return getBean("daoUser", IDAOUser.class);
	}

	private TransactionManager getTransactionManager() {
		return getBean("transactionManager", TransactionManager.class);
	}

	private <T> T getBean(String name, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T t = (T) context.getBean(name, clazz);
		return t;
	}

	
}
