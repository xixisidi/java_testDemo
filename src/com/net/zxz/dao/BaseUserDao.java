/* 
 * @(#)LoginDao.java    Created on 2013-10-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-24 上午10:33:31 $
 */
public class BaseUserDao<T> {
    private static final Log log = LogFactory.getLog(BaseUserDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        try {
            log.debug("执行SQL:" + sql);
            return jdbcTemplate.query(sql, args, rowMapper);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Object queryForObj(String sql, RowMapper<Object> rowMapper) {
        try {
            log.debug("执行SQL:" + sql);
            return jdbcTemplate.queryForObject(sql, rowMapper);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public long queryForLong(String sql) {
        return jdbcTemplate.queryForLong(sql);
    }

    public int queryForInt(String sql, Object[] args) {
        return jdbcTemplate.queryForInt(sql, args);
    }

    protected void execute(String sql) {
        try {
            log.debug("执行SQL:" + sql);
            jdbcTemplate.execute(sql);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 批量处理
     * 
     * @param sql
     * @param pss
     * @return
     */
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
        return jdbcTemplate.batchUpdate(sql, pss);
    }

    public int update(String sql, PreparedStatementSetter setter) {
        return jdbcTemplate.update(sql, setter);
    }
}
