/*
 * @(#)MyTask.java    Created on 2014-10-20
 * Copyright (c) 2014 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package timer.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhangxz
 * @version $Revision: 1.0 $, $Date: 2016年2月2日 下午5:05:59 $
 */
public class TestScheduledExecutorService {
    private static ScheduledFuture<?> inactivityFuture;
    private static ScheduledExecutorService inactivityTimer;

    public static void main(String[] args) {
        // 创建定时器
        inactivityTimer = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());

        // 创建一个延迟执行的任务
        inactivityTimer.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("关闭定时器");
                inactivityTimer.shutdown();
            }
        }, 100, TimeUnit.SECONDS);

        // 创建一个定时任务
        inactivityFuture = inactivityTimer.scheduleAtFixedRate(new Runnable() {
            private int count = 5;

            @Override
            public void run() {
                System.out.println("定时任务执行");
                count--;
                if (count == 0) {
                    // 关闭task
                    inactivityFuture.cancel(true);
                    // 关闭timer
                    // inactivityTimer.shutdown();
                    System.out.println("关闭定时任务");
                }
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    private static final class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            // thread.setDaemon(true);
            return thread;
        }
    }
}
