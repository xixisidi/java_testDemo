/* 
 * @(#)point.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.kwnoledge;

import java.util.Date;

/**
 * 知识点
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 下午1:38:51 $
 */
public class KwnoledgePoint2 {
    private long id;
    // 知识点名称
    private String name;
    // 年级
    private String gradeCode;
    // 科目
    private String subjectCode;
    // 父知识点ID
    private long parentId;
    // 是否有子节点
    private boolean hasChild;
    // 知识点级别
    private int poinitLevel;
    // 创建时间
    private Date createTime;
    // 排序号
    private long orderNo;
    // 老的id
    private long wpId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public int getPoinitLevel() {
        return poinitLevel;
    }

    public void setPoinitLevel(int poinitLevel) {
        this.poinitLevel = poinitLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public long getWpId() {
        return wpId;
    }

    public void setWpId(long wpId) {
        this.wpId = wpId;
    }
}
