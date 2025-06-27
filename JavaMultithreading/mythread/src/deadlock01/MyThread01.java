package deadlock01;

public class MyThread01 extends Thread {

    static Object objA = new Object();
    static Object objB = new Object();

    @Override
    public void run() {
        while(true) {
            if (getName().equals("treadAAA")) {
                synchronized(objA){
                    System.out.println("线程A拿到了A锁,准备拿B锁");
                    synchronized (objB){
                        System.out.println("线程A拿到了B锁,顺利执行完一轮");
                    }
                }
            } else if(getName().equals("treadBBB")) {
                synchronized(objB){
                    System.out.println("线程B拿到了B锁,准备拿A锁");
                    synchronized (objA){
                        System.out.println("线程B拿到了A锁,顺利执行完一轮");
                    }
                }
            }
        }
    }
}
