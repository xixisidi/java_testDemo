/*
 * @(#)HttpData.java    Created on 2016年9月21日
 * Copyright (c) 2016 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package socket.proxy;

import java.io.File;
import java.io.RandomAccessFile;

import com.opensymphony.util.GUID;

/**
 * @author zhangxz HTTP通信数据处理
 * @version $Revision: 1.0 $, $Date: 2016年9月21日 上午11:35:33 $
 */
public class HttpData {
    private final String path;
    private String header;
    private String body;
    private boolean isImage;

    public HttpData() {
        path = "F:\\temp\\" + GUID.generateGUID() + ".data";
    }

    /**
     * 保存通信信息
     *
     * @param bytes
     * @param startIndex
     * @param length
     */
    public void saveFile(byte[] bytes, int startIndex, int length) {
        try {
            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            RandomAccessFile randomFile = new RandomAccessFile(path, "rw");

            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(bytes, startIndex, length);
            randomFile.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印http信息
     *
     * @param gzip
     */
    public void printHttp(boolean gzip) {
        String content = "============================开始打印\r\n";
        content += "地址：" + path + "\r\n";
        content += header + "\r\n";
        if (isImage) {
            content += ">>>>>>>>>图片放弃打印_" + path + "<<<<<<<<<<<\r\n";
        }
        else {
            if (gzip) {
                body = GzipUtil.parseAsString(path);
            }
            else {
                body = GzipUtil.getAsString(path);
            }
            content += body + "\r\n";
        }
        content += "============================结束打印";
        System.out.println(content);
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPath() {
        return path;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean isImage) {
        this.isImage = isImage;
    }

}
