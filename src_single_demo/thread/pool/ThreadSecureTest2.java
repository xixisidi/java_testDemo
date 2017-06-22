package thread.pool;
import java.util.concurrent.CountDownLatch;

/**
 * 线程安全的测试|多线程使用方法
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-2 上午11:45:09 $
 */
public class ThreadSecureTest2 {
    public static void main(String[] args) {
        // 进行10次测试
        for (int i = 0; i < 10; i++) {
            test0(); //异步
            //test1(); //同步
        }
    }
    
    //异步
    public static void test0() {
        ThreadSecureTest2 threadSecureTest = new ThreadSecureTest2();
        A a = threadSecureTest.new A();
        
        // 线程数量1000
        int threadCount = 1000;
        // 用来让主线程等待threadCount个子线程执行完毕
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ASC asc = threadSecureTest.new ASC();
        // 启动threadCount个子线程
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new MyThread(a, countDownLatch,asc));
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
        System.out.println(a.getI());
    }
    
    //同步
    public static void test1(){
        ThreadSecureTest2 threadSecureTest = new ThreadSecureTest2();
        A a = threadSecureTest.new A();
        
        // 线程数量1000
        int threadCount = 1000;
        for (int i = 0; i < threadCount; i++) {
            MyThread myThread = new MyThread(a);
            myThread.test();
        }
        // List的size
        System.out.println(a.getI());
    }
    
    static class MyThread implements Runnable {
        private A a;
        private ASC asc;
        private CountDownLatch countDownLatch;

        public MyThread(A a){
            this.a = a;
        }
        public MyThread(A a, CountDownLatch countDownLatch,ASC asc) {
            this.a = a;
            this.countDownLatch = countDownLatch;
            this.asc = asc;
        }
        
        public void test(){
            // 每个线程向List中添加100个元素
            for (int i = 0; i < 10000; i++) {
                //a.setI(a.getI() + 1);
                asc.add1(a);
            }
        }

        public void run() {
            test();
            // 完成一个子线程
            countDownLatch.countDown();
        }
    }
    
    public class ASC{
        private synchronized void add1(A a){
            a.setI(a.getI() + 1);
        }
    }
    
     public class A {
        private int  i = 0;

        public int getI() {
            return i;
        }

        public synchronized void setI(int i) {
            this.i = i;
        }
    }
}
