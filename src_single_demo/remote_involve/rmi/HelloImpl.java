/* 
 * @(#)HelloImpl.java    Created on 2014-5-13
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package remote_involve.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午5:44:33 $
 */
public class HelloImpl extends UnicastRemoteObject implements IHello {
    private static final long serialVersionUID = 1L;

    public HelloImpl() throws RemoteException { 
    } 

    public String helloWorld() throws RemoteException { 
        return "Hello World!"; 
    }
    
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException { 
        return "你好，" + someBodyName + "!"; 
    }
}
