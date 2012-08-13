package com.hx.model.dto;

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
 * Game turns
 * @author Luis Ollero
 *
 */
@Entity
@Table(name="hx_turn", catalog="hx")
public class Turn {

	@Id
	@Column(name = "turn_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="turn_number")
	private Integer number;	// turn number
	@Column(name="turn_ended_date")
	private Long ended;		// Date (in millis) of when the turn was finished
	@Column(name="turn_finish", nullable=false)
	private Boolean finish = false;	// Indicates whenever a turn is finished
	
	@ManyToOne
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="hx_turn_house", referencedColumnName="hx_house_id")
	private House house;	// House that determines this turn

	//Getters and Setters
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
