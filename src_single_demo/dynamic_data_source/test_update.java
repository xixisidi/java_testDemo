/* 
 * @(#)test.java    Created on 2014-3-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package dynamic_data_source;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import dynamic_data_source.core.DatabaseContextHolder;
import dynamic_data_source.service.BaseService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-3-20 下午4:18:55 $
 */
public class test_update {
    private static BaseService baseService;

    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "src_single_demo/dynamic_data_source/spring/applicationContext.xml",
                "src_single_demo/dynamic_data_source/spring/applicationContext-dataSource.xml"
        });
        baseService = (BaseService) webApplicationContext.getBean("baseService");
    }

    public static void main(String args[]) throws Exception {
        DatabaseContextHolder.setCustomerType("dataSource2");
        String sql1 = "INSERT test1(name) values('123123')";
        baseService.update(sql1);
    }
}
