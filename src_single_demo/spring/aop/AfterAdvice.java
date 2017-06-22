/* 
 * @(#)IStudent.java    Created on 2014-5-14
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-14 下午5:02:15 $
 */
public class AfterAdvice implements AfterReturningAdvice{
    @Override
    public void afterReturning(Object returnValue ,Method method,Object[] args,Object target) throws Throwable {
        System.out.println("这是AfterAdvice类的afterReturning方法.");
    }
}
