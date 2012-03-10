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
 * Messages sent as "declarations" to the rest of the players. Depending on the
 * level of a player this will be published in the correspondent section
 * 
 * @author Luis Ollero
 * 
 */
@Entity
@Table(name = "hx_communications", catalog = "hx")
public class Communication {

	@Id
	@Column(name = "hx_communications_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "hx_communications_from", referencedColumnName = "hx_user_id")
	private User from; // User that sends the communication

	@Column(name = "hx_communications_subject", length = 25)
	private String subject;

	@Column(name = "hx_communications_message", length = 5000)
	private String message; // Body

	@Column(name = "hx_communications_sending_date")
	private Date sendingDate;

	@Column(name = "hx_communications_level")
	private int level; // Level of the communication

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", subject=" + subject + "]";
	}

}
