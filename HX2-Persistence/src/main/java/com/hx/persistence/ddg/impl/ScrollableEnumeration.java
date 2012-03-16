package com.hx.persistence.ddg.impl;

import java.util.Enumeration;

import org.hibernate.ScrollableResults;

public class ScrollableEnumeration implements Enumeration<Object> {

	private final ScrollableResults results;
	private boolean hasMoreElements;

	public ScrollableEnumeration(ScrollableResults results) {
		this.results = results;
		this.hasMoreElements = this.results.first();
	}

	@Override
	public boolean hasMoreElements() {
		return this.hasMoreElements;
	}

	@Override
	public Object nextElement() {
		Object object = this.results.get(0);
		this.hasMoreElements = this.results.next();
		return object;
	}
}
