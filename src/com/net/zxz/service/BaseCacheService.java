/* 
 * @(#)service.java    Created on 2013-10-23
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.service;

import java.util.List;

import com.net.zxz.common.CacheList;
import com.net.zxz.memcache.MemcachedMgr;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-23 下午8:28:10 $
 */
public class BaseCacheService {
    private final MemcachedMgr memcachedManager = MemcachedMgr.getInstance();

    /*
     * 获取对象
     */
    public Object getObject(String key) {
        return memcachedManager.get(key);
    }

    /*
     * 缓存单个对象
     */
    public void putObject(String key, Object object) {
        memcachedManager.add(key, object);
    }

    /*
     * 缓存列
     */
    public void putList(String key, List list) {
        CacheList<Object> cacheList = new CacheList<Object>();
        for (Object object : list) {
            cacheList.add(object);
        }
        memcachedManager.add(key, cacheList);
    }
}
