/* 
 * @(#)IStudent.java    Created on 2014-5-14
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package spring.aop;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-5-14 下午5:02:15 $
 */
public class StudentImpl implements IStudent{
    public  void  addStudent(String name){
        System.out.println( " 欢迎  " + name + "  你加入Spring家庭! " );
    }
}