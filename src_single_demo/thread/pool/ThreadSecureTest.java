package thread.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 线程安全的测试|多线程使用方法
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-2 上午11:45:09 $
 */
public class ThreadSecureTest {
    public static void main(String[] args) {
        // 进行10次测试
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    public static void test() {
        ThreadSecureTest threadSecureTest = new ThreadSecureTest();

        // 用来测试的List
        // 线程安全
        List list = Collections.synchronizedList(new ArrayList());
        // 线程不安全
        // List list = new ArrayList();

        // 线程数量(1000
        int threadCount = 1000;
        // 用来让主线程等待threadCount个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        // 启动threadCount个子线程
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(threadSecureTest.new MyThread(list, countDownLatch));
            thread.start();
        }
        try {
            // 主线程等待所有子线程执行完成，再向下执行
            countDownLatch.await();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // List的size
        System.out.println(list.size());
    }

    class MyThread implements Runnable {
        private final List list;
        private final CountDownLatch countDownLatch;

        public MyThread(List list, CountDownLatch countDownLatch) {
            this.list = list;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            // 每个线程向List中添加100个元素
            for (int i = 0; i < 100; i++) {
                list.add(new Object());
            }
            // 完成一个子线程
            countDownLatch.countDown();
        }
    }
}
