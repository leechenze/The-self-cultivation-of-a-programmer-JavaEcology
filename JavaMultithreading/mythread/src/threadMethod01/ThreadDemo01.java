package threadMethod01;


public class ThreadDemo01 {
    public static void main(String[] args) throws InterruptedException {
        /**
         * currentThread
         * setName
         * getName
         * sleep
         */
        MyThread01 t1 = new MyThread01("飞机");
        MyThread01 t2 = new MyThread01("坦克");

        t1.start();
        t2.start();

        System.out.println("11111111111");
        Thread.sleep(1000);
        System.out.println("22222222222");



    }
}
