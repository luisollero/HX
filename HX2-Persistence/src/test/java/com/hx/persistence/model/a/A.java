package com.hx.persistence.model.a;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.hx.persistence.model.b.B;

@Entity
public class A {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Basic
	public String name;

	@ManyToOne()
	public B b;
}
