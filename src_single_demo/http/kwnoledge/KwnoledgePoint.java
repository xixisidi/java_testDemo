/* 
 * @(#)point.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.kwnoledge;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识点
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 下午1:38:51 $
 */
public class KwnoledgePoint {
    private String id;
    private String parentId;
    private String text;
    private List<KwnoledgePoint> points = new ArrayList<KwnoledgePoint>();

    private String subjectCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<KwnoledgePoint> getPoints() {
        return points;
    }

    public void setPoints(List<KwnoledgePoint> points) {
        this.points = points;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

}
