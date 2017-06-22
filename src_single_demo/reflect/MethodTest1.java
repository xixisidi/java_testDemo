/* 
 * @(#)MethodWrite.java    Created on 2013-12-20
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.net.zxz.bean.User;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-20 下午2:13:06 $
 */
public class MethodTest1 {
    private static User user = new User();

    public static void main(String[] args) throws Exception {
        // 获取对象申明的字段
        Field field = user.getClass().getDeclaredField("mainPhone");
        // 获取字段赋值的方法
        Method method = user.getClass().getMethod("setMainPhone", field.getType());
        // 执行方法
        method.invoke(user, new Object[] { 15067140779l });

        method = user.getClass().getMethod("getMainPhone");
        Object sObject = method.invoke(user, new Object[] {});
        // 打印
        System.out.println("mainPhone:" + sObject);
    }
}
