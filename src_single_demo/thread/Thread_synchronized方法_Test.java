package thread;

import java.util.concurrent.CountDownLatch;

/**
 * synchronized方法使用
 * 
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2013-12-2 上午11:45:09 $
 */
public class Thread_synchronized方法_Test {
    private static Integer i = 0;
    private static String flag = "1";
    private static final int threadNum = 100;
    private static CountDownLatch countDownLatch;

    public static void main(String[] args) throws InterruptedException {
        // 通过普通执行
        threadTest("general", new AddFunction() {
            @Override
            public void add() {
                i++;
            }
        });

        // 通过synchronized_method执行
        threadTest("synchronized_method", new AddFunction() {
            @Override
            public void add() {
                syncAdd();
            }
        });

        // 通过synchronized_block执行
        threadTest("synchronized_block", new AddFunction() {
            @Override
            public void add() {
                synchronized (flag) {
                    i++;
                }
            }
        });
    }

    /**
     * 测试i++多线程
     * 
     * @param methodType
     * @param addFunction
     * @throws InterruptedException
     */
    private static void threadTest(String methodType, final AddFunction addFunction) throws InterruptedException {
        i = 0;
        // 线程计数器
        countDownLatch = new CountDownLatch(threadNum);
        // 执行线程
        for (int j = 0; j < threadNum; j++) {
            // 执行线程
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        // 执行i++
                        addFunction.add();
                    }
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        // 睡眠等待程序线程执行完成
        countDownLatch.await();
        System.out.println("异步执行[" + methodType + "]结束，i:" + i);
    }

    /**
     * i++方法
     * 
     * @author Administrator
     * @version $Revision: 1.0 $, $Date: 2014-11-12 下午1:32:11 $
     */
    public static interface AddFunction {
        public void add();
    }

    /**
     * 使i++原子化
     */
    public static synchronized void syncAdd() {
        i++;
    }
}
