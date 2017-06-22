/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.delete;

import http.kwnoledge.KwnoledgePoint2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class DeleteService {
    private static UserService userService;
    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "base_src/conf/spring/applicationContext.xml", "src/conf/spring/**/applicationContext-*.xml" });
        userService = (UserService) webApplicationContext.getBean("userService");
    }

    public static List<KwnoledgePoint2> getKwnoledgePoint2s(Long[] ids) {
        String sql = "select * from t_knowledge_point where id in (" + StringUtils.join(ids, ",") + ")";
        return userService.exeSql(sql, new Object[] {}, new RowMapper<KwnoledgePoint2>() {
            @Override
            public KwnoledgePoint2 mapRow(ResultSet rs, int i) throws SQLException {
                KwnoledgePoint2 point = new KwnoledgePoint2();
                point.setId(rs.getLong("id"));
                point.setWpId(rs.getLong("wp_id"));
                point.setName(rs.getString("name"));
                return point;
            }
        });
    }

    public static void deleteById(final long id) {
        String sql = "delete from t_knowledge_point where id = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, id);
            }
        });
    }

    public static void updateNoChild(final long id) {
        String sql = "update t_knowledge_point set has_child = 0 where id = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, id);
            }
        });
    }
}
