package com.hx.model;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CreateSchema {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				CreateSchema.class.getSimpleName() + ".xml", CreateSchema.class);

		SchemaCreator schemaCreator = (SchemaCreator) context
				.getBean("schemaCreator");
		schemaCreator.create();
	}
}
