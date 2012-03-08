package com.hx.engine.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hx.engine.IHouseEngine;
import com.hx.engine.IPersonalityEngine;
import com.hx.engine.IRegimentEngine;
import com.hx.engine.ISectorEngine;
import com.hx.engine.IUserEngine;

public class TestBase {

	private ClassPathXmlApplicationContext context;
	
	@Before
	public void setUp() {
		this.configureLog4j();
		context = new ClassPathXmlApplicationContext("context.xml", this.getClass());
	}
	
	protected void configureLog4j() {
		BasicConfigurator.resetConfiguration();
		ConsoleAppender consoleAppender = new ConsoleAppender(
				new SimpleLayout());
		Logger.getRootLogger().addAppender(consoleAppender);
		Logger.getLogger("org.hibernate").setLevel(Level.WARN);
	}

	public IUserEngine getUserEngine() {
		return (IUserEngine) context.getBean("userEngine", IUserEngine.class);
	}
	
	public ISectorEngine getSectorEngine() {
		return (ISectorEngine) context.getBean("sectorEngine", ISectorEngine.class);
	}
	
	public IHouseEngine getHouseEngine() {
		return (IHouseEngine) context.getBean("houseEngine", IHouseEngine.class);
	}
	
	public IPersonalityEngine getPersonalityEngine() {
		return (IPersonalityEngine) context.getBean("personalityEngine", IPersonalityEngine.class);
	}

	public IRegimentEngine getRegimentEngine() {
		return (IRegimentEngine) context.getBean("regimentEngine", IRegimentEngine.class);
	}

}
