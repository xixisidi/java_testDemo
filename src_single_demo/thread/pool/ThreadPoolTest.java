package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的测试
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-2 上午11:45:09 $
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        // test1();// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        // test2();// 创建一个可重用固定线程数的线程池
        test3();// 开两个线程的线程池
    }

    public static void test1() {
        ExecutorService pool = Executors.newSingleThreadExecutor();

        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口

        Thread t1 = new MyThread();

        Thread t2 = new MyThread();

        Thread t3 = new MyThread();

        Thread t4 = new MyThread();

        Thread t5 = new MyThread();

        // 将线程放入池中进行执行

        pool.execute(t1);

        pool.execute(t2);

        pool.execute(t3);

        pool.execute(t4);

        pool.execute(t5);

        // 关闭线程池

        pool.shutdown();

    }

    public static void test2() {
        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口

        Thread t1 = new MyThread();

        Thread t2 = new MyThread();

        Thread t3 = new MyThread();

        Thread t4 = new MyThread();

        Thread t5 = new MyThread();

        // 将线程放入池中进行执行

        pool.execute(t1);

        pool.execute(t2);

        pool.execute(t3);

        pool.execute(t4);

        pool.execute(t5);

        // 关闭线程池

        pool.shutdown();

    }

    public static void test3() {
        // 创建一个可重用固定线程数的线程池

        ExecutorService pool = Executors.newCachedThreadPool();

        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口

        Thread t1 = new MyThread();

        Thread t2 = new MyThread();

        Thread t3 = new MyThread();

        Thread t4 = new MyThread();

        Thread t5 = new MyThread();

        // 将线程放入池中进行执行

        pool.execute(t1);

        pool.execute(t2);

        pool.execute(t3);

        pool.execute(t4);

        pool.execute(t5);

        // 关闭线程池

        pool.shutdown();
    }

    public static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "正在执行。。。");
        }

    }
}
