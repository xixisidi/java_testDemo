package com.net.zxz.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

import com.net.zxz.bean.User;
import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-11-29 下午5:56:05 $
 */
public class test1 {
    private static UserService userService;
    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "base_src/conf/spring/applicationContext.xml", "src/conf/spring/**/applicationContext-*.xml" });
        userService = (UserService) webApplicationContext.getBean("userService");
    }

    public static void main(String[] aa) {
        // test0();// 数据库统计 和 程序统计，差距
        // test1();//测试一次性全部查出 和 一条一条查出，差距
        // test2();
        // test3();
        testTimeDif();
    }

    /**
     * 测试 TIMESTAMPDIFF
     */
    public static void testTimeDif() {

        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        for (int i = 0; i < 1000; i++) {
            Calendar calendar = Calendar.getInstance();
            Date begin = calendar.getTime();
            calendar.add(Calendar.MINUTE, 1);
            final Date end = calendar.getTime();
            userService.exeSql("SELECT TIMESTAMPDIFF(MINUTE, now(), ?) as dif,now() as begin", new Object[] { end },
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet rs, int i) throws SQLException {
                            // System.out.println(rs.getLong("dif") + "-" + df.format(rs.getTimestamp("begin")) + "-" +
                            // df.format(end));
                            System.out.println(rs.getLong("dif") + "-" + rs.getTimestamp("begin").getTime() + "-"
                                    + end.getTime());
                            return null;
                        }
                    });

        }
    }

    // 数据库统计 和 程序统计，差距
    public static long num = 0;

    public static void test0() {
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
                test1.this.num++;
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

    // 测试一次性全部查出 和 一条一条查出，差距
    public static void test1() {
        long s = System.currentTimeMillis();
        List<User> userList = userService.listUser();
        System.out.println(System.currentTimeMillis() - s);

        s = System.currentTimeMillis();
        for (User user : userList) {
            User user2 = userService.getUserById(user.getId());
        }
        System.out.println(System.currentTimeMillis() - s);

        // s=System.currentTimeMillis();
        // User user2 = userService.getUserById(userList.get(0).getId());
        // System.out.println(System.currentTimeMillis()-s);
    }

    //
    public static void test2() {
        // 三张表关联获取 -------445
        long s = System.currentTimeMillis();
        userService
                .exeSql("SELECT DISTINCT a.id,a.name FROM t_agency a,t_course_push cp,t_course c WHERE (a.id = cp.p_agencyid AND cp.courseid = 541) OR (c.id = 541 AND a.id = c.agencyid)",
                        new RowMapper<User>() {
                            @Override
                            public User mapRow(ResultSet rs, int i) throws SQLException {
                                return null;
                            }
                        });
        System.out.println((System.currentTimeMillis() - s) + "---" + num);

        // union获取 --------5
        s = System.currentTimeMillis();
        userService
                .exeSql("SELECT a.id,a.name FROM t_agency a,t_course_push cp WHERE a.id = cp.p_agencyid AND cp.courseid = 541 UNION SELECT a.id,a.name FROM t_agency a,t_course c WHERE c.id = 541 AND a.id = c.agencyid",
                        new RowMapper<User>() {
                            @Override
                            public User mapRow(ResultSet rs, int i) throws SQLException {
                                return null;
                            }
                        });
        System.out.println((System.currentTimeMillis() - s) + "---" + num);

        // 两条sql获取 -------12
        s = System.currentTimeMillis();
        userService.exeSql("SELECT * FROM t_agency a,t_course c WHERE c.id = 541 AND a.id = c.agencyid",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        return null;
                    }
                });
        userService.exeSql(
                "SELECT * FROM t_agency a,t_course_push cp WHERE a.id = cp.p_agencyid AND cp.courseid = 541",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        return null;
                    }
                });
        System.out.println((System.currentTimeMillis() - s) + "---" + num);
    }

    public static void test3() {
        long s = System.currentTimeMillis();
        userService.exeSql("SELECT COUNT(id) FROM t_agency_school GROUP BY schoolid", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                return null;
            }
        });
        System.out.println((System.currentTimeMillis() - s) + "---" + num);

        s = System.currentTimeMillis();
        userService.exeSql("SELECT * FROM t_agency_school ORDER BY schoolid", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                return null;
            }
        });
        int y = 0;
        for (int i = 0; i < 100; i++) {
            y++;
        }
        System.out.println((System.currentTimeMillis() - s) + "---" + num);
    }
}
