/* 
 * @(#)MethodWrite.java    Created on 2013-12-20
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package reflect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取对象属性信息，并对属性赋值
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-20 下午2:13:06 $
 */
public class PropertyDescriptorTest {
    public void TestMethodInvoke() {
        // 需要赋的属性值
        Map<String, Object> propertyValueMap = new HashMap<String, Object>();
        propertyValueMap.put("id", 1001l);
        propertyValueMap.put("userName", "zhangxz");

        try {
            // 实例化一个Bean
            MyBean bean = new MyBean();
            // 获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            // 属性信息
            PropertyDescriptor[] propertyDesc = beanInfo.getPropertyDescriptors();
            // 循环属性信息
            for (int i = 0; i < propertyDesc.length; i++) {
                // 输出属性
                System.out.println("property:" + propertyDesc[i].getName());
                if ("class".equalsIgnoreCase(propertyDesc[i].getName())) {
                    continue;
                }

                // 获取属性的写方法
                Method method = propertyDesc[i].getWriteMethod();
                // 执行方法
                method.invoke(bean, new Object[] { propertyValueMap.get(propertyDesc[i].getName()) });
            }

            // 输出赋值结果
            System.out.println("");
            String msg = "输出结果 ->";
            msg += "id:" + bean.getId();
            msg += ", name:" + bean.getUserName();
            System.out.println(msg);
        }
        catch (IntrospectionException e) {
            System.out.println("Java Bean 内省异常。");
        }
        catch (IllegalAccessException ia) {
            System.out.println("参数异常。");
            ia.printStackTrace();
        }
        catch (InvocationTargetException ie) {
            System.out.println("invode异常。");
            ie.printStackTrace();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new PropertyDescriptorTest().TestMethodInvoke();
    }
}
