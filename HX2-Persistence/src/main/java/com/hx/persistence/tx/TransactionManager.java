package com.hx.persistence.tx;

import java.util.concurrent.Callable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;

public interface TransactionManager {

	SessionFactory getSessionFactory();

	<T> T runInReadOnlyTransaction(Callable<T> callable) throws Exception;

	<T> T runInReadWriteTransaction(Callable<T> callable) throws Exception;

	<T> T runInTransaction(boolean readOnly, Propagation propagation,
			Callable<T> callable) throws Exception;

	Session getCurrentSession();

	void openReadOnlySession();

	void openReadWriteSession();

	void commitSession();

	void rollbackSession();

	void close();
}
