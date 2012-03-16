package com.hx.persistence.ddg.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

public class WithLazies {

	private WithLazies() {
		super();
	}

	public static Collection<String> getProperties(
			SessionFactory sessionFactory, Class<?> clazz) {

		ClassMetadata metadata = sessionFactory.getClassMetadata(clazz);

		String[] propertyNames = metadata.getPropertyNames();
		Type[] propertyTypes = metadata.getPropertyTypes();
		List<String> collection = new LinkedList<String>();
		for (int i = 0; i < propertyTypes.length; i++) {
			if (propertyTypes[i].isCollectionType()) {
				collection.add(propertyNames[i]);
			}
		}

		return collection;
	}
}
