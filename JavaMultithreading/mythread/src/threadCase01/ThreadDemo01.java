package threadCase01;

public class ThreadDemo01 {
    public static void main(String[] args) {
        /**
         * 多线程的第一种启动方式
         * 1.自己定义一个类,继承thread类
         * 2.重写run方法
         * 3.创建之类对象, 并启动线程
         */
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        t1.setName("Thread01");
        t2.setName("Thread02");

        t1.run();
        t2.run();

    }
}
