package com.hx.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Messages sent among players
 * 
 * @author Luis Ollero
 * 
 */
@Entity
@Table(name = "hx_messages", catalog = "hx")
public class Message {

	@Id
	@Column(name = "hx_message_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "hx_messages_from", referencedColumnName = "hx_personality_id")
	private Personality from;

	@ManyToMany(targetEntity = com.hx.model.dto.Personality.class, cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(schema = "hx", name = "hx_message_personality", joinColumns = @JoinColumn(name = "message_id"), inverseJoinColumns = @JoinColumn(name = "personality_id"))
	private Set<Personality> to = new HashSet<Personality>();

	@Column(name = "hx_messages_subject", length = 25)
	private String subject;

	@Column(name = "hx_messages_message")
	private String message;

	@Column(name = "hx_messages_sending_date")
	private Date sendingDate;

	// GETTER'S AND SETTER's
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Personality getFrom() {
		return from;
	}

	public void setFrom(Personality from) {
		this.from = from;
	}

	public Set<Personality> getTo() {
		return to;
	}

	public void setTo(Set<Personality> to) {
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
		return "Message [id=" + id + ", from=" + from + ", subject=" + subject
				+ ", message=" + message + ", sendingDate=" + sendingDate + "]";
	}

}
