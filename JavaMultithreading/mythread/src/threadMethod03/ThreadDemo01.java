package threadMethod03;

public class ThreadDemo01 {
    public static void main(String[] args) {
        /**
         * setDaemon
         */

        MyThread01 t1 = new MyThread01();
        MyThread02 t2 = new MyThread02();

        t1.setName("goddess");
        t2.setName("spare");

        // 设置第二个线程为守护线程(备胎线程)
        t2.setDaemon(true);

        t1.start();
        t2.start();

    }
}
