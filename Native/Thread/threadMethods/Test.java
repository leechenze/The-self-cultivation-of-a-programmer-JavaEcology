package threadMethods;

public class Test {
    public static void main(String[] args) {
        TestRun run0 = new TestRun();
        TestRun run1 = new TestRun();
        Thread t0 = new Thread(run0);
        Thread t1 = new Thread(run1);
        t0.setName("线程t0");
        t1.setName("线程t1");
        t0.start();
        t1.start();
        // 如果在创建线程时没有指定名称，那么系统给定的默认名称为Thread-0， Thread-1...
        System.out.println(t0.getName());
        // t0.setName("线程ttttt0");
        System.out.println(t0.getName());

        // 线程的优先级，就是哪个线程有较大的概率先执行
        // 优先级使用数组1-10来表示的，数字越大优先级越高，如果没有设置优先级那么默认值是5
        System.out.println("t0的优先级是" + t0.getPriority());
        // 设置线程的优先级
        t0.setPriority(1);
        t1.setPriority(10);









        System.out.println("===========1");
        System.out.println("===========2");
        System.out.println("===========3");
    }
}

class TestRun implements Runnable{
    int count = 0;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "多线程运行的代码");
        for(int i = 0; i < 5; i++) {
            count ++;
            System.out.println(Thread.currentThread().getName() + "多线程的逻辑代码" + count);
        }
    }
}