package com.hx.model;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hx.model.dao.ethereal.IDAOAction;
import com.hx.model.dao.ethereal.IDAOCommunication;
import com.hx.model.dao.ethereal.IDAOHouse;
import com.hx.model.dao.ethereal.IDAOMessage;
import com.hx.model.dao.ethereal.IDAOPersonality;
import com.hx.model.dao.ethereal.IDAORegiment;
import com.hx.model.dao.ethereal.IDAOSector;
import com.hx.model.dao.ethereal.IDAOSuplyLine;
import com.hx.model.dao.ethereal.IDAOTurn;
import com.hx.model.dao.ethereal.IDAOUser;
import com.hx.persistence.tx.TransactionManager;

public class PopulationConfigurator {
	private ClassPathXmlApplicationContext context;

	public void setUp() {

		this.configureLog4j();

		context = new ClassPathXmlApplicationContext("CreateSchema.xml",
				this.getClass());
		
		getTransactionManager().openReadWriteSession();

	}

	public void tearDown() {
		if (context != null) {
			getTransactionManager().commitSession();
			getTransactionManager().close();
		}
	}

	private TransactionManager getTransactionManager() {
		return getBean("transactionManager", TransactionManager.class);
	}

	private <T> T getBean(String name, Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T t = (T) context.getBean(name, clazz);
		return t;
	}

	protected void configureLog4j() {
		BasicConfigurator.resetConfiguration();
		ConsoleAppender consoleAppender = new ConsoleAppender(
				new SimpleLayout());
		Logger.getRootLogger().addAppender(consoleAppender);
		Logger.getLogger("org.hibernate").setLevel(Level.WARN);
	}

	// Getters
	protected IDAOUser getDaoUser() {
		return getBean("daoUser", IDAOUser.class);
	}

	protected IDAOSector getDaoSector() {
		return getBean("daoSector", IDAOSector.class);
	}

	protected IDAOHouse getDaoHouse() {
		return getBean("daoHouse", IDAOHouse.class);
	}

	protected IDAOCommunication getDaoCommunication() {
		return getBean("daoCommunication", IDAOCommunication.class);
	}

	protected IDAOMessage getDaoMessage() {
		return getBean("daoMessage", IDAOMessage.class);
	}

	protected IDAOPersonality getDaoPersonality() {
		return getBean("daoPersonality", IDAOPersonality.class);
	}

	protected IDAOAction getDaoAction() {
		return getBean("daoAction", IDAOAction.class);
	}

	protected IDAORegiment getDaoRegiment() {
		return getBean("daoRegiment", IDAORegiment.class);
	}

	protected IDAOSuplyLine getDaoSupplyLine() {
		return getBean("daoSupplyLine", IDAOSuplyLine.class);
	}

	protected IDAOTurn getDaoTurn() {
		return getBean("daoTurn", IDAOTurn.class);
	}
}
