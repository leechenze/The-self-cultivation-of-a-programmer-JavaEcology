package waitAndNotify01;

public class Foodie extends Thread{
    @Override
    public void run() {

        while(true){
            synchronized (Desk.lock){
                // 如果缓冲区的消费总数为0, 那么消费者就不能再消费了
                if (Desk.count == 0) {
                    break;
                }else{
                    // 先判断缓冲区是否有数据
                    if(Desk.foodFlag == 0) {
                        // 如果没有就等待
                        try {
                            // 这里用 Desk.lock 调用wait是为了让当前线程跟锁绑定.
                            Desk.lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        // 把消费者的总容量减一
                        Desk.count--;
                        // 如果有就消费
                        System.out.println("消费者在消费数据,还能再消费: " + Desk.count + " 条.");
                        // 消费完后唤醒生产者继续生产
                        Desk.lock.notifyAll();
                        // 修改缓冲区的状态(修改缓冲区是否有数据的状态)
                        Desk.foodFlag = 0;
                    }
                }
            }
        }

    }
}
