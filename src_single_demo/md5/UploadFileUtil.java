/*
 * @(#)upload.java    Created on 2015-3-21
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package md5;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 文件上传服务的文件处理工具类
 *
 * @author zhangxz
 * @version $Revision: 1.0 $, $Date: 2015年11月17日 下午6:10:34 $
 */
public class UploadFileUtil {
    // 文件根路径
    public final static String ROOT = "G:\\md5\\";

    /**
     * 保存文件
     *
     * @param src
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Boolean saveFile(Object src, String filePath) throws IOException {
        filePath = ROOT + filePath;

        File toFile = new File(filePath);
        toFile.getParentFile().mkdirs();
        if (src instanceof File) {
            // 文件
            FileUtils.copyFile((File) src, toFile);
            return true;
        }
        else if (src instanceof String) {
            // 保存文本
            // 指定UTF-8编码写文件
            FileOutputStream fos = new FileOutputStream(toFile);
            try {
                fos.write(((String) src).getBytes("UTF-8"));
            }
            finally {
                IOUtils.closeQuietly(fos);
            }
            return true;
        }
        else if (src instanceof InputStream) {
            // 保存流
            BufferedOutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                IOUtils.copy((InputStream) src, outputStream);
            }
            finally {
                IOUtils.closeQuietly(outputStream);
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 获取文件长度
     *
     * @param filePath
     * @return
     */
    public static Long getFileLength(String filePath) {
        filePath = ROOT + filePath;
        File file = new File(filePath);
        if (file.isFile()) {
            return file.length();
        }
        else {
            return 0l;
        }
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath
     * @return
     */
    public static Boolean fileExists(String filePath) {
        filePath = ROOT + filePath;
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static Boolean deleteFile(String filePath) {
        filePath = ROOT + filePath;
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * 获取文件字符串
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getFileAsString(String filePath) throws Exception {
        filePath = ROOT + filePath;

        File file = new File(filePath);
        if (!file.isFile()) {
            return "";
        }
        StringBuffer context = new StringBuffer();
        BufferedReader input = null;
        try {
            // 指定UTF-8编码读文件
            input = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file.getAbsoluteFile()), Charset.forName("UTF-8")));
            String line = null;
            while ((line = input.readLine()) != null) {
                context.append(line);
            }
        }
        finally {
            if (input != null) {
                IOUtils.closeQuietly(input);
            }
        }
        return context.toString();
    }

    /**
     * 获取文件列表
     *
     * @param dirPath
     * @return
     */
    public static String[] getFiles(String dirPath) {
        dirPath = ROOT + dirPath;
        File file = new File(dirPath);
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new String[0];
        }
        String[] files = new String[listFiles.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = listFiles[i].getAbsolutePath().replace(ROOT, "");
        }
        return files;
    }

    /**
     * 写文件
     *
     * @param data
     * @param begin
     * @param path
     * @return
     * @throws Exception
     */
    public static Boolean randomWrite(Object data, long begin, String path) throws Exception {
        path = ROOT + path;

        RandomAccessFile fs = null;
        try {
            File file = new File(path).getParentFile();
            if (!file.exists()) {
                file.mkdirs();
            }

            fs = new RandomAccessFile(path, "rw");
            fs.seek(begin);// 指定位置开始写入
            if (begin > fs.length()) {
                throw new Exception("插入地址超过文件的长度[文件长度:" + fs.length() + ",插入地址:" + begin + "]");
            }

            // 写入
            if (data instanceof InputStream) {
                InputStream is = (InputStream) data;
                byte[] bytes = new byte[1024];
                int c = 0;
                while ((c = is.read(bytes, 0, bytes.length)) > 0) {
                    fs.write(bytes, 0, c);
                }
            }
            else {
                fs.write((byte[]) data);
            }

            return true;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (fs != null) {
                try {
                    fs.close();
                }
                catch (Exception e) {

                }
            }
        }
    }

    public static void copyFile(String dirPath, String srcPath) throws IOException {
        File srcFile = new File(ROOT + srcPath);
        File destFile = new File(ROOT + dirPath);
        FileUtils.copyFile(srcFile, destFile);
    }

    public static File getFile(String srcPath) {
        return new File(ROOT + srcPath);
    }

    public static void rename(String srcPath, String dirPath) {
        new File(ROOT + srcPath).renameTo(new File(ROOT + dirPath));
    }

    public static void main(String[] args) throws Exception {
        randomWrite(new byte[100], 0, "temp.temp");
        copyFile("4.temp", "temp.temp");
    }

    public static byte[] getFileAsBytes(String filePath) throws Exception {
        filePath = ROOT + filePath;
        InputStream is = new FileInputStream(new File(filePath));
        byte[] bytes = new byte[130];
        is.read(bytes);
        is.close();
        return bytes;
    }
}
