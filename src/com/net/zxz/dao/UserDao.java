/* 
 * @(#)LoginDao.java    Created on 2013-10-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.net.zxz.bean.User;
import com.net.zxz.memcache.MemcachedMgr;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-24 上午10:33:31 $
 */
@Repository("userDao")
public class UserDao extends BaseUserDao<User> {

    public List<User> find() {
        String sql = "SELECT * FROM T_USER where id = 9";
        return this.query(sql, new Object[] {}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User login = new User();
                login.setId(rs.getLong("ID"));
                login.setUserName(rs.getString("userName"));
                login.setMainPhone(rs.getLong("mainPhone"));
                return login;
            }

        });
    }

    public void update() {
        @SuppressWarnings("unused")
        Random random = new Random();
        String sql = "update T_USER set mainPhone = " + new Random().nextInt() + " where id = 9";
        this.execute(sql);
    }

    public void update(String sql) {

    }

    public List<User> listUser() {
        // String sql = "SELECT * FROM T_USER LIMIT 0,8";
        String sql = "SELECT * FROM T_USER";
        return this.query(sql, new Object[] {}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User login = new User();
                login.setId(rs.getLong("ID"));
                login.setUserName(rs.getString("userName"));
                login.setMainPhone(rs.getLong("mainPhone"));
                return login;
            }

        });
    }

    public User getUserById(long userId) {
        String sql = "SELECT * FROM T_USER where id = " + userId;
        return (User) this.queryForObj(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setUserName(rs.getString("userName"));
                user.setMainPhone(rs.getLong("mainPhone"));
                return user;
            }
        });
    }

    public boolean checkBoolean() {
        String sql = "SELECT count(1) FROM T_USER where id = -1";
        return (Boolean) queryForObj(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                System.out.println(rs.getInt(1));
                return rs.getBoolean(1);
            }
        });
    }

    public List exeSql(String sql, Object[] args, RowMapper rowMapper) {
        return this.query(sql, args, rowMapper);
    }

    /*
     * 测试
     */
    public static void main(String[] args) {
        // 得到MemcachedManager实例
        MemcachedMgr cache = MemcachedMgr.getInstance();
        // 创建UserDao对象
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(
                "E:\\MyWork\\workspace\\testDemo\\base_src\\conf\\spring\\applicationContext.xml");
        UserDao userDao = webApplicationContext.getBean(UserDao.class);
        System.out.println(userDao.checkBoolean());
    }
}
