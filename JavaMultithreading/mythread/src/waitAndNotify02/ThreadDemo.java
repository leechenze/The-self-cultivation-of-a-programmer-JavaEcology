package waitAndNotify02;

import java.util.concurrent.ArrayBlockingQueue;

public class ThreadDemo {
    public static void main(String[] args) {
        /**
         * 表示创建有界的String类型的阻塞队列,最多只能创建一个.
         * 创建阻塞队列对象
         */
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        // 创建线程对象,并把阻塞队列对象传递过去
        Cook cook = new Cook(queue);
        Foodie foodie = new Foodie(queue);

        // 开启线程
        cook.start();
        foodie.start();

    }
}
