/* 
 * @(#)HelloImpl.java    Created on 2013-12-10
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package proxy;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-10 下午4:37:17 $
 */
public class HelloImpl implements Hello{

    public void sayHello(String to){
        System.out.println("Say hello to " + to);
    }

    public void print(String s){
        System.out.println("print : " + s);
    }
}
