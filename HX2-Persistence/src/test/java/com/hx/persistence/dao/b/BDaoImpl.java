package com.hx.persistence.dao.b;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.hx.persistence.ddg.NoDynamicDaoGeneration;
import com.hx.persistence.model.b.B;
import com.hx.persistence.tx.TransactionManager;

public abstract class BDaoImpl implements BDao {

	private TransactionManager transactionManager;

	@NoDynamicDaoGeneration
	public List<B> findByNameLessThan(String name) {

		Criteria criteria = this.transactionManager.getCurrentSession()
				.createCriteria(B.class).add(Restrictions.lt("name", name));

		@SuppressWarnings("unchecked")
		List<B> list = criteria.list();
		return list;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
