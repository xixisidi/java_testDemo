/* 
 * @(#)JdbcTemplate.java    Created on 2015-2-9
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package dynamic_data_source.transaction;

import javax.sql.DataSource;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-2-9 下午1:35:04 $
 */
public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate {
    private DataSource dataSource1;
    private DataSource dataSource2;

    public DataSource getDataSource1() {
        return dataSource1;
    }

    public void setDataSource1(DataSource dataSource1) {
        this.dataSource1 = dataSource1;
    }

    public DataSource getDataSource2() {
        return dataSource2;
    }

    public void setDataSource2(DataSource dataSource2) {
        this.dataSource2 = dataSource2;
    }

    @Override
    public DataSource getDataSource() {
        return null;
    }

}
