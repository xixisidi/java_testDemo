/*
 * @(#)HttpHeader.java    Created on 2016年9月21日
 * Copyright (c) 2016 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package socket.proxy;

import java.io.UnsupportedEncodingException;

/**
 * @author zhangxz http请求头或返回头
 * @version $Revision: 1.0 $, $Date: 2016年9月21日 上午11:49:55 $
 */
public class HttpHeader {
    public static final int CLIENT = 0;
    public static final int SERVER = 1;
    private static final String SEPARATOR = "\r\n";
    private static final byte[] HTTP_BODY_END = new byte[] { 0x0d, 0x0a, 0x30, 0x0d, 0x0a, 0x0d, 0x0a };
    private static final byte[] HTTP_HEAD_END = new byte[] { 0x0d, 0x0a, 0x0d, 0x0a };

    // 请求头
    public String method; // GET
    public String url;         // http://m.cs.netstudy5.tst/course/courseCenter.htm
    public String host;      // m.cs.netstudy5.tst
    public int port;           // 端口号
    public String domain;

    // 返回头
    public int code;// 200
    public String contentType;// :image/png
    public String contentEncoding;// :gzip
    public String transferEncoding;// :chunked
    public long contentLength;// :15381

    // 连接方式
    public String connection; // Keep-Alive or close

    private byte[] bytes;
    private int c;
    private String headerStr;
    private int headerEndIndex;

    /**
     * 获取客户端的http请求头
     *
     * @param bytes
     * @param c
     * @return
     */
    public String getClientHeader(byte[] bytes, int c) {
        for (int i = 0; i < c; i++) {

            // 是否是结束符
            for (int j = 0; j < HTTP_BODY_END.length; j++) {
                // 不是http结束
                if (bytes[j] != HTTP_BODY_END[j]) {
                    break;
                }

                if (j == HTTP_BODY_END.length - 1) {
                    return null;
                }

            }
        }
        return null;
    }

    /**
     * 获取服务器返回的http响应头
     *
     * @param bytes
     * @param c
     * @return
     */
    public String getServerHeader(byte[] bytes, int c) {
        // HTTP开头,说明是请求头
        if (bytes[0] == 'H' && bytes[1] == 'T' && bytes[2] == 'T' && bytes[3] == 'P') {
            headerEndIndex = 0;
            a: for (int i = 0; i < c; i++) {
                // 是否是结束符
                for (int j = 0; j < HTTP_HEAD_END.length; j++) {
                    if (bytes[i + j] != HTTP_HEAD_END[j]) {
                        break;
                    }

                    if (j == HTTP_HEAD_END.length - 1) {
                        headerEndIndex = i - 1;
                        break a;
                    }
                }

            }

            try {
                int headerLength = headerEndIndex + 1;
                headerStr = new String(bytes, 0, headerLength, "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String[] headers = headerStr.split(SEPARATOR);
            String text = null;
            for (int i = 0; i < headers.length; i++) {
                text = headers[i];
                if (i == 0) {
                    String[] values = text.split(" ");
                    code = Integer.parseInt(values[1].trim());
                    continue;
                }
                // 压缩方式
                if (text.indexOf("Content-Encoding:") == 0) {
                    contentEncoding = text.split(" ")[1].trim();
                    continue;
                }
                // 文件类型
                if (text.indexOf("Content-Type:") == 0) {
                    contentType = text.split(" ")[1].trim();
                    continue;
                }
                // 是否是chunked
                if (text.indexOf("Transfer-Encoding:") == 0) {
                    transferEncoding = text.split(" ")[1].trim();
                    continue;
                }
                // 正文长度
                if (text.indexOf("Content-Length:") == 0) {
                    // contentLength = Long.valueOf(text.split(" ")[1].trim(), 16);
                    contentLength = Long.parseLong(text.split(" ")[1].trim());
                    continue;
                }
            }

            return headerStr;
        }
        return null;
    }

    public int getHeaderEndIndex() {
        return headerEndIndex;
    }

    public boolean isHeader() {
        return code > 0;
    }

    public boolean isImage() {
        if (contentType != null && contentType.indexOf("image") >= 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Long.valueOf("16", 16));
    }

    // 重定向
    // HTTP/1.1 302 Found
    // Server: nginx
    // Date: Wed, 06 Jul 2016 10:35:08 GMT
    // Connection: keep-alive
    // Cache-Control: no-cache, must-revalidate
    // Expires: Thu, 01 Jan 1970 00:00:00 GMT
    // Pragma: no-cache
    // Set-Cookie: 915cde8154bedcc959214bdd5ea97d61=9; Domain=yflb.kehou.com; Expires=Sat, 02-Jun-2074 18:11:40 GMT;
    // Path=/
    // Location: http://m.yflb.kehou.com/mobileConfig.htm?aid=9
    // Content-Length: 0
}
