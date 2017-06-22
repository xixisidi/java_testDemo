package thread;

import java.util.concurrent.CountDownLatch;

/**
 * i++多线程下不安全
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-2 上午11:45:09 $
 */
public class Thread_i加加_Test {
    private static int i = 0;
    private static CountDownLatch countDownLatch;

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 100;
        countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new MyThread();// 执行线程
        }
        countDownLatch.await();// 睡眠等待程序线程执行完成
        System.out.println("异步执行结束，i:" + i);
    }

    public static class MyThread implements Runnable {
        public MyThread() {
            Thread thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            for (int j = 0; j < 1000; j++) {
                i++;
            }
            countDownLatch.countDown();
        }
    }
}
