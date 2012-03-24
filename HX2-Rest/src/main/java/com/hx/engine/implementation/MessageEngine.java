package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.hx.engine.IMessageEngine;
import com.hx.model.dao.ethereal.IDAOMessage;
import com.hx.model.dto.Message;

public class MessageEngine implements IMessageEngine {

	private IDAOMessage daoMessage;
	
	public Collection<com.hx.engine.pojo.Message> findAll() {
		Collection<Message> list = daoMessage.findWithLazies();
		ArrayList<com.hx.engine.pojo.Message> returnList = new ArrayList<com.hx.engine.pojo.Message>();
		for (Message comm : list) {
			returnList.add(new com.hx.engine.pojo.Message(comm));
		}
		return returnList;
	}

	public com.hx.engine.pojo.Message getById(Integer id) {
		Message comm = daoMessage.getById(id);
		if (comm != null) {
			return new com.hx.engine.pojo.Message(comm);
		}
		return new com.hx.engine.pojo.Message(-1);
	}

	public void saveOrUpdate(Message message) {
		daoMessage.saveOrUpdate(message);
	}

	public IDAOMessage getDaoMessage() {
		return daoMessage;
	}

	public void setDaoMessage(IDAOMessage daoMessage) {
		this.daoMessage = daoMessage;
	}

	public void delete(Message message) {
		daoMessage.delete(message);
	}

}
