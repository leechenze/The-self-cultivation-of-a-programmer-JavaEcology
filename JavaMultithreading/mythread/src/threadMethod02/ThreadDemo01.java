package threadMethod02;

public class ThreadDemo01 {
    public static void main(String[] args) {
        /**
         * setPriority
         * getPriority
         */

        MyRunnable mr = new MyRunnable();
        Thread t1 = new Thread(mr, "plane");
        Thread t2 = new Thread(mr, "tank");

        System.out.println(t1.getPriority());
        System.out.println(t2.getPriority());
        System.out.println(Thread.currentThread().getPriority());


        t1.setPriority(1);
        t2.setPriority(10);

        t1.start();
        t2.start();

    }
}
