/* 
 * @(#)test.java    Created on 2014-8-19
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package jvm;

import java.util.Properties;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-8-19 上午11:01:16 $
 */
public class test {

    public static void main(String[] args) {
        Properties props = System.getProperties();
        System.out.println("读取虚拟机参数：");
        System.out.println("  tstudy.fileconvert.start：" + props.getProperty("-tstudy.fileconvert.start"));
        System.out.println("  java.version：" + props.getProperty("java.version"));
        System.out.println("  XX:MaxNewSize：" + props.getProperty("XX:MaxNewSize"));
    }
}
