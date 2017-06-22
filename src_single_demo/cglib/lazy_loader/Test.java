/* 
 * @(#)BeanTest.java    Created on 2015-8-13
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package cglib.lazy_loader;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-13 下午11:31:05 $
 */
public class Test {
    public static void main(String[] args) {
        // 创建Bean类型的延迟加载代理实例
        TestBean bean = (TestBean) Enhancer.create(TestBean.class, new LazyProxy());
        System.out.println("------");
        System.out.println(bean.getUserName());
    }
}
