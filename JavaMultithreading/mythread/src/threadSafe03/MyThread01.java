package threadSafe03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread01 extends Thread{
    static int ticket = 0;

    Lock lock = new ReentrantLock();


    @Override
    public void run() {
        while (true) {
            // 加锁
            lock.lock();
            try {
                if(ticket == 100) {
                    break;
                }else{
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ticket++;
                    System.out.println(getName() + " 正在卖第 " + ticket + " 张票");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            } finally {
                // 释放锁
                lock.unlock();
            }
        }
    }
}
