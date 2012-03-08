package com.hx.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Turnos del juego
 * @author kineas
 *
 */
@Entity
@Table(name="hx_turn", catalog="hx")
public class Turn {

	@Id
	@Column(name="hx_turn_id")
	private String id;		// Id del turno. Compuesto por number y House.name.
	@Column(name="hx_turn_number")
	private Integer number;	// Número del turno.
	@Column(name="hx_turn_ended")
	private Long ended;		// La fecha (en millis) de cuándo fue terminado el turno.
	@Column(name="hx_turn_finish", nullable=false)
	private Boolean finish = false;	// Indica si el turno se ha terminado.
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_turn_house", referencedColumnName="hx_house_id")
	private House house;	// Casa propietaria del turno.

	//Getters and Setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Boolean getFinish() {
		return finish;
	}

	public void setFinish(Boolean finish) {
		this.finish = finish;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
	
	public Long getEnded() {
		return ended;
	}

	public void setEnded(Long ended) {
		this.ended = ended;
	}

	@Override
	public String toString() {
		return number + " || " + house.getName() ;
	}
}
