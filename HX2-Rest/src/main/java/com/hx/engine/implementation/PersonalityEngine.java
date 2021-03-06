package com.hx.engine.implementation;

import java.util.Collection;

import com.hx.engine.IPersonalityEngine;
import com.hx.model.dao.ethereal.IDAOPersonality;
import com.hx.model.dto.Personality;


/**
 * Clase de utilidad para facilitar el control de transacciones en el trabajo
 * con personajes.
 * 
 * @author Luis Ollero
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

	public com.hx.engine.pojo.Personality getById(Integer id) {
		Personality aux = daoPersonality.getWithLaziesById(id);
		if (aux != null) {
			return new com.hx.engine.pojo.Personality(aux);
		}
		return new com.hx.engine.pojo.Personality(-1);
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
