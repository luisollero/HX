package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

import com.hx.engine.IUserEngine;
import com.hx.model.dao.ethereal.IDAOUser;
import com.hx.model.dto.Personality;
import com.hx.model.dto.User;

/**
 * Engine for working with users against the database
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
	
	public com.hx.engine.pojo.User getById(Integer id) {
		User aux = daoUser.getWithLaziesById(id);
		if (aux != null) {
			return new com.hx.engine.pojo.User(aux);
		}
		return new com.hx.engine.pojo.User(-1);
	}
	
	public void delete(User user) {
		daoUser.delete(user);
	}
	
	public Collection<com.hx.engine.pojo.User> findAll() {
		Collection<com.hx.engine.pojo.User> returnList = new ArrayList<com.hx.engine.pojo.User>();
		Collection<User> list = daoUser.find();
		for (User user : list) {
			returnList.add(new com.hx.engine.pojo.User(user));
		}
		return returnList;
	}
	
	public ArrayList<User> findByPersonality(Personality personality) {
		return daoUser.findByPersonality(personality);
	}

	public User checkUser(String userName, String userPass) {
		User user = daoUser.findByMailAndPass(userName, userPass).get(0);
		return user;
	}
	
//	public ArrayList<User> findByRole(Role role) {
//		return daoUser.(role);
//	}
}
