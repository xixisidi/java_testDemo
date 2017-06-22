/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.move;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class MoveService {
    private static UserService userService;
    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "base_src/conf/spring/applicationContext.xml", "src/conf/spring/**/applicationContext-*.xml" });
        userService = (UserService) webApplicationContext.getBean("userService");
    }

    // 复制子节点
    public static void update_child_leaf_level(final long parentId, final int level) {
        String sql = "update t_knowledge_point set point_level = ? where parent_id = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, level);
                ps.setObject(2, parentId);
            }
        });
    }

    // 复制子节点
    public static void update_start_leaf_level_and_parent_id(final long id, final long parent_id, final int level) {
        String sql = "update t_knowledge_point set point_level = ?,parent_id = ?,has_child=1 where id = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, level);
                ps.setObject(2, parent_id);
                ps.setObject(3, id);
            }
        });
    }
}
