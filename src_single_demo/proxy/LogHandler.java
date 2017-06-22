/* 
 * @(#)LogHandler.java    Created on 2013-12-10
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-10 下午4:38:28 $
 */
public class LogHandler implements InvocationHandler {
    private final Object delegate;

    public LogHandler(Object obj) {
        this.delegate = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doBefore();

        Object result = method.invoke(delegate, args);

        after();
        return result;
    }

    private void doBefore() {
        System.out.println("before....");
    }

    private void after() {
        System.out.println("after....");
    }
}
