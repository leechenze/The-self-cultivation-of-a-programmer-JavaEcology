package threadMethod01;

public class MyThread01 extends Thread{

    public MyThread01() {
    }

    public MyThread01(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() + " @ " + i);
        }
    }
}
