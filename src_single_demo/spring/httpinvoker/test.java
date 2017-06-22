/* 
 * @(#)TestPo.java    Created on 2014-5-13
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package spring.httpinvoker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-13 下午6:08:42 $
 */
public class test {
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("src_single_demo/spring/httpinvoker/applicationContext-httpinvoker-client.xml");
        HttpInvokerTestI httpInvokerTestI = (HttpInvokerTestI) context.getBean("remoteService");
        System.out.println(httpInvokerTestI.getTestPo("dddd").getDesp());
   }
}
