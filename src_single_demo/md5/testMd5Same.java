/*
 * @(#)test.java    Created on 2015-8-27
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package md5;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-27 下午4:30:12 $
 */
public class testMd5Same {
    private static String base_path = "/";

    public static void test() throws Exception {
        final String tempName = "temp.temp";

        int n = 128;
        byte[] bytes = new byte[n];

        UploadFileUtil.randomWrite(bytes, 0, tempName);
        String md5_value = md5.getFileMD5(UploadFileUtil.getFile(tempName));
        UploadFileUtil.rename(tempName, md5_value + ".temp");

        while (true) {
            n++;

            bytes = new byte[n];
            for (int i = 0; i < bytes.length; i++) {
                String[] names = UploadFileUtil.getFiles(base_path);
                for (int j = 0; j <= 255; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    // 修改文件内容
                    bytes[i] = (byte) j;
                    for (String srcName : names) {
                        if (srcName.equals(tempName)) {
                            continue;
                        }
                        UploadFileUtil.copyFile(tempName, srcName);
                        UploadFileUtil.randomWrite(new byte[] { bytes[i] }, i, tempName);
                        md5_value = md5.getFileMD5(UploadFileUtil.getFile(tempName));
                        String fileName = md5_value + ".temp";
                        if (UploadFileUtil.fileExists(fileName)) {
                            UploadFileUtil.rename(tempName, md5_value + "_same.temp");
                            printByte(UploadFileUtil.getFileAsBytes(md5_value + "_same.temp"));
                            printByte(UploadFileUtil.getFileAsBytes(fileName));
                            System.out.println("发现相同的md5！");
                            return;
                        }
                        UploadFileUtil.rename(tempName, fileName);
                        printByte(UploadFileUtil.getFileAsBytes(fileName));
                        System.out.println(">>>>>>>>>>>>>>>" + i + "_" + j + "_" + fileName);
                    }
                }
            }
        }
    }

    public static void printByte(byte[] bytes) {
        for (byte b : bytes) {
            System.out.print(b);
        }
        System.out.println("");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>开始");
        test();
        System.out.println(">>>>>>>>>>>>>>>结束");
    }
}
