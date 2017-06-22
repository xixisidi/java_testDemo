/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.copy.temp;

import http.kwnoledge.KwnoledgePoint2;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class Copy_From_Temp_Main {
    public static void main(String[] args) throws Exception {
        copyList();
        System.out.print("完成_end");
    }

    public static void copyList() {
        String gradeCode = "22";
        String subjectCode = "005";
        SaveKwnoledgeService.copy_root_list(subjectCode, gradeCode);
        List<KwnoledgePoint2> list = SaveKwnoledgeService.get_child_leaf_wp_by_parentid(0, gradeCode, subjectCode);

        // 循环插入子节点
        for (KwnoledgePoint2 kwnoledgePoint2 : list) {
            copy_child_leaf(kwnoledgePoint2.getId(), kwnoledgePoint2.getWpId(), 2);
        }
    }

    // 复制
    public static void startCopy(long start_wp_id, long insert_sq_id, int sq_level) {
        // 赋值开始节点
        SaveKwnoledgeService.copy_start_leaf(insert_sq_id, sq_level + 1, start_wp_id);
        // 复制子节点
        copy_child_leaf(get_sq_id_by_wp_id(start_wp_id), start_wp_id, sq_level + 2);
    }

    // 复制子节点
    public static void copy_child_leaf(long parentId, long parentId_wp, int level) {
        // 复制子节点
        SaveKwnoledgeService.copy_wp_to_sq_now_leaf(parentId, parentId_wp, level);

        // 取出wp子节点
        List<KwnoledgePoint2> list = SaveKwnoledgeService.get_child_leaf_wp_by_parentid(parentId);

        // 没有子节点
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        // 循环插入子节点
        for (KwnoledgePoint2 kwnoledgePoint2 : list) {
            System.out.println(kwnoledgePoint2.getName() + "_" + level);
            copy_child_leaf(kwnoledgePoint2.getId(), kwnoledgePoint2.getWpId(), level + 1);
        }
    }

    public static long get_sq_id_by_wp_id(long wp_id) {
        return SaveKwnoledgeService.get_sq_id_by_wp_id(wp_id);
    }
}
