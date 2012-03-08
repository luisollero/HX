package com.hx.engine.implementation;

import java.util.Collection;

import com.hx.engine.IPersonalityEngine;
import com.hx.model.dao.ethereal.IDAOPersonality;
import com.hx.model.dto.Personality;


/**
 * Clase de utilidad para facilitar el control de transacciones en el trabajo
 * con personajes.
 * 
 * @author kineas
 * 
 */
public class PersonalityEngine implements IPersonalityEngine {
	
	private IDAOPersonality daoPersonality = null;
	
	public IDAOPersonality getDaoPersonality() {
		return daoPersonality;
	}

	public void setDaoPersonality(IDAOPersonality daoPersonality) {
		this.daoPersonality = daoPersonality;
	}

	public Personality getById(String id) {
		return daoPersonality.getById(id);
	}
	
	public void saveOrUpdate(final Personality personality) {
		daoPersonality.saveOrUpdate(personality);
	}
	
	public void delete(Personality personality) {
		daoPersonality.delete(personality);
	}
	
	public Collection<Personality> findAll() {
		return daoPersonality.find();
	}
	
}
