package com.highexplosive.client.model;


/**
 * POJO for declarations in the game
 * 
 * @author Luis Ollero
 * 
 */
public class Declaration {

	public static final String DECLARATION_ID = "declaration_id";
	private int declarationId;
	private int fromId;

	private String fromName;
	private String subject;
	private String bodyResume;
	private String body;
	private String published;

	private boolean favorited;
	private int karma;
	private long time;

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}


	public int getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(int declarationId) {
		this.declarationId = declarationId;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
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

	public String getBodyResume() {
		return bodyResume;
	}

	public void setBodyResume(String bodyResume) {
		this.bodyResume = bodyResume;
	}

	public int getKarma() {
		return karma;
	}

	public void setKarma(int karma) {
		this.karma = karma;
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

	@Override
	public String toString() {
		return "Declaration [declarationId=" + declarationId + ", fromId="
				+ fromId + ", fromName=" + fromName + ", subject=" + subject
				+ ", bodyResume=" + bodyResume + ", body=" + body
				+ ", published=" + published + ", favorited=" + favorited
				+ ", karma=" + karma + ", time=" + time + "]";
	}

	
}
