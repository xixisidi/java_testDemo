/* 
 * @(#)test.java    Created on 2014-3-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package _class.dynamic_load_jar;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-3-20 下午4:18:55 $
 */
public class test {
    public static void main(String args[]) throws Exception {
        //加载jar
        //String dir = "file:E:/testDemo.jar";//绝对路径
        String dir = "file:/lib/testDemo.jar";//项目路径
        URL url = new URL(dir);
        ClassLoader  cl = new URLClassLoader(new URL[]{url});
        
        //加载类
        Class<?> c = cl.loadClass("com.net.zxz.bean.User");//从加载器中加载Class
        System.out.println("类："+c.getName()+"[加载成功]");
        
        //实例化类
        Object user = c.newInstance();
        Method method = c.getMethod("setUserName", String.class);
        method.invoke(user, "zhangxz");
        method = c.getMethod("getUserName");
        System.out.println("User.userName:" + method.invoke(user) + "[实例化并赋值成功]");
    }
}
