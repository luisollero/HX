package com.hx.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hx_action", catalog="hx")
public class Action {

	@Id
	@Column(name="hx_message_id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	/**
	 * ID de la casa
	 */
	@Column(name="hx_action_where",nullable=false)
	private String where;
	
	/**
	 * Nivel del personaje
	 */
	@Column(name="hx_action_who",nullable=false)
	private String who;	
	
	/**
	 * Nombre de la columna que se pretende cambiar
	 */
	@Column(name="hx_action_what",nullable=false)
	private String what;
	
	/**
	 * Valor que se le quiere otorgar
	 */
	@Column(name="hx_action_value",nullable=false)
	private String value;

	//GETTER'S SETTER'S
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Action [what=" + what + ", where=" + where + ", who=" + who
				+ "]";
	}

}
