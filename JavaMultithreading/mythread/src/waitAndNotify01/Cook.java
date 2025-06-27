package waitAndNotify01;

public class Cook extends Thread{
    @Override
    public void run() {

        while (true) {
            synchronized(Desk.lock) {
                // 如果缓冲区的消费总数为0, 就表示消费者已经不能再消费了
                if(Desk.count == 0){
                    break;
                }else{
                    // 判断消费区是否有数据
                    if (Desk.foodFlag == 1) {
                        // 如果有,就等待
                        try {
                            Desk.lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        // 如果没有,就生产数据
                        System.out.println("生产者生产了一条数据");
                        // 修改缓冲区的数据状态
                        Desk.foodFlag = 1;
                        // 唤醒等待的消费者消费
                        Desk.lock.notifyAll();
                    }
                }
            }
        }

    }
}