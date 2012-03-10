package com.hx.rest.action;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.ICommunicationEngine;
import com.hx.engine.pojo.Communication;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAwareSupport;

public class CommunicationController extends ValidationAwareSupport implements
		ModelDriven<Communication>, Validateable {

	private static final long serialVersionUID = 6134808879242484608L;

	private String id;
	private Communication communication = new Communication();
	
	private ICommunicationEngine communicationEngine;

	// GET /test/1
	public HttpHeaders show() {
//		communication.setName(houseEngine.getById(id).getName());
		communication.setCommunicationId(1);
		communication.setFromId(1);
		communication.setFromName("from name");
		communication.setSubject("subject");
		communication.setPublishedIn("Tallin mensk");
		communication.setKarma(2);
		communication.setBody("Body");
		communication.setFavorited(true);
		
		return new DefaultHttpHeaders("show");
	}

	public void validate() {
		communication.setBody("validate");
	}

	public Communication getModel() {
		return communication;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Autowired
	public void setCommunicationEngine(ICommunicationEngine communicationEngine) {
		this.communicationEngine = communicationEngine;
	}
	
	

}
