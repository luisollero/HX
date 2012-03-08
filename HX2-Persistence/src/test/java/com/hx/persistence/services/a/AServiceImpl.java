package com.hx.persistence.services.a;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import com.hx.persistence.dao.a.ADao;
import com.hx.persistence.model.a.A;

public class AServiceImpl implements AService {

	private ADao aDao;

	@Override
	public void save(A a) {
		this.aDao.save(a);
	}

	@Override
	public List<A> find() {
		return this.aDao.find();
	}

	@Override
	public A getById(long id) {
		return this.aDao.getById(id);
	}

	@Override
	public List<A> scroll() {
		LinkedList<A> list = new LinkedList<A>();

		Enumeration<A> scroll = this.aDao.scroll();
		while (scroll.hasMoreElements()) {
			list.add(scroll.nextElement());
		}
		return list;
	}

	@Override
	public List<A> findOrderAscByNameLimited(int offset, int limit) {
		return aDao.findOrderAscByNameLimited(offset, limit);
	}

	public ADao getADao() {
		return aDao;
	}

	public void setADao(ADao dao) {
		aDao = dao;
	}
}
