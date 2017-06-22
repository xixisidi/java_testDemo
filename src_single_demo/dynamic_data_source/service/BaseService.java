/* 
 * @(#)LoginDao.java    Created on 2013-10-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package dynamic_data_source.service;

import org.springframework.beans.factory.annotation.Autowired;

import dynamic_data_source.dao.BaseDao;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-24 上午10:33:31 $
 */
public class BaseService {
    @Autowired
    private BaseDao baseDao;

    public int update(String sql) {
        return baseDao.update(sql);
    }
}
