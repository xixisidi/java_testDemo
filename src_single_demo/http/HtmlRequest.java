/*
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 获取url数据
 *
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-12-2 上午10:56:34 $
 */
public class HtmlRequest {
    // http://blog.csdn.net/ewili/article/details/9172599
    public static String getURL(String url) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        // 伪造数据
        getMethod.setRequestHeader("Cookie",
                "2f784d0a1c27219aea7261f3ff8a731f=1C_9B_62_70_71_8A_75_73_64_24_3E_E7_FF_22_B4_0E_50_8A_2A_CF_40_21_32_CE_3E_E8_92_A1_B7_00_DC_65; 8c965a695ba86a7eb5d5f2cfaa0589de=2F_C5_E3_54_D4_02_C3_F5; a4671f405d72ffb21e1ab88d0435f0e5=17_EF_CD_E7_02_42_FC_6B_30_AD_E4_6C_C7_4C_6A_DB; request-id=1492168489601; mobile_sessiont_id_myflb.kehou.com=GR8NJ55SF1-6B0KLGHMM4EGQ0MS9YC62-24BLQH1J-A4; userType=2; loginName=yy022012067");
        client.executeMethod(getMethod);
        return new String(getMethod.getResponseBody(), "utf-8");
    }

    public static void main(String[] args) throws Exception {
        String url = "https://myflb.kehou.com/student/myCourse.htm?page.currentPage=1&courseType=wait&userType=2&__data=true";
        String data = getURL(url);
        System.out.println(data);
    }
}
