package com.hx.rest.action;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;

public class TestController extends ValidationAwareSupport implements
		ModelDriven<TestObject>, Validateable {

	private static final long serialVersionUID = 8283023925756299661L;

	private TestObject testObject = new TestObject();

	private String id;

	public TestController() {
		super();
	}

	public String test() {
		testObject.load(id);
		return "test";
	}

	// GET /test/1
	public HttpHeaders show() {
		return new DefaultHttpHeaders("show");
	}

	// GET /test
	public HttpHeaders index() {
		return new DefaultHttpHeaders("index");
	}

	// POST /test
	public HttpHeaders create() throws Exception {
		// TODO: creation action here

		return new DefaultHttpHeaders("success");
	}

	// PUT /test/1
	public String update() throws Exception {
		// TODO: update action here
		return "success";
	}

	// DELETE /test/1
	public String destroy() {
		// TODO: delete action here
		return "success";
	}

	public void validate() {
		// TODO add validation here

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TestObject getModel() {
		return testObject;
	}

}
