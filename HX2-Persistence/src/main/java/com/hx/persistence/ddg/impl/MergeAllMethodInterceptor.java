package com.hx.persistence.ddg.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import net.sf.cglib.proxy.MethodProxy;

import com.hx.persistence.tx.TransactionManager;

public class MergeAllMethodInterceptor extends TxMethodInterceptor {

    public MergeAllMethodInterceptor(TransactionManager transactionManager) {
        super(transactionManager);
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        LinkedList<Object> list = new LinkedList<Object>();
        for (Object item : ((Collection<?>) args[0])) {
            list.add(this.getCurrentSession().merge(item));
        }
        return list;
    }
}