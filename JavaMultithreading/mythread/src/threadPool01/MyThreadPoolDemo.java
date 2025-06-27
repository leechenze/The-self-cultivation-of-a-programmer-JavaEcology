package threadPool01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {

        // 创建线程池
        // 无上限的线程池创建
        // ExecutorService pool1 = Executors.newCachedThreadPool();
        // 有上限的线程池创建
        ExecutorService pool1 = Executors.newFixedThreadPool(3);


        // 给线程池提交任务
        pool1.submit(new MyRunnable());
        // Thread.sleep(1000);
        pool1.submit(new MyRunnable());
        // Thread.sleep(1000);
        pool1.submit(new MyRunnable());
        // Thread.sleep(1000);
        pool1.submit(new MyRunnable());
        // Thread.sleep(1000);
        pool1.submit(new MyRunnable());
        // Thread.sleep(1000);

        // 销毁线程池, 线程池一般是不会销毁的, 因为服务器都是24h开机的, 所以没必要销毁线程池.
        // pool1.shutdown();
    }
}
