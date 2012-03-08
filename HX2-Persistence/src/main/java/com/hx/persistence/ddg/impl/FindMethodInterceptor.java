package com.hx.persistence.ddg.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.hx.persistence.tx.TransactionManager;

public abstract class FindMethodInterceptor extends TxMethodInterceptor {

	protected final int MAX_RESULTS_DEFAULT = Integer.valueOf(100);

	public final Class<?> clazz;

	public final FindType findType;

	public final boolean withLazies;

	public final Collection<String> lazyProperties;

	public final List<Boolean> ascOrderProperties;

	public final List<String> orderProperties;

	public final boolean limited;

	public FindMethodInterceptor(TransactionManager transactionManager,
			Class<?> clazz, FindType findType, boolean withLazies,
			List<Boolean> ascOrderProperties, List<String> orderProperties,
			boolean limited) {

		super(transactionManager);
		this.clazz = clazz;
		this.findType = findType;
		this.withLazies = withLazies;
		if (this.withLazies) {
			SessionFactory sessionFactory = transactionManager
					.getSessionFactory();
			this.lazyProperties = WithLazies.getProperties(sessionFactory,
					clazz);
		} else {
			this.lazyProperties = new LinkedList<String>();
		}
		this.ascOrderProperties = ascOrderProperties;
		this.orderProperties = orderProperties;
		this.limited = limited;
	}

	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy methodProxy) {

		Criteria criteria = this.getCurrentSession().createCriteria(this.clazz);

		if (this.lazyProperties != null) {
			for (String property : this.lazyProperties) {
				criteria.setFetchMode(property, FetchMode.JOIN);
			}
			if (lazyProperties.size() > 0) {
				criteria
						.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			}
		}

		// WHERE
		configure(criteria, args);

		// ORDER BY
		Iterator<String> it1 = this.orderProperties.iterator();
		Iterator<Boolean> it2 = this.ascOrderProperties.iterator();
		while (it1.hasNext()) {
			String propertyName = it1.next();
			Boolean isAsc = it2.next();
			criteria.addOrder(isAsc ? Order.asc(propertyName) : Order
					.desc(propertyName));
		}

		// Si se limita la consulta, los siguientes parámetro deben indicar
		// la ventana inicial y el límite
		if (this.limited) {
			int offset = (Integer) args[args.length - 2];
			if (offset != 0) {
				criteria.setFirstResult(offset);
			}
			int limit = (Integer) args[args.length - 1];
			if (limit != 0) {
				criteria.setMaxResults(limit);
			}
		}
		// } else if (this.findType != FindType.count) {
		// criteria.setFirstResult(0).setMaxResults(MAX_RESULTS_DEFAULT);
		// }

		switch (this.findType) {
		case get:
			return criteria.uniqueResult();

		case notNullGet:
			Object result = criteria.uniqueResult();
			if (result == null) {
				throw new RuntimeException(this.clazz.getSimpleName()
						+ " not found when " + method.getName() + "()");
			}
			return result;

		case find:
			return criteria.list();

		case scroll:
			ScrollableResults results = criteria.scroll();
			return new ScrollableEnumeration(results);

		case count:
			criteria.setProjection(Projections.rowCount());
			return criteria.list().get(0);

		default:
			throw new RuntimeException();
		}
	}

	protected abstract void configure(Criteria criteria, Object[] args);
}
