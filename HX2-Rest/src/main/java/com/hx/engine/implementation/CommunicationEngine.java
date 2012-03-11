package com.hx.engine.implementation;

import java.util.ArrayList;
import java.util.Collection;

import com.hx.engine.ICommunicationEngine;
import com.hx.model.dao.ethereal.IDAOCommunication;
import com.hx.model.dto.Communication;

public class CommunicationEngine implements ICommunicationEngine {

	private IDAOCommunication daoCommunication;
	
	public Collection<com.hx.engine.pojo.Communication> findAll() {
		Collection<Communication> list = daoCommunication.findWithLazies();
		ArrayList<com.hx.engine.pojo.Communication> returnList = new ArrayList<com.hx.engine.pojo.Communication>();
		com.hx.engine.pojo.Communication pojo;
		for (Communication comm : list) {
			pojo = new com.hx.engine.pojo.Communication();
			pojo.setCommunicationId(comm.getId());
			pojo.setFromName(comm.getFrom().getCompleteName());
			pojo.setPublishedIn(comm.getPublishedIn());
			returnList.add(pojo);
		}
		return returnList;
	}

	public Communication getById(String id) {
		if (id != null) {
			return daoCommunication.getById(id);
		}
		return new Communication();
	}

	public void saveOrUpdate(Communication communication) {
		daoCommunication.saveOrUpdate(communication);
	}

	public IDAOCommunication getDaoCommunication() {
		return daoCommunication;
	}

	public void setDaoCommunication(IDAOCommunication daoCommunication) {
		this.daoCommunication = daoCommunication;
	}

	public void delete(Communication communication) {
		daoCommunication.delete(communication);
	}

}
