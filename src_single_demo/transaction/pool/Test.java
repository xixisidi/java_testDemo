/* 
 * @(#)SimpleTest.java    Created on 2015-8-2
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package transaction.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-2 下午3:17:22 $
 */
public class Test {
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/test";
    public static String user = "root";
    public static String password = "zdsoft";

    public static void main(String[] args) {
        // 初始化连接池
        BasicDataSource DS = new BasicDataSource();
        DS.setDriverClassName(driver);
        DS.setUsername(user);
        DS.setPassword(password);
        DS.setUrl(url);
        DS.setInitialSize(5); // 初始连接池连接个数
        DS.setMaxActive(100);// 最大激活连接数
        DS.setMaxIdle(30);// 最大闲置连接数
        DS.setMaxWait(10000);// 获得连接的最大等待毫秒数

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DS.getConnection();
            // conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from t_user");
            rs.next();
            System.out.println(rs.getString(2));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (DS != null) {
                    DS.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
