package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.hx.engine.IUserEngine;
import com.hx.model.dao.ethereal.IDAOUser;
import com.hx.model.dto.Personality;
import com.hx.model.dto.User;

/**
 * Clase de utilidad para facilitar el control de transacciones en el trabajo
 * con Usuarios.
 * 
 * @author Luis Ollero
 * 
 */
public class UserEngine implements IUserEngine {

	private IDAOUser daoUser = null;
	
	public IDAOUser getDaoUser() {
		return daoUser;
	}

	public void setDaoUser(IDAOUser daoUser) {
		this.daoUser = daoUser;
	}

	public void saveOrUpdate(final User user) {
		daoUser.saveOrUpdate(user);									
	}
	
	public User getById(String id) {
		return daoUser.getById(id);
	}
	
	public void delete(User user) {
		daoUser.delete(user);
	}
	
	public Collection<User> findAll() {
		return daoUser.find();
	}
	
	public ArrayList<User> findByPersonality(Personality personality) {
		return daoUser.findByPersonality(personality);
	}
	
//	public ArrayList<User> findByRole(Role role) {
//		return daoUser.(role);
//	}
}
