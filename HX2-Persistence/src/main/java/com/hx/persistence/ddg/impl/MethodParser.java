package com.hx.persistence.ddg.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;

import com.hx.persistence.tx.TransactionManager;

public class MethodParser {

    private MethodParser() {
        super();
    }

    public static MethodInterceptor parse(Class<?> clazz, String methodName, TransactionManager transactionManager) {

        for (FindType findType : FindType.values()) {

            if (methodName.startsWith(findType.toString())) {
                String methodNameTail = methodName.substring(findType.toString().length());

                List<String> properties = new LinkedList<String>();
                List<Boolean> ascOrderProperties = new LinkedList<Boolean>();
                List<String> orderProperties = new LinkedList<String>();
                boolean limited = false;

                // WITH LAZIES
                boolean withLazies = false;
                if (methodNameTail.startsWith("WithLazies")) {
                    if (findType != FindType.count) {
                        withLazies = true;
                        methodNameTail = methodNameTail.substring("WithLazies".length());
                    } else {
                        return new UnknownMethodInterceptor();
                    }
                }

                // LIMITED
                if (methodNameTail.endsWith("Limited")) {
                    methodNameTail = methodNameTail.substring(0, methodNameTail.length() - "Limited".length());
                    limited = true;
                }

                if (methodNameTail.length() == 0) {

                } else if (methodNameTail.startsWith("By") || methodNameTail.startsWith("OrderAscBy")
                        || methodNameTail.startsWith("OrderDescBy")) {

                    if (methodNameTail.startsWith("By")) {
                        methodNameTail = methodNameTail.substring("By".length());
                    }

                    String[] split = methodNameTail.split("And", -1);

                    if (split.length == 0) {
                        return new UnknownMethodInterceptor();
                    }

                    // PROPERTIES && SORTED PROPERTIES
                    boolean firstPhase = true;
                    for (String element : split) {

                        if (element.length() == 0) {
                            return new UnknownMethodInterceptor();
                        }

                        if (element.startsWith("OrderAscBy")) {
                            ascOrderProperties.add(Boolean.TRUE);
                            element = element.substring("OrderAscBy".length());
                            orderProperties.add(getPropertyName(element));
                            firstPhase = false;
                        } else if (element.startsWith("OrderDescBy")) {
                            ascOrderProperties.add(Boolean.FALSE);
                            element = element.substring("OrderDescBy".length());
                            orderProperties.add(getPropertyName(element));
                            firstPhase = false;
                        } else if (firstPhase) {
                            properties.add(getPropertyName(element));
                        } else {
                            return new UnknownMethodInterceptor();
                        }
                    }
                } else {
                    return new UnknownMethodInterceptor();
                }

                if (properties.size() == 1 && properties.contains("example")) {
                    return new FindByExampleMethodInterceptor(transactionManager, clazz, findType, withLazies,
                            ascOrderProperties, orderProperties, limited);
                }
                return new FindByPropertiesMethodInterceptor(transactionManager, clazz, findType, withLazies,
                        ascOrderProperties, orderProperties, limited, properties);
            }
        }

        if ("save".equals(methodName)) {
            return new SaveMethodInterceptor(transactionManager);
        } else if ("update".equals(methodName)) {
            return new UpdateMethodInterceptor(transactionManager);
        } else if ("saveOrUpdate".equals(methodName)) {
            return new SaveOrUpdateMethodInterceptor(transactionManager);
        } else if ("delete".equals(methodName)) {
            return new DeleteMethodInterceptor(transactionManager);
        } else if ("saveAll".equals(methodName)) {
            return new SaveAllMethodInterceptor(transactionManager);
        } else if ("updateAll".equals(methodName)) {
            return new UpdateAllMethodInterceptor(transactionManager);
        } else if ("saveOrUpdateAll".equals(methodName)) {
            return new SaveOrUpdateAllMethodInterceptor(transactionManager);
        } else if ("deleteAll".equals(methodName)) {
            return new DeleteAllMethodInterceptor(transactionManager);
        } else if ("refresh".equals(methodName)) {
            return new RefreshMethodInterceptor(transactionManager);
        } else if ("merge".equals(methodName)) {
            return new MergeMethodInterceptor(transactionManager);
        } else if ("mergeAll".equals(methodName)) {
            return new MergeAllMethodInterceptor(transactionManager);
        } else if ("flush".equals(methodName)) {
            return new FlushMethodInterceptor(transactionManager);
        }

        return null;
    }

    private static String getPropertyName(String propertyName) {
        char lowerCase = Character.toLowerCase(propertyName.charAt(0));
        return lowerCase + propertyName.substring(1);
    }
}
