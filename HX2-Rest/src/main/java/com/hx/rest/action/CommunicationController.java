package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.ICommunicationEngine;
import com.hx.engine.pojo.Communication;
import com.opensymphony.xwork2.ModelDriven;

public class CommunicationController implements ModelDriven<Object> {

	private String id;
	private Communication communication = new Communication();
	private Collection<Communication> list;
	
	private ICommunicationEngine communicationEngine;

	// GET /test/1
	public HttpHeaders show() {
		// communication.setName(houseEngine.getById(id).getName());
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

	public HttpHeaders index() {
		list = communicationEngine.findAll();
		return new DefaultHttpHeaders("index");
	}
	
	// POST /communication
	public HttpHeaders create() throws Exception {

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

	@Autowired
	public void setCommunicationEngine(ICommunicationEngine communicationEngine) {
		this.communicationEngine = communicationEngine;
	}

}
