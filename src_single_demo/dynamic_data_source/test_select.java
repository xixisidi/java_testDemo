/* 
 * @(#)test.java    Created on 2014-3-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package dynamic_data_source;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

import dynamic_data_source.core.DatabaseContextHolder;
import dynamic_data_source.dao.BaseDao;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-3-20 下午4:18:55 $
 */
public class test_select {
    private static BaseDao baseDao;

    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "src_single_demo/dynamic_data_source/spring/applicationContext.xml",
                "src_single_demo/dynamic_data_source/spring/applicationContext-dataSource.xml"

        });
        baseDao = (BaseDao) webApplicationContext.getBean("baseDao");
    }

    public static void main(String args[]) throws Exception {
        DatabaseContextHolder.setCustomerType("dataSource1");
        String sql1 = "SELECT * FROM T_COURSE LIMIT 1";
        baseDao.queryForObj(sql1, new RowMapper<Object>() {

            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                System.out.println("name:" + rs.getString("name"));
                return null;
            }
        });

        DatabaseContextHolder.setCustomerType("dataSource2");
        String sql2 = "SELECT * FROM t_question LIMIT 1";
        baseDao.queryForObj(sql2, new RowMapper<Object>() {

            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                System.out.println("title:" + rs.getString("title"));
                return null;
            }
        });
    }
}
