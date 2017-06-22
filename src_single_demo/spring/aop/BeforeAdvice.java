/* 
 * @(#)IStudent.java    Created on 2014-5-14
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-14 下午5:02:15 $
 */
public class BeforeAdvice implements MethodBeforeAdvice{

    @Override
    public void before(Method method,Object[] args, Object target) throws Throwable {
        System.out.println( " 这是BeforeAdvice类的before方法. " );
    }
}
