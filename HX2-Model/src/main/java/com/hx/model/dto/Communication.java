package com.hx.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@JoinColumn(name = "hx_communications_from", referencedColumnName = "hx_personality_id")
	private Personality from; // User that sends the communication

	@Column(name = "hx_communications_subject", length = 40)
	private String subject;

	@Column(name = "hx_communications_message", length = 5000)
	private String message; // Body

	@Column(name = "hx_communications_published", length = 40)
	private String publishedIn;

	@Column(name = "hx_communications_sending_date")
	private Date sendingDate;

	@Column(name = "hx_communications_scope")
	@Enumerated(EnumType.STRING)
	private Scope scope;

	@Column(name = "hx_communications_karma")
	private int karma; // Upvotes of the communication

	@ManyToMany(targetEntity = com.hx.model.dto.Personality.class, cascade = {
		CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinTable(schema = "hx", name = "hx_comm_upvoted", joinColumns = @JoinColumn(name = "communication_id"),  inverseJoinColumns = @JoinColumn(name = "personality_id"))
	private Set<Personality> upvotedSet = new HashSet<Personality>();

	public Set<Personality> getUpvotedSet() {
		return this.upvotedSet;
	}

	public void setUpvotedSet(Set<Personality> upvotedSet) {
		this.upvotedSet = upvotedSet;
	}

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

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public String getPublishedIn() {
		return publishedIn;
	}

	public void setPublishedIn(String publishedIn) {
		this.publishedIn = publishedIn;
	}

	public int getKarma() {
		return karma;
	}

	public void setKarma(int karma) {
		this.karma = karma;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", subject=" + subject + "]";
	}

}
