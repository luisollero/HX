package com.hx.rest.action;

import java.util.Collection;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.hx.engine.IMessageEngine;
import com.hx.engine.pojo.Message;
import com.hx.model.dto.Personality;
import com.opensymphony.xwork2.ModelDriven;

public class MessageController implements ModelDriven<Object> {

	private String id;
	private String token;
	private String subject;
	private String body;
	private String fromId;
	private Message message;
	private Collection<Message> list;
	
	private IMessageEngine messageEngine;

	// GET /test/1
	public HttpHeaders show() {
		message = messageEngine.getById(Integer.valueOf(id));
		return new DefaultHttpHeaders("show");
	}

	public HttpHeaders index() {
		list = messageEngine.findAll();
		return new DefaultHttpHeaders("index");
	}
	
	// POST /message
	public HttpHeaders create() throws Exception {
		message = new Message();
		message.setSubject(subject);
		message.setBody(body);
		
		com.hx.model.dto.Message comm = (com.hx.model.dto.Message) message.toDTO();
		comm.setFrom((Personality) new com.hx.engine.pojo.Personality(Integer.valueOf(fromId)).toDTO());
		messageEngine.saveOrUpdate(comm);

		return new DefaultHttpHeaders("success");
	}

	// PUT /message/1
	public String update() throws Exception {
		messageEngine.saveOrUpdate((com.hx.model.dto.Message)message.toDTO());
		return "success";
	}

	public Object getModel() {
		return (list != null ? list : message);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	@Autowired
	public void setMessageEngine(IMessageEngine messageEngine) {
		this.messageEngine = messageEngine;
	}
	
}
