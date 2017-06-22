/*
 * @(#)NotifyTest.java    Created on 2014-10-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package thread;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2014-10-20 下午8:10:38 $
 */
public class Thread_守护线程_Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                }
            }
        });

        // 守护线程会随主线程关闭而关闭
        thread.setDaemon(true);

        thread.start();
    }
}
