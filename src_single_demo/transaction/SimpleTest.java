/* 
 * @(#)SimpleTest.java    Created on 2015-8-2
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-2 下午3:17:22 $
 */
public class SimpleTest {
    public static String driver = "com.mysql.jdbc.Driver";
    public static String url = "jdbc:mysql://localhost:3306/test";
    public static String user = "root";
    public static String password = "zdsoft";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            stmt = conn.createStatement();
            // 查询
            rs = stmt.executeQuery("select * from t_user");
            rs.next();
            String name = rs.getString(2);
            System.out.print(name);

            // 更新
            pstmt = conn.prepareStatement("update t_user set name = ? where id = ?");
            pstmt.setString(1, "zhangxz1");
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement("select * from t_user where id = ?");
            pstmt.setInt(1, 1);
            rs = pstmt.executeQuery();
            rs.next();
            name = rs.getString(2);
            System.out.print(name);

            if (name.equals("zhangxz")) {
                conn.rollback();
            }

            conn.commit();
        }
        catch (SQLException se) {
            if (conn != null) {
                try {
                    conn.rollback();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            se.printStackTrace();
        }
        catch (Exception se) {

        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
                stmt = null;

            }

            if (pstmt != null) {
                try {
                    pstmt.close();
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
                pstmt = null;
            }

            if (conn != null) {
                try {
                    conn.close();
                }
                catch (SQLException se) {
                    se.printStackTrace();
                }
                conn = null;
            }
        }

    }
}
