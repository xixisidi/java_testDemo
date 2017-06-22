/* 
 * @(#)Login.java    Created on 2013-10-24
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-24 上午10:37:38 $
 */
public class User implements Serializable {
    private Long id;
    private String userName;
    private long mainPhone;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getMainPhone() {
        return mainPhone;
    }

    public void setMainPhone(long mainPhone) {
        this.mainPhone = mainPhone;
    }

}
