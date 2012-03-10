package com.hx.engine.implementation;

import java.util.Collection;

import com.hx.engine.ICommunicationEngine;
import com.hx.model.dao.ethereal.IDAOCommunication;
import com.hx.model.dto.Communication;

public class CommunicationEngine implements ICommunicationEngine {

	private IDAOCommunication daoCommunication;
	
	public Collection<Communication> findAll() {
		return daoCommunication.find();
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
