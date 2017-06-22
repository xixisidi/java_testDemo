/* 
 * @(#)ProcessTest.java    Created on 2014-8-18
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package cmd;

import java.io.IOException;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-8-18 下午5:17:45 $
 */
public class ProcessTest {

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("C:\\Windows\\system32\\calc.exe");// 启动计算器
        Process p = builder.start();
        // 调用这个方法的目的：直到calc.exe进程结束，才继续执行
        p.waitFor();// 即：calc.exe不结束，当前现成保持阻塞
    }
}
