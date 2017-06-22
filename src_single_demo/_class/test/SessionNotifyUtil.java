/*
 * @(#)CoolieUtils.java    Created on 2013-11-7
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package _class.test;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 会话通知
 *
 * @author zhangxz
 * @version $Revision: 1.0 $, $Date: 2016年3月7日 上午9:42:02 $
 */
public class SessionNotifyUtil {
    private final static String KEY_PREFIX = "SessionNotifyUtil_";

    /**
     * 注册通知
     *
     * @param key
     * @param item
     */
    public static void register(String key, NotifyItem item) {
        System.out.println(item.getClass().getName());
    }

    /**
     * 执行通知
     *
     * @param key
     * @param params
     */
    public static void notify(String key, Map<String, Object> params) {
        // Object notifyItemObj = ServletActionContext.getRequest().getSession().getAttribute(KEY_PREFIX + key);
        // if (notifyItemObj == null) {
        // return;
        // }
        // ((NotifyItem) notifyItemObj).run(params);
    }

    public interface NotifyItem extends Serializable {
        public void run(Map<String, Object> params);
    }

    public void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object item = new NotifyItem() {
            @Override
            public void run(Map<String, Object> params) {
                System.out.println("鹅鹅鹅鹅鹅鹅");
            }
        };

        String innerClass = item.getClass().getName();

        Constructor constructor = item.getClass().getDeclaredConstructor(new Class[] { SessionNotifyUtil.class });
        NotifyItem myObj1 = (NotifyItem) constructor.newInstance(new SessionNotifyUtil());
        myObj1.run(null);
    }

    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException {
        new SessionNotifyUtil().test();
    }
}
