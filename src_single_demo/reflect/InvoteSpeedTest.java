/* 
 * @(#)MethodWrite.java    Created on 2013-12-20
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package reflect;

import java.lang.reflect.Method;

/**
 * 反射速度测试
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-20 下午2:13:06 $
 */
public class InvoteSpeedTest {
    private final MyBean beanObj = new MyBean();

    public void TestMethodInvoke() throws Exception {
        long t = System.currentTimeMillis();
        int num = 100000;

        // 反射赋值
        for (int i = 1; i <= num; i++) {
            Class<MyBean> myBean = MyBean.class;
            Method method = myBean.getMethod("setUserName", String.class);
            method.invoke(beanObj, "zhangxz");
        }
        System.out.println("反射赋值" + num + "次" + (System.currentTimeMillis() - t) + "毫秒");

        // 普通赋值
        t = System.currentTimeMillis();
        for (int i = 1; i <= num; i++) {
            beanObj.setUserName("zhangxz1");
        }
        System.out.println("普通赋值" + num + "次" + (System.currentTimeMillis() - t) + "毫秒");
    }

    public static void main(String[] args) throws Exception {
        new InvoteSpeedTest().TestMethodInvoke();
    }
}
