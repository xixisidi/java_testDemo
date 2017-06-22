/* 
 * @(#)MyClass.java    Created on 2015-8-11
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-11 下午10:41:49 $
 */
public class Main {

    public static void main(String[] args) {
        Callback[] callbacks = new Callback[] { new MethodInterceptorImpl(), NoOp.INSTANCE };

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyClass.class);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new CallbackFilterImpl());

        MyClass my = (MyClass) enhancer.create();
        my.method();
        my.method2();
    }

    private static class CallbackFilterImpl implements CallbackFilter {

        @Override
        public int accept(Method method) {

            if (method.getName().equals("method2")) {
                return 1;

            }
            else {
                return 0;
            }
        }
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

            System.out.println(method);

            return proxy.invokeSuper(obj, args);
        }
    }

}
