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
public class Thread_线程锁_Test {
    private final String flag[] = { "true" };

    class NotifyThread extends Thread {
        public NotifyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                sleep(1000);// 推迟3秒钟通知
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (flag) {
                flag.notifyAll();
            }
        }
    };

    class WaitThread extends Thread {
        public WaitThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (flag) {
                System.out.println(getName() + " begin waiting!");
                long waitTime = System.currentTimeMillis();

                try {
                    flag.wait(3000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                waitTime = System.currentTimeMillis() - waitTime;
                System.out.println("wait time :" + waitTime);
                System.out.println(getName() + " end waiting!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread_线程锁_Test test = new Thread_线程锁_Test();
        NotifyThread notifyThread = test.new NotifyThread("notify01");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        notifyThread.start();
        waitThread01.start();
    }
}
