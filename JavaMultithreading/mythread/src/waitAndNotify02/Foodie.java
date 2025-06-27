package waitAndNotify02;

import java.util.concurrent.ArrayBlockingQueue;

public class Foodie extends Thread{

    ArrayBlockingQueue<String> queue;

    public Foodie(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            // 注意在外面不要再写synchronized了,因为take方法内部已经有锁了,再写就锁嵌套了,容易导致死锁.
            // 不断的从阻塞队列中获取数据
            try {
                String data = queue.take();
                System.out.println("消费者获取了一个数据 " + data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
