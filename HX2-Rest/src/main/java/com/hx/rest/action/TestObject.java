package com.hx.rest.action;

public class TestObject {
	
	private String id;
	private String someString;
	private long someLong;
	private String anotherString;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSomeString() {
		return someString;
	}

	public void setSomeString(String someString) {
		this.someString = someString;
	}

	public long getSomeLong() {
		return someLong;
	}

	public void setSomeLong(long someLong) {
		this.someLong = someLong;
	}

	public String getAnotherString() {
		return anotherString;
	}

	public void setAnotherString(String anotherString) {
		this.anotherString = anotherString;
	}

	public void load(String id2) {
		this.anotherString = "whateverLongString";
		this.someLong = 123;
	}
}
