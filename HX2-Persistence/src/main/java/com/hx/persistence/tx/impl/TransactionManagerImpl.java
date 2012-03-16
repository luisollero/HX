package com.hx.persistence.tx.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Propagation;

import com.hx.persistence.tx.TransactionManager;

public class TransactionManagerImpl implements TransactionManager {

	private SessionFactory sessionFactory;

	private long timeoutDelayInMillis = 1 * 60 * 1000;

	private final ThreadLocal<Stack<SessionProxyInfo>> currentSessionProxyInfos;

	private final Timer timer;

	public TransactionManagerImpl() {
		super();
		this.currentSessionProxyInfos = new ThreadLocal<Stack<SessionProxyInfo>>() {

			@Override
			protected Stack<SessionProxyInfo> initialValue() {
				return new Stack<SessionProxyInfo>();
			}
		};
		this.timer = new Timer(true);
		this.timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				timer.purge();
			}
		}, 0, 60 * 1000);
	}

	private class SessionProxyInfo {

		final Session session;
		final Stack<SessionProxyInfo> stack;
		final Session sessionProxy;
		TimeoutTimerTask timerTask;

		public SessionProxyInfo(boolean isReadOnly) {
			super();
			// session
			this.session = sessionFactory.openSession();
			if (isReadOnly) {
				this.session.setFlushMode(FlushMode.MANUAL);
			}
			// stack
			this.stack = currentSessionProxyInfos.get();
			stack.push(this);
			// sessionProxy
			TimeoutInvocationHandler invocationHandler = new TimeoutInvocationHandler(
					this);
			Session proxy = (Session) Proxy.newProxyInstance(
					Session.class.getClassLoader(),
					new Class[] { Session.class }, invocationHandler);
			this.sessionProxy = proxy;
			// timerTask
			this.timerTask = new TimeoutTimerTask(this);
		}

		void update() {
			synchronized (this) {
				this.timerTask.cancel();
				if (this.session.isOpen()) {
					this.timerTask = new TimeoutTimerTask(this);
				}
			}
		}

		void close() {
			synchronized (this) {
				this.stack.remove(this);
				this.timerTask.cancel();
				if (this.session.isOpen()) {
					this.session.close();
				}
			}
		}
	}

	private class TimeoutTimerTask extends TimerTask {

		SessionProxyInfo sessionProxyInfo;

		public TimeoutTimerTask(SessionProxyInfo sessionProxyInfo) {
			this.sessionProxyInfo = sessionProxyInfo;
			timer.schedule(this, timeoutDelayInMillis);
		}

		@Override
		public void run() {
			this.sessionProxyInfo.close();
		}
	}

	private class TimeoutInvocationHandler implements InvocationHandler {

		private final SessionProxyInfo sessionProxyInfo;

		public TimeoutInvocationHandler(SessionProxyInfo sessionProxyInfo) {
			super();
			this.sessionProxyInfo = sessionProxyInfo;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			this.sessionProxyInfo.update();
			return method.invoke(this.sessionProxyInfo.session, args);
		}
	}

	@Override
	public <T> T runInReadOnlyTransaction(Callable<T> callable)
			throws Exception {
		return this.runInTransaction(true, Propagation.REQUIRED, callable);
	}

	@Override
	public <T> T runInReadWriteTransaction(Callable<T> callable)
			throws Exception {
		return this.runInTransaction(false, Propagation.REQUIRED, callable);
	}

	@Override
	public <T> T runInTransaction(boolean isReadOnly, Propagation propagation,
			Callable<T> callable) throws Exception {

		Stack<SessionProxyInfo> stack = currentSessionProxyInfos.get();
		boolean isSession = !stack.isEmpty();
		if ((isSession && !isReadOnly && (stack.peek().session.getFlushMode() == FlushMode.ALWAYS))) {
			// the existent readOnly/readWrite session is reused when the new
			// session is readOnly
			isSession = false;
		}

		switch (propagation) {
		case MANDATORY:
			// Support a current transaction, throw an exception if none exists.
			if (!isSession) {
				throw new RuntimeException("Session not found in "
						+ Propagation.class.getSimpleName() + " " + propagation);
			}
			return callable.call();

		case NESTED:
			// Execute within a nested transaction if a current transaction
			// exists, behave like PROPAGATION_REQUIRED else.
			if (isSession) {
				throw new RuntimeException("JDBC does not support "
						+ Propagation.class.getSimpleName() + " " + propagation);
			}
			return createSessionAndTransactionAndRunCallable(isReadOnly,
					callable);

		case NEVER:
			// Execute non-transactionally, throw an exception if a transaction
			// exists.
			if (isSession) {
				throw new RuntimeException("Session found in "
						+ Propagation.class.getSimpleName() + " " + propagation);
			}
			return createSessionOnlyAndRunCallable(isReadOnly, callable);

		case NOT_SUPPORTED:
			// Execute non-transactionally, suspend the current transaction if
			// one exists.
			return createSessionOnlyAndRunCallable(isReadOnly, callable);

		case REQUIRED:
			// Support a current transaction, create a new one if none exists.
			if (isSession) {
				return callable.call();
			}
			return createSessionAndTransactionAndRunCallable(isReadOnly,
					callable);

		case REQUIRES_NEW:
			// Create a new transaction, suspend the current transaction if one
			// exists.
			return createSessionAndTransactionAndRunCallable(isReadOnly,
					callable);

		case SUPPORTS:
			// Support a current transaction, execute non-transactionally if
			// none exists.
			if (isSession) {
				return callable.call();
			}
			return createSessionOnlyAndRunCallable(isReadOnly, callable);

		default:
			throw new RuntimeException("Unknown "
					+ Propagation.class.getSimpleName() + " " + propagation);
		}
	}

	private <T> T createSessionAndTransactionAndRunCallable(boolean isReadOnly,
			Callable<T> callable) throws Exception {

		SessionProxyInfo sessionProxyInfo = new SessionProxyInfo(isReadOnly);
		try {

			Transaction transaction = sessionProxyInfo.session
					.beginTransaction();
			try {

				T t = callable.call();

				transaction.commit();
				transaction = null;
				return t;

			} finally {
				if (transaction != null) {
					transaction.rollback();
				}
			}

		} finally {
			sessionProxyInfo.close();
		}
	}

	private <T> T createSessionOnlyAndRunCallable(boolean isReadOnly,
			Callable<T> callable) throws Exception {

		SessionProxyInfo sessionProxyInfo = new SessionProxyInfo(isReadOnly);
		try {

			return callable.call();

		} finally {
			sessionProxyInfo.close();
		}
	}

	@Override
	public Session getCurrentSession() {
		Stack<SessionProxyInfo> stack = this.currentSessionProxyInfos.get();
		if (stack.empty()) {
			throw new RuntimeException("No open "
					+ Session.class.getSimpleName());
		}
		return stack.peek().sessionProxy;
	}

	@Override
	public void openReadOnlySession() {
		SessionProxyInfo sessionProxyInfo = new SessionProxyInfo(true);
		sessionProxyInfo.session.beginTransaction();
	}

	@Override
	public void openReadWriteSession() {
		SessionProxyInfo sessionProxyInfo = new SessionProxyInfo(false);
		sessionProxyInfo.session.beginTransaction();
	}

	@Override
	public void commitSession() {
		SessionProxyInfo sessionProxyInfo = currentSessionProxyInfos.get()
				.peek();
		try {
			sessionProxyInfo.session.getTransaction().commit();
		} finally {
			sessionProxyInfo.close();
		}
	}

	@Override
	public void rollbackSession() {
		SessionProxyInfo sessionProxyInfo = currentSessionProxyInfos.get()
				.peek();
		try {
			sessionProxyInfo.session.getTransaction().rollback();
		} finally {
			sessionProxyInfo.close();
		}
	}

	@Override
	public void close() {
		this.sessionFactory.close();
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public long getTimeoutDelayInMillis() {
		return this.timeoutDelayInMillis;
	}

	public void setTimeoutDelayInMillis(long timeoutDelayInMillis) {
		this.timeoutDelayInMillis = timeoutDelayInMillis;
	}
}
