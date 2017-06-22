package com.net.zxz.test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-11-29 下午5:56:05 $
 */
public class test2 {
    private static UserService userService;

    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "base_src/conf/spring/applicationContext.xml", "src/conf/spring/**/applicationContext-*.xml" });
        userService = (UserService) webApplicationContext.getBean("userService");
    }

    public static void main(String[] aa) throws InterruptedException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sql = "insert into t_test(status) values(0)";
                userService.update(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {

                    }
                });
            }
        }).start();
        Thread.sleep(1000);
        new Thread(new Runnable() {

            @Override
            public void run() {
                String sql = "insert into t_test(status) values(0)";
                userService.update(sql, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {

                    }
                });
            }
        }).start();

    }
}
