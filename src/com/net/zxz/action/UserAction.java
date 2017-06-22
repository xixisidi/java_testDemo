/* 
 * @(#)IndexAction.java    Created on 2013-10-23
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.net.zxz.bean.User;
import com.net.zxz.service.UserService;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-10-23 下午5:57:40 $
 */
@Scope("prototype")
@Controller
public class UserAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Autowired
    private UserService userService;

    private List<User> userList;

    /*
     * 缓存的测试
     */
    public String userList() {
        userList = userService.listUser();
        return SUCCESS;
    }

    public List<User> getUserList() {
        return userList;
    }
}
