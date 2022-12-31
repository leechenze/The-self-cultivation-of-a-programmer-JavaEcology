package inheritThread;

public class Test {
    public static void main(String[] args) {
        Thread t0 = new TestThread();
        // 普通方法的同步执行
        // new Test().test1();
        // 启动线程(开始运行run方法中的异步代码块)
         t0.start();
        System.out.println("===========");
        System.out.println("===========");
        System.out.println("===========");
        // 如上输出的===========，每次main方法运行以上的输出和开启线程的run方法中的打印语句先后顺序是不固定的
        // 原因在于main执行t0.start()方法开启多线程之后，就相当于在main方法之外开启一个线程分支
        // 这个时候t0.start()的之后的其他代码的运行就与run方法运行无关了
        // 这就是多线程的异步，而普通方法（test1）的执行是同步的；

    }

    public void test1() {
        System.out.println("普通方法test1运行的代码");
        for (int i = 0; i < 5; i++) {
            System.out.println("普通方法test1运行的逻辑代码" + i);
        }
    }
}
