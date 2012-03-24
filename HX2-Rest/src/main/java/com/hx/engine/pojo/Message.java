package com.hx.engine.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Luis Ollero
 * 
 */
public class Message implements Pojo {

	private Integer messageId;
	private Integer fromId;
	private String fromName;
	private String subject;
	private String body;
	private Set<Personality> to = new HashSet<Personality>();
	private long time;
	private boolean favorited;

	public Message(Integer id) {
		this.messageId = id;
	}

	public Message(com.hx.model.dto.Message comm) {
		this.messageId = comm.getId();
		this.body = comm.getMessage();
		this.fromId = comm.getFrom().getId();
		this.fromName = comm.getFrom().getName();
		this.subject = comm.getSubject();
		this.time = comm.getSendingDate().getTime();
		for (com.hx.model.dto.Personality personality : comm.getTo()) {
			this.to.add(new Personality(personality));
		}
	}

	public Message() {
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public Set<Personality> getTo() {
		return to;
	}

	public void setTo(Set<Personality> to) {
		this.to = to;
	}

	public Object toDTO() {
		com.hx.model.dto.Message message = new com.hx.model.dto.Message();
		message.setId(this.messageId);
		message.setMessage(this.getBody());
		message.setSendingDate(new Date(this.time));
		message.setSubject(this.subject);
		return message;
	}

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", fromId=" + fromId
				+ ", fromName=" + fromName + ", subject=" + subject + ", body="
				+ body + ", time=" + time + ", favorited=" + favorited + "]";
	}

	
}
