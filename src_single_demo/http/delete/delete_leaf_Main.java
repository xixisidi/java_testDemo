/* 
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.delete;

import http.kwnoledge.KwnoledgePoint2;
import http.kwnoledge.SaveKwnoledgeService;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class delete_leaf_Main {
    public static void main(String[] args) throws Exception {
        Long[] ids = new Long[] { 5850L };
        boolean isJustDeleteLeaf = false;
        delete(ids, isJustDeleteLeaf);
        System.out.println("删除完成");
    }

    public static void delete(Long[] ids, boolean isJustDeleteLeaf) {
        if (ids.length == 0) {
            return;
        }

        List<KwnoledgePoint2> list = DeleteService.getKwnoledgePoint2s(ids);
        for (KwnoledgePoint2 kwnoledgePoint2 : list) {
            // step:1,删除子节点
            deleteChildLeaf(kwnoledgePoint2.getId());
            // step:2,更新为没有叶子节点
            DeleteService.updateNoChild(kwnoledgePoint2.getId());
        }
    }

    public static void deleteChildLeaf(long id) {
        List<KwnoledgePoint2> list = SaveKwnoledgeService.get_child_leaf_wp_by_parentid(id);

        // 没有子节点
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        // 删除子节点
        for (KwnoledgePoint2 kwnoledgePoint2 : list) {
            System.out.println(kwnoledgePoint2.getName());
            // step:1,删除子节点
            deleteChildLeaf(kwnoledgePoint2.getId());
            // step:2,删除自己
            DeleteService.deleteById(kwnoledgePoint2.getId());
        }

    }
}
