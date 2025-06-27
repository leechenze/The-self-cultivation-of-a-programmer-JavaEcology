package deadlock01;

public class ThreadDemo {
    public static void main(String[] args) {
        MyThread01 t1 = new MyThread01();
        MyThread01 t2 = new MyThread01();

        t1.setName("threadAAA");
        t2.setName("threadBBB");

        t1.start();
        t2.start();

    }
}
