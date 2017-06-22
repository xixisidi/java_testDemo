/* 
 * @(#)MyDWRServlet.java    Created on 2013-11-11
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.net.zxz.dwr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ltd.getahead.dwr.DWRServlet;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-11-11 上午9:11:05 $
 */
@SuppressWarnings("deprecation")
public class MyDWRServlet extends DWRServlet {
    private static final long serialVersionUID = -3963044280324991356L;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.service(request, response);
        System.out.println("DWRServlet执行");
    }
}
