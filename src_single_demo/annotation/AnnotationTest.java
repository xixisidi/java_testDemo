/* 
 * @(#)AnnotationTest.java    Created on 2013-12-2
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package annotation;

import java.lang.reflect.InvocationTargetException;

/**
 * 注解的使用方法
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-2 上午11:46:40 $
 */
public class AnnotationTest {
    @HelloWorldAnnotation(name = "小明")  
    public String sayHello(String name) {  
        if (name == null ) {  
            name = "";  
        }         
        return name + " say hello world!";  
    }
    
    public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
        ParseAnnotationStub parse = new ParseAnnotationStub();
        String returnValue = parse.parseMethod(AnnotationTest.class);
        System.out.println(returnValue);
    }
}
