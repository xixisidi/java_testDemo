/*
 * @(#)Test.java    Created on 2014-12-18
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package _class.test;

import java.lang.reflect.Field;

import javax.annotation.Resource;

public class Test {
    @Resource
    private String nameStr;

    public static void main(String[] args)
            throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Class<Test> clazz = Test.class;
        Object a = clazz.newInstance();

        Field[] fileds = clazz.getDeclaredFields();
        Field field = fileds[0];
        Resource securityAntion = field.getAnnotation(Resource.class);
        if (securityAntion != null) {
            field.setAccessible(true);
            field.set(a, "1234");
        }

        System.out.println(field.get(a));
    }
}
