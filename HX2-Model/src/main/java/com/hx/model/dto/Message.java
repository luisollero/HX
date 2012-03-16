package com.hx.model.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Messages sent among players
 * @author luisollero
 *
 */
@Entity
@Table(name="hx_messages", catalog="hx")
public class Message {
	
	@Id
	@Column(name="hx_message_id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_messages_from", referencedColumnName="hx_user_id")
	private User from;
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_messages_to", referencedColumnName="hx_user_id")
	private User to;
	
	@Column(name="hx_messages_subject",length=25)
	private String subject;
	
	@Column(name="hx_messages_message", length=500)
	private String message;
	
	@Column(name="hx_messages_sending_date")
	private Date sendingDate;

	//GETTER'S AND SETTER's
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSendingDate() {
		return sendingDate;
	}
	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}
	
	@Override
	public String toString() {
		return "Message [from=" + from + ", to=" + to + ", subject=" + subject
				+ "]";
	}
	
}
