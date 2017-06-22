/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.copy;

import http.kwnoledge.KwnoledgePoint;
import http.kwnoledge.SaveKwnoledgeService;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class wwwwww_Main {
    final static String ROOT_URL = "http://homework.leke.cn/tag/tree/knowledgeTreeDataService/queryTreeNodes.htm?";
    final static String ID = "knowledgeId";
    final static String PARENT_ID = "parentId";
    final static String TEXT = "knowledgeName";

    public static void main(String[] args) throws Exception {
        String[] subjectCodes = new String[] { "006", "007", "008", "009" };
        for (int i = 0; i < subjectCodes.length; i++) {
            List<KwnoledgePoint> list = SaveKwnoledgeService.getFirstLevelKwnoledgePoints(subjectCodes[i]);
            updateSubjectCode(list);// 递归修改学科类型
        }
        System.out.println("处理结束");
    }

    public static void updateSubjectCode(List<KwnoledgePoint> list) {
        for (KwnoledgePoint kwnoledgePoint : list) {
            System.out.println(kwnoledgePoint.getId());
            // 修改子节点SubjectCode
            SaveKwnoledgeService.updateSubjectCodeByParentId(kwnoledgePoint.getId(), kwnoledgePoint.getSubjectCode());

            // 如果有子节点，对子节点进行处理
            List<KwnoledgePoint> points = SaveKwnoledgeService.getKwnoledgePointsBYParentId(kwnoledgePoint.getId());
            if (CollectionUtils.isEmpty(points)) {
                break;
            }
            updateSubjectCode(points);
        }
    }
}
