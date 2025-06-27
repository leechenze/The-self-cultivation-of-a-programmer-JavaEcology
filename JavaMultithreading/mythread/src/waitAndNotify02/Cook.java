package waitAndNotify02;

import java.util.concurrent.ArrayBlockingQueue;

public class Cook extends Thread{

    ArrayBlockingQueue<String> queue;

    public Cook(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            // 注意在外面不要再写synchronized了,因为put方法内部已经有锁了,再写就锁嵌套了,容易导致死锁.
            // 不断的把数据放到阻塞队列中
            try {
                queue.put("date1111");
                System.out.println("生产者生产了一个数据");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
