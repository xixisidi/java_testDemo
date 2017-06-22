/* 
 * @(#)ProxyTest.java    Created on 2013-12-10
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package proxy;

import java.lang.reflect.Proxy;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-10 下午4:39:52 $
 * 
 * java动态代理
 */
public class ProxyTest {
    public static void main(String[] args) {
        HelloImpl impl = new HelloImpl();
        LogHandler handler = new LogHandler(impl);
        
        // 这里把handler与impl新生成的代理类相关联
        //Hello hello = (Hello) Proxy.newProxyInstance(impl.getClass().getClassLoader(), impl.getClass().getInterfaces(),handler);
        Hello hello = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(),handler);

        // 这里无论访问哪个方法，都是会把请求转发到handler.invoke
        hello.print("All the test");
    }
}
