/* 
 * @(#)IHello.java    Created on 2014-5-13
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package remote_involve.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午5:43:05 $
 */
public interface IHello extends Remote{
    public String helloWorld() throws RemoteException; 
    public String sayHelloToSomeBody(String someBodyName) throws RemoteException; 
}
