/*
 * @(#)HttpHeader.java    Created on 2016年9月21日
 * Copyright (c) 2016 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package socket.proxy;

import java.io.UnsupportedEncodingException;

/**
 * @author zhangxz HTTP分块发送
 * @version $Revision: 1.0 $, $Date: 2016年9月21日 上午11:49:55 $
 */
public class HttpChunck {
    public static final byte[] HTTP_BODY_END = new byte[] { 0x0d, 0x0a, 0x30, 0x0d, 0x0a, 0x0d, 0x0a };
    private byte[] httpBytes;
    private final int httpLength;
    private int chunckStartIndex;
    private int chunckEndIndex;
    private int endTestIndex;
    private boolean isEnd;

    public HttpChunck(byte[] httpBytes, int startIndex, int httpLength, int preEndTestIndex)
            throws NumberFormatException, UnsupportedEncodingException {
        this.httpLength = httpLength;

        // 说明是从头开始初始化
        if (preEndTestIndex <= -1) {
            init(httpBytes, startIndex, httpLength);
        }
        // 继续前一次传输结束判断
        else {
            preTest(httpBytes, startIndex, httpLength, preEndTestIndex);
        }

    }

    /**
     * 从头初始化
     *
     * @param httpBytes
     * @param startIndex
     * @param httpLength
     * @throws NumberFormatException
     * @throws UnsupportedEncodingException
     */
    private void init(byte[] httpBytes, int startIndex, int httpLength)
            throws NumberFormatException, UnsupportedEncodingException {
        // 0x0d0x0a<length>0x0d0x0a
        // 获取起点
        for (int i = startIndex + 2; i < httpLength; i++) {
            if (httpBytes[i + 1] != 0x0d) {
                continue;
            }
            if (httpBytes[i + 2] != 0x0a) {
                continue;
            }

            chunckStartIndex = i + 3;
            break;
        }
        if (chunckStartIndex == 0) {
            return;
        }

        // 获取结束点
        // (char)httpBytes[4063]
        // new String(httpBytes, 0, httpLength, "utf-8");
        //new String(httpBytes, 0, 3, "utf-8").trim()
                
        try {
        		chunckEndIndex = Integer .valueOf(new String(httpBytes, startIndex + 2, chunckStartIndex - 4 - startIndex, "utf-8").trim(), 16)+ chunckStartIndex - 1;
        } catch (Exception e) {
                 e.printStackTrace();
        }

        // 验证是否是结束
        isEnd = false;
        endTestIndex = -1;
        int l = httpLength - (chunckEndIndex + 1);// 剩余字节长度
        if (l >= HTTP_BODY_END.length) {
            for (int i = 0; i < HTTP_BODY_END.length; i++) {
                if (httpBytes[chunckEndIndex + 1 + i] != HTTP_BODY_END[i]) {
                    isEnd = false;
                    break;
                }

                if (i == HTTP_BODY_END.length - 1) {
                    isEnd = true;
                }
            }
        }
        // 未结束，结束标识是否是在下一次传输
        else if (!isEnd) {
            for (int i = 0; i < l; i++) {
                if (httpBytes[chunckEndIndex + 1 + i] != HTTP_BODY_END[i]) {
                    break;
                }

                if (i == l - 1) {
                    endTestIndex = i;
                }
            }
        }
    }

    /**
     * 测试上一次结束
     *
     * @param httpBytes
     * @param startIndex
     * @param httpLength
     * @param preEndTestIndex
     */
    private void preTest(byte[] httpBytes, int startIndex, int httpLength, int preEndTestIndex) {
        for (int i = 0; i < httpLength; i++) {
            if (httpBytes[i] != HTTP_BODY_END[i + preEndTestIndex]) {
                isEnd = false;
                break;
            }

            if (i == httpLength - 1) {
                isEnd = true;
            }
        }
    }

    /**
     * 一个chunk多次数据流读取，更新状态
     *
     * @param httpBytes
     * @param remainContentLength
     * @return
     */
    public boolean remainContentLength(byte[] httpBytes, int httpLength, long remainContentLength) {
        chunckStartIndex = 0;
        chunckEndIndex = (int)remainContentLength - 1;
    	if (isEnd) {
            return isEnd;
        }

        long l = httpLength - remainContentLength;// 剩余字节长度
        if (l >= HTTP_BODY_END.length) {
            for (int i = 0; i < HTTP_BODY_END.length; i++) {
                if (httpBytes[(int) remainContentLength + i] != HTTP_BODY_END[i]) {
                    isEnd = false;
                    break;
                }

                if (i == HTTP_BODY_END.length - 1) {
                    isEnd = true;
                }
            }
        }
        // 未结束，结束标识是否是在下一次传输
        else if (!isEnd) {
            for (int i = 0; i < l; i++) {
                if (httpBytes[(int) remainContentLength + i] != HTTP_BODY_END[i]) {
                    break;
                }

                if (i == l - 1) {
                    endTestIndex = i;
                }
            }
        }

        return isEnd;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public int getChunckStartIndex() {
        return chunckStartIndex;
    }

    public int getChunckEndIndex() {
        return chunckEndIndex;
    }

    public int getEndTestIndex() {
        return endTestIndex;
    }

}
