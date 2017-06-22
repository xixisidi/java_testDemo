/* 
 * @(#)LoginDao.java    Created on 2013-10-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package dynamic_data_source.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-24 上午10:33:31 $
 */
public class BaseDao {
    private static final Log log = LogFactory.getLog(BaseDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    public int update(String sql) {
        return jdbcTemplate.update(sql);
    }
}
