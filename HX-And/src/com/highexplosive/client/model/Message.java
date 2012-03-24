package com.highexplosive.client.model;

import java.util.ArrayList;


/**
 * POJO for messages in the game
 * 
 * @author Luis Ollero
 * 
 */
public class Message {

	public static final String MESSAGE_ID = "message_id";
	private int messageId;
	private int fromId;

	private String fromName;
	private ArrayList<Character> to;
	private String subject;
	private String bodyResume;
	private String body;

	private boolean favorited;
	private long time;

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
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
		return "Message [messageId=" + messageId + ", fromId=" + fromId
				+ ", fromName=" + fromName + ", to=" + to + ", subject="
				+ subject + ", bodyResume=" + bodyResume + ", body=" + body
				+ ", favorited=" + favorited + ", time=" + time + "]";
	}


	
}
