/* 
 * @(#)LazyProxy.java    Created on 2015-8-13
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package cglib.lazy_loader;

import net.sf.cglib.proxy.LazyLoader;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-13 下午11:30:18 $
 */
public class LazyProxy implements LazyLoader {
    // 拦截Bean的加载，本方法会延迟处理
    @Override
    public Object loadObject() throws Exception {
        System.out.println("开始延迟加载!");
        TestBean bean = new TestBean(); // 创建实体Bean
        bean.setUserName("test"); // 给一个属性赋值
        return bean; // 返回Bean
    }
}
