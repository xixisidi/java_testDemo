/*
 * @(#)service.java    Created on 2013-10-23
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.net.zxz.bean.User;
import com.net.zxz.dao.UserDao;
import com.net.zxz.memcache.annotation.Cacheable;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-23 下午8:28:10 $
 */
@Service("userService")
public class UserService {
    @Autowired
    private UserDao userDao;

    public void update() throws Exception {
        userDao.update();
        User login = userDao.find().get(0);
        System.out.println(login.getMainPhone());

        if (true) {
            throw new RuntimeException("执行失败");
        }
    }

    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
        return userDao.batchUpdate(sql, pss);
    }

    public void find() throws Exception {
        userDao.update();
        User login = userDao.find().get(0);
        System.out.println(login.getMainPhone());
        if (true) {
            throw new Exception("执行失败");
        }
    }

    /**
     * 缓存测试
     */
    @Cacheable(id = "listUser")
    public List<User> listUser() {
        return userDao.listUser();
    }

    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    public List exeSql(String sql, Object[] args, RowMapper<?> rowMapper) {
        return userDao.exeSql(sql, args, rowMapper);
    }

    public void exeSql(String sql, RowMapper<User> rowMapper) {
        userDao.exeSql(sql, new Object[] {}, rowMapper);
    }

    public long queryForLong(String sql) {
        return userDao.queryForLong(sql);
    }

    public int queryForInt(String sql, Object[] args) {
        return userDao.queryForInt(sql, args);
    }

    /**
     * 事务回滚测试
     */
    public void updateFailTest() {
        userDao.update();
        User login = userDao.find().get(0);
        System.out.println(login.getMainPhone());

        if (true) {
            throw new RuntimeException("执行失败");
        }
    }

    static int i = 0;

    public int update(String sql, PreparedStatementSetter setter) {
        // String i = UUID.randomUUID().toString();
        // System.out.println(i + "_s");
        // String sql1 = "update t_test set status = 2 where id = 1 and status <> 2";
        //
        // int ii = userDao.update(sql1, setter);
        // System.out.println(i + "_s_" + ii);
        // if (ii <= 0) {
        // return 0;
        // }
        //
        // try {
        // Thread.sleep(60 * 60 * 1000);
        return userDao.update(sql, setter);
        // }
        // catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // finally {
        // System.out.println(i + "_e");
        // return 0;
        // }
    }
}
