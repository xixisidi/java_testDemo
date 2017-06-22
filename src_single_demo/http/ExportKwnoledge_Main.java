/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http;

import http.kwnoledge.KwnoledgePoint;
import http.kwnoledge.SaveKwnoledgeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class ExportKwnoledge_Main {
    final static String ROOT_URL = "http://homework.leke.cn/tag/tree/knowledgeTreeDataService/queryTreeNodes.htm?";
    final static String ID = "knowledgeId";
    final static String PARENT_ID = "parentId";
    final static String TEXT = "knowledgeName";

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 10; j++) {
                List<KwnoledgePoint> points = getJsonTree("0", j, i);
                if (CollectionUtils.isEmpty(points)) {
                    continue;
                }
                // 将2级转换成1级
                points = points.get(0).getPoints();
                for (KwnoledgePoint kwnoledgePoint : points) {
                    kwnoledgePoint.setParentId("0");
                }
                insertSubjectKwnoledge(points, getSubjectType(j), getGradeType(i), 1);
            }
        }
        System.out.println("插入完成");
    }

    /**
     * 获取url数据，并转换成对象[分级]
     * 
     * @param rootId
     * @param subjectId
     * @param schoolStageId
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static List<KwnoledgePoint> getJsonTree(String rootId, int subjectId, int schoolStageId)
            throws HttpException, IOException {
        String url = ROOT_URL + "parentId=" + rootId + "&rootId=0&visitMode=child&subjectId=" + subjectId
                + "&schoolStageId=" + schoolStageId;
        String data = HtmlRequest.getURL(url);
        if (StringUtils.isBlank(data)) {
            return null;
        }
        JSONArray jSONArray = JSONArray.fromObject(data);
        List<KwnoledgePoint> points = new ArrayList<KwnoledgePoint>();
        for (int i = 0; i < jSONArray.size(); i++) {
            KwnoledgePoint p = new KwnoledgePoint();
            JSONObject jsonObject = jSONArray.getJSONObject(i);
            p.setId(jsonObject.getString(ID));
            p.setParentId(jsonObject.getString(PARENT_ID));
            p.setText(jsonObject.getString(TEXT));
            points.add(p);
            p.setPoints(getJsonTree(jsonObject.getString(ID), subjectId, schoolStageId));
        }
        return points;
    }

    /**
     * 插入当前学科的知识点
     * 
     * @param points
     * @param subjectId
     * @param gradeId
     * @param level
     */
    public static void insertSubjectKwnoledge(List<KwnoledgePoint> points, String subjectId, String gradeId, int level) {
        for (int i = 1; i <= points.size(); i++) {
            KwnoledgePoint point = points.get(i - 1);
            SaveKwnoledgeService.insert(point, subjectId, gradeId, level, i,
                    CollectionUtils.isNotEmpty(point.getPoints()));
            print(point.getText(), level);
            if (CollectionUtils.isNotEmpty(point.getPoints())) {
                insertSubjectKwnoledge(point.getPoints(), subjectId, gradeId, level + 1);
            }
        }
    }

    /**
     * 按顺序打印出知识点
     * 
     * @param name
     * @param level
     */
    public static void print(String name, int level) {
        String k = "";
        for (int j = 0; j < level; j++) {
            k += " ";
        }
        System.out.println(k + name);
    }

    /**
     * 学科转换
     * 
     * @param i
     * @return
     */
    public static String getSubjectType(int i) {
        switch (i) {
        case 1:
            return "001";
        case 2:

            return "002";
        case 3:

            return "003";
        case 4:

            return "008";
        case 5:

            return "009";
        case 6:

            return "006";
        case 7:

            return "004";
        case 8:

            return "005";
        case 9:

            return "007";
        case 10:

            return "010";
        default:
            return "";
        }
    }

    /**
     * 年级转换
     * 
     * @param i
     * @return
     */
    public static String getGradeType(int i) {
        switch (i) {
        case 1:
            return "11";
        case 2:

            return "22";
        case 3:

            return "33";
        default:
            return "";
        }
    }
}
