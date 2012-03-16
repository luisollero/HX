package com.hx.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbcDriver;

public class CommonTezt {

	public CommonTezt() {
		super();
	}

	protected void configureLog4j() {
		BasicConfigurator.resetConfiguration();
		ConsoleAppender consoleAppender = new ConsoleAppender(
				new SimpleLayout());
		Logger.getRootLogger().addAppender(consoleAppender);
		Logger.getLogger("org.hibernate").setLevel(Level.WARN);
	}

	protected AnnotationConfiguration createAnnotationConfiguration(String url) {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		configuration.setProperty("hibernate.connection.url", url);
		configuration.setProperty("hibernate.connection.username", "sa");
		configuration.setProperty("hibernate.connection.password", "");
		configuration.setProperty("hibernate.connection.driver_class",
				jdbcDriver.class.getName());
		configuration.setProperty("hibernate.dialect", HSQLDialect.class
				.getName());
		return configuration;
	}

	protected void shutdown(String url) throws Exception {

		Class.forName(jdbcDriver.class.getName());
		Connection connection = DriverManager.getConnection(url, "sa", "");
		try {

			Statement statement = connection.createStatement();
			try {
				statement.execute("shutdown");
			} finally {
				statement.close();
			}

		} finally {
			connection.close();
		}

	}
}
