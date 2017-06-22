/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.copy.temp;

import http.kwnoledge.KwnoledgePoint;
import http.kwnoledge.KwnoledgePoint2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class SaveKwnoledgeService {
    private static UserService userService;
    private final static String FROM_TABLE = "netstudy_exer_temp.t_knowledge_point";
    private final static String TO_TABLE = "netstudy5_exer.t_knowledge_point";
    private final static String TABLE_NAME = "netstudy5_exer.t_knowledge_point";
    static {
        ApplicationContext webApplicationContext = new FileSystemXmlApplicationContext(new String[] {
                "base_src/conf/spring/applicationContext.xml", "src/conf/spring/**/applicationContext-*.xml" });
        userService = (UserService) webApplicationContext.getBean("userService");
    }

    public static void copy_root_list(final String subjectCode, final String gradeCode) {
        String sql = "insert " + TO_TABLE;
        sql += "(wp_id,name,grade_code,subject_code,parent_id,has_child,point_level,creation_time,order_no)";
        sql += " select";
        sql += " id,name,grade_code,subject_code,0,has_child,1,creation_time,order_no";
        sql += " from " + FROM_TABLE;
        sql += " where parent_id = 0 and subject_code = ? and grade_code = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, subjectCode);
                ps.setObject(2, gradeCode);
            }
        });
    }

    /**
     * 插入学科
     * 
     * @param point
     * @param subjectId
     * @param gradeId
     * @param level
     * @param order
     * @param hasChild
     */
    public static void insert(final KwnoledgePoint point, final String subjectId, final String gradeId,
            final int level, final int order, final boolean hasChild) {
        String sql = "INSERT "
                + TABLE_NAME
                + "(id,NAME,grade_code,subject_code,parent_id,has_child,point_level,creation_time,order_no) VALUES(?,?,?,?,?,?,?,?,?)";
        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, point.getId());
                ps.setObject(2, point.getText());
                ps.setObject(3, gradeId);
                ps.setObject(4, subjectId);
                ps.setObject(5, point.getParentId());
                ps.setObject(6, hasChild);
                ps.setObject(7, level);
                ps.setObject(8, new Date());
                ps.setObject(9, order);
            }
        });
    }

    /**
     * 通过parentid修改学科类型
     * 
     * @param parentId
     * @param subjectCode
     */
    public static void updateSubjectCodeByParentId(final String parentId, final String subjectCode) {
        String sql = "UPDATE " + TABLE_NAME + " SET subject_code = ? WHERE parent_ID = ?";
        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, subjectCode);
                ps.setObject(2, parentId);
            }
        });
    }

    // 获取学科第一级
    public static List<KwnoledgePoint> getFirstLevelKwnoledgePoints(String subjectId) {
        String sql = "select * from " + TABLE_NAME + " where grade_code='22' and parent_id = 0 and subject_code = ?";
        return userService.exeSql(sql, new Object[] { subjectId }, new RowMapper<KwnoledgePoint>() {

            @Override
            public KwnoledgePoint mapRow(ResultSet rs, int i) throws SQLException {
                KwnoledgePoint point = new KwnoledgePoint();
                point.setId(rs.getString("id"));
                point.setParentId(rs.getString("parent_id"));
                point.setSubjectCode(rs.getString("subject_code"));
                return point;
            }

        });
    }

    /**
     * 通过parentid获取下一级
     * 
     * @param parentId
     * @return
     */
    public static List<KwnoledgePoint> getKwnoledgePointsBYParentId(String parentId) {
        String sql = "select * from " + TABLE_NAME + " where parent_id = ?";
        return userService.exeSql(sql, new Object[] { parentId }, new RowMapper<KwnoledgePoint>() {

            @Override
            public KwnoledgePoint mapRow(ResultSet rs, int i) throws SQLException {
                KwnoledgePoint point = new KwnoledgePoint();
                point.setId(rs.getString("id"));
                point.setParentId(rs.getString("parent_id"));
                point.setSubjectCode(rs.getString("subject_code"));
                return point;
            }

        });
    }

    // 复制子节点
    public static void copy_wp_to_sq_now_leaf(final long parentId, final long parentId_wp, final int level) {
        String sql = "insert t_knowledge_point";
        sql += "(wp_id,name,grade_code,subject_code,parent_id,has_child,point_level,creation_time,order_no)";
        sql += " select";
        sql += " id,name,grade_code,subject_code,?,has_child,?,creation_time,order_no";
        sql += " from t_knowledge_point_wp";
        sql += " where parent_id = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, parentId);
                ps.setObject(2, level);
                ps.setObject(3, parentId_wp);
            }
        });
    }

    // 复制指定节点
    public static void copy_start_leaf(final long insert_sq_id, final int level, final long wp_id) {
        String sql = "INSERT T_KNOWLEDGE_POINT";
        sql += "(wp_id,NAME,grade_code,subject_code,parent_id,has_child,point_level,creation_time,order_no)";
        sql += "SELECT";
        sql += " id,NAME,grade_code,subject_code,?,has_child,?,creation_time,order_no";
        sql += " FROM T_KNOWLEDGE_POINT_wp";
        sql += " WHERE id = ?";

        userService.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1, insert_sq_id);
                ps.setObject(2, level);
                ps.setObject(3, wp_id);
            }
        });
    }

    public static List<KwnoledgePoint2> get_child_leaf_wp_by_parentid(long parentId_sq) {
        String sql = "select * from t_knowledge_point where parent_id = ?";
        return userService.exeSql(sql, new Object[] { parentId_sq }, new RowMapper<KwnoledgePoint2>() {

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

    public static List<KwnoledgePoint2> get_child_leaf_wp_by_parentid(long parentId_sq, String gradeCode,
            String subjectCode) {
        String sql = "select * from t_knowledge_point where parent_id = ? and subject_code = ? and grade_code = ?";
        return userService.exeSql(sql, new Object[] { parentId_sq, subjectCode, gradeCode },
                new RowMapper<KwnoledgePoint2>() {

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

    // 通过老的id获取新的id
    public static long get_sq_id_by_wp_id(long wp_id) {
        String sql = "select id from t_knowledge_point where wp_id = ?";
        List<Long> list = userService.exeSql(sql, new Object[] { wp_id }, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getLong("id");
            }
        });

        return list.get(0);
    }
}
