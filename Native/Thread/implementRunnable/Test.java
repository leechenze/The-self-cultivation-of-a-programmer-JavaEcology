package implementRunnable;

public class Test {
    public static void main(String[] args) {
        Thread t0 = new Thread(new TestRunnable());
        // t0.start();
        Runnable run = new TestRunnable();
        Thread t1 = new Thread(run, "t1");
        t1.start();
        Thread t2 = new Thread(run, "t2");
        t2.start();

        System.out.println("===========");
        System.out.println("===========");
        System.out.println("===========");
    }
}
