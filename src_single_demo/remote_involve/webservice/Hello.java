/* 
 * @(#)Hello.java    Created on 2014-5-16
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package remote_involve.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-16 下午4:56:24 $
 */
@WebService
public class Hello {
    @WebMethod 
    public String hello(String name) {   
        return "Hello, " + name + "\n";   
    }   
        
     public static void main(String[] args) {   
       // create and publish an endpoint   
           Hello hello = new Hello();   
           Endpoint endpoint = Endpoint.publish("http://localhost:8080/hello", hello);    
    } 
}
