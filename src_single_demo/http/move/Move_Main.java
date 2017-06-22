/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.move;

import http.kwnoledge.KwnoledgePoint2;
import http.kwnoledge.SaveKwnoledgeService;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class Move_Main {
    final static String ROOT_URL = "http://homework.leke.cn/tag/tree/knowledgeTreeDataService/queryTreeNodes.htm?";
    final static String ID = "knowledgeId";
    final static String PARENT_ID = "parentId";
    final static String TEXT = "knowledgeName";

    public static void main(String[] args) throws Exception {
        replace(20061L, 19819L, 2);
        System.out.println("转移完成");
    }

    // toId 的 level
    public static void replace(long fromId, long toId, int level) {
        // 将from parent_id 设置成toId
        MoveService.update_start_leaf_level_and_parent_id(fromId, toId, level + 1);
        update_child_leaf_level(fromId, level + 2);
    }

    public static void update_child_leaf_level(long parentId, int level) {
        // 更改level
        MoveService.update_child_leaf_level(parentId, level);

        // 取出子节点
        List<KwnoledgePoint2> list = SaveKwnoledgeService.get_child_leaf_wp_by_parentid(parentId);

        // 没有子节点
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        // 循环插入子节点
        for (KwnoledgePoint2 kwnoledgePoint2 : list) {
            System.out.println(kwnoledgePoint2.getName() + "_" + level);
            update_child_leaf_level(kwnoledgePoint2.getId(), level + 1);
        }
    }
}
