package threadMethod04;


public class ThreadDemo01 {
    public static void main(String[] args) {
        /**
         * yield 礼让线程
         */
        MyThread01 t1 = new MyThread01();
        MyThread01 t2 = new MyThread01();

        t1.setName("plane");
        t2.setName("tank");

        t1.start();
        t2.start();

    }
}
