package com.net.zxz.test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.net.zxz.bean.User;
import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-11-29 下午5:56:05 $
 */
public class test {
    private static UserService userService;
    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "base_src/conf/spring/applicationContext.xml", "src/conf/spring/**/applicationContext-*.xml" });
        userService = (UserService) webApplicationContext.getBean("userService");
    }

    public static void main(String[] aa) {
        test0();
        // test1();
        // test2();
        // test3();
    }

    public static void test0() {
        String sql = "INSERT INTO t_agency_school(agencyid,schoolid,NAME,a) VALUES(?,?,?,?)";
        final List<Object[]> p = new ArrayList<Object[]>();
        p.add(new Object[] { 3, 1, "12343", "d" });
        p.add(new Object[] { 4, 2, "12343", null });
        // p.add(new Object[] { "df", 2, "12343", null });
        userService.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] args = p.get(i);
                ps.setObject(1, args[0]);
                ps.setObject(2, args[1]);
                ps.setObject(3, args[2]);
                ps.setObject(4, args[3]);
            }

            @Override
            public int getBatchSize() {
                return p.size();
            }
        });
    }

    // 数据库统计 和 程序统计，差距
    public static long num = 0;

    public static void test3242343() {
        long s = System.currentTimeMillis();
        num = userService.queryForLong("select SQL_NO_CACHE count(id) from t_user WHERE agencyid=1 AND STATUS = 0");
        System.out.println("mysql:" + (System.currentTimeMillis() - s) + "---" + num);

        // 暂时注销，查询数据量过大，导致内存溢出
        // num = 0;
        // s=System.currentTimeMillis();
        // userService.exeSql("select * from t_user", new RowMapper<User>() {
        // @Override
        // public User mapRow(ResultSet rs, int i) throws SQLException {
        // //long ss = rs.getLong("id");
        // test.this.num ++;
        // return null;
        // }
        // });
        // System.out.println("java:*" + (System.currentTimeMillis()-s) + "---" + num);

        num = 0;
        s = System.currentTimeMillis();
        userService.exeSql("select SQL_NO_CACHE id from t_user WHERE agencyid=1 AND STATUS = 0", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                // long ss = rs.getLong("id");
                test.this.num++;
                return null;
            }
        });
        System.out.println("java_id:" + (System.currentTimeMillis() - s) + "---" + num);

        // s=System.currentTimeMillis();
        // userService.exeSql("select * from t_user order by mainemail limit 1000", new RowMapper<User>() {
        // @Override
        // public User mapRow(ResultSet rs, int i) throws SQLException {
        // //System.out.println(rs.getLong("id"));
        // rs.getLong(1);
        // rs.getString(2);
        // rs.getString(3);
        // rs.getString(4);
        // rs.getString(5);
        // rs.getString(6);
        // rs.getString(7);
        // rs.getString(8);
        // return null;
        // }
        // });
        // System.out.println("---");
        // System.out.println(System.currentTimeMillis()-s);
    }
}
