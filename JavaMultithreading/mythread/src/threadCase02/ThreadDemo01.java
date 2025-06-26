package threadCase02;

import threadCase01.MyThread;

public class ThreadDemo01 {
    public static void main(String[] args) {
        /**
         * 多线程的第二种启动方式
         * 1.自己定义一个类, 实现Runnable接口
         * 2.重写run方法
         * 3.创建自己的类的对象
         * 4.创建一个Thread类对象, 并开启线程
         */

        // 创建MyRun对象, 表示多线程要执行的任务
        MyRun run1 = new MyRun();

        // 创建线程对象
        Thread t1 = new Thread(run1);
        Thread t2 = new Thread(run1);

        // 给线程设置名字
        t1.setName("thread01");
        t2.setName("thread02");

        // 开启线程
        t1.start();
        t2.start();

    }
}
