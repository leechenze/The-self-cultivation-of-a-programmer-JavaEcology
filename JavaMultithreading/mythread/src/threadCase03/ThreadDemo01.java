package threadCase03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadDemo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 多线程的第三种启动方式
         * 特点:可以获取到多线程的运行结果, 书写会比之前两个方式麻烦一些
         * 前两种方式的run方法都是void类型, 所以是没有返回结果的
         * 1.创建一个自定义的MyCallable类实现Callable接口
         * 2.重写call方法(又返回值的, 返回多线程运行的结果)
         * 3.创建自定义的MyCallable对象(用来表示多线程要执行的任务)
         * 4.创建Future对象(用于管理多线程运行的结果, Future是一个接口, 所以需要创建Future实现类FutureTask对象)
         * 5.创建Thread类对象, 并启动(表示线程)
         */

        // 创建自定义的MyCallable对象(用来表示多线程要执行的任务)
        MyCallable mc = new MyCallable();
        // 创建Future对象(用于管理多线程运行的结果, Future是一个接口, 所以需要创建Future实现类FutureTask对象)
        FutureTask<Integer> ft = new FutureTask<>(mc);
        // 创建Thread类对象, 并启动(表示线程)
        Thread t1 = new Thread(ft);
        // 启动线程
        t1.start();

        // 获取多线程的运行结果(sum的和)
        Integer res = ft.get();
        System.out.println(res);

    }
}
