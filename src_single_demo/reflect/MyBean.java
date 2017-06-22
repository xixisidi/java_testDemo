/* 
 * @(#)MethodWrite.java    Created on 2013-12-20
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package reflect;

/**
 * bean
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-20 下午2:13:06 $
 */
public class MyBean {
    private long id = 0;
    private String userName = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String hello(String name) {
        System.out.println("hello -> print:" + name);
        return name;
    }
}
