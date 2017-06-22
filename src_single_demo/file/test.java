/*
 * @(#)test.java    Created on 2014-3-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-3-20 下午4:18:55 $
 */
public class test {
    public static void main(String args[]) throws Exception {
        // File file = new File("F:\\weblog_2014_2_7.log");
        // InputStreamReader read = new InputStreamReader(new FileInputStream(file),"gbk");
        // BufferedReader br = new BufferedReader(read);
        // String dataString = null;
        // while((dataString = br.readLine())!=null){
        // System.out.println(dataString);
        // }

        byte[] aa = new byte[] { (byte) 255, (byte) 134, (byte) 63, (byte) 1, (byte) 255, (byte) 134, (byte) 63,
                (byte) 1, (byte) 255, (byte) 134, (byte) 255, (byte) 134, (byte) 63, (byte) 1, (byte) 255, (byte) 134,
                (byte) 63, (byte) 1, (byte) 255, (byte) 134, (byte) 255, (byte) 134, (byte) 63, (byte) 1, (byte) 255,
                (byte) 134, (byte) 63, (byte) 1, (byte) 255, (byte) 134, (byte) 255, (byte) 134, (byte) 63, (byte) 1,
                (byte) 255, (byte) 134, (byte) 63, (byte) 1, (byte) 255, (byte) 134, (byte) 255, (byte) 134, (byte) 63,
                (byte) 1, (byte) 255, (byte) 134, (byte) 63, (byte) 1 };
        OutputStream outputStream = new FileOutputStream(new File("G:\\1.a"));
        outputStream.write(aa);

    }

    public static byte[] int2byteArray(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >>> 24);// 取最高8位放到0下标
        result[1] = (byte) (num >>> 16);// 取次高8为放到1下标
        result[2] = (byte) (num >>> 8); // 取次低8位放到2下标
        result[3] = (byte) (num); // 取最低8位放到3下标
        return result;
    }
}
