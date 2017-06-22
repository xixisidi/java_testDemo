/* 
 * @(#)MethodWrite.java    Created on 2013-12-20
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package reflect;

/**
 * 动态加载类
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-20 下午2:13:06 $
 */
public class DynamicLoadClassTest {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("reflect.MyBean");// 动态加载类
        MyBean bean = (MyBean) cls.newInstance();// 实例化对象
        bean.hello("成功调用！");// 调用对象方法
    }
}
