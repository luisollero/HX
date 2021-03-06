package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.ICommunicationEngine;
import com.hx.engine.pojo.Communication;
import com.hx.model.dto.Personality;
import com.opensymphony.xwork2.ModelDriven;

public class CommunicationController implements ModelDriven<Object> {

	private String token;
	private String id;
	private String subject;
	private String published;
	private String body;
	private String fromId;
	private Communication communication;
	private Collection<Communication> list;
	
	private ICommunicationEngine communicationEngine;

	// GET /communication/1
	public HttpHeaders show() {
		communication = communicationEngine.getById(Integer.valueOf(id));
		return new DefaultHttpHeaders("show");
	}

	public HttpHeaders index() {
		list = communicationEngine.findAll();
		return new DefaultHttpHeaders("index");
	}
	
	// POST /communication
	public HttpHeaders create() throws Exception {
		communication = new Communication();
		communication.setSubject(subject);
		communication.setBody(body);
		communication.setPublishedIn(published);
		
		com.hx.model.dto.Communication comm = (com.hx.model.dto.Communication) communication.toDTO();
		comm.setFrom((Personality) new com.hx.engine.pojo.Personality(Integer.valueOf(fromId)).toDTO());
		communicationEngine.saveOrUpdate(comm);

		return new DefaultHttpHeaders("success");
	}

	// PUT /communication/1
	public String update() throws Exception {
		communicationEngine.saveOrUpdate((com.hx.model.dto.Communication)communication.toDTO());
		return "success";
	}

	public Object getModel() {
		return (list != null ? list : communication);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getFromId() {
		return fromId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	@Autowired
	public void setCommunicationEngine(ICommunicationEngine communicationEngine) {
		this.communicationEngine = communicationEngine;
	}
	
}
