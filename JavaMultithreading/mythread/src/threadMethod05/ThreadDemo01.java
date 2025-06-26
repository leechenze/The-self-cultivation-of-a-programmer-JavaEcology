package threadMethod05;

public class ThreadDemo01 {
    public static void main(String[] args) throws InterruptedException {
        /**
         * join 插入线程
         */
        MyThread01 t1 = new MyThread01();
        t1.setName("potato");
        t1.start();

        // 表示把t1线程,插入到主线程之前执行
        // t1.join();

        // 一段for循环在当前线程(主线程/main线程)中执行
        for (int i = 0; i < 10; i++) {
            System.out.println("main线程 " + i);
        }

        // 表示把t1线程,插入到主线程之后执行
        // t1.join();

    }
}
