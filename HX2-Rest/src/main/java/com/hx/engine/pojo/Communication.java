package com.hx.engine.pojo;

/**
 * 
 * @author Luis Ollero
 *
 */
public class Communication  {

	private Integer communicationId;
	private Integer fromId;
	private String fromName;
	private String publishedIn;
	private String subject;
	private String body;
	private long time;
	private boolean favorited;
	private Integer karma;

	public Integer getCommunicationId() {
		return communicationId;
	}

	public void setCommunicationId(Integer communicationId) {
		this.communicationId = communicationId;
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

	public String getPublishedIn() {
		return publishedIn;
	}

	public void setPublishedIn(String publishedIn) {
		this.publishedIn = publishedIn;
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

	public Integer getKarma() {
		return karma;
	}

	public void setKarma(Integer karma) {
		this.karma = karma;
	}

}
