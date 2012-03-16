package com.hx.persistence.model.b;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.hx.persistence.model.a.A;

@Entity
public class B {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Basic
	public String name;

	@OneToMany(mappedBy = "b")
	@LazyCollection(LazyCollectionOption.FALSE)
	public Set<A> aSet;
}
