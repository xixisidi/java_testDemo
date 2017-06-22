/*
 * @(#)HtmlRequest.java    Created on 2014-12-2
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package http.upload_file;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2016年3月21日 下午5:22:18 $
 */
public class HtmlRequest {
    public static String getString(String url) throws Exception {
        GetMethod getMethod = null;
        try {
            HttpClient client = new HttpClient();
            getMethod = new GetMethod(url);
            getMethod.setRequestHeader("Range", " bytes=1-7");
            int status = client.executeMethod(getMethod);
            if (status >= 400) {
                throw new IOException("访问失败，状态码:" + status);
            }
            // getMethod.getResponseHeader(headerName);
            return new String(getMethod.getResponseBody(), "utf-8");
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (getMethod != null) {
                try {
                    getMethod.releaseConnection();
                }
                catch (Exception e2) {
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // String url = "http://uploadfile.netstudy5.dev/upload/question/20150228/4.txt";
        String url = "http://wxcs.oss-cn-shanghai.aliyuncs.com/crossdomain.xml";
        System.out.println(getString(url));
    }
}
