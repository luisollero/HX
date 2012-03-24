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
		for (Communication comm : list) {
			returnList.add(new com.hx.engine.pojo.Communication(comm));
		}
		return returnList;
	}

	public com.hx.engine.pojo.Communication getById(Integer id) {
		Communication comm = daoCommunication.getById(id);
		if (comm != null) {
			return new com.hx.engine.pojo.Communication(comm);
		}
		return new com.hx.engine.pojo.Communication(-1);
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
