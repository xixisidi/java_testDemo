/* 
 * @(#)test.java    Created on 2015-8-27
 * Copyright (c) 2015 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package md5;

import java.io.File;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2015-8-27 下午4:30:12 $
 */
public class test {
    public static void main(String[] args) {
        String path = "G:\\1\\1.jpg";
        File file = new File(path);
        System.out.print(md5.getFileMD5(file));
    }
}
