package producerModel;

public class Test {
    public static void main(String[] args) {
        Clerk c = new Clerk();
        // 生产者
        // 消费时不生产，生产时不消费
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(c) {
                    // 无限循环代表无限生产次数
                    while(true){
                        // 如果店员处的产品为0，则开始生产
                        if (c.productNum == 0) {
                            System.out.println("产品数为0，开始生产");
                            while(c.productNum < 4) {
                                // 增加库存，开始生产
                                c.productNum++;
                                System.out.println("库存" + c.productNum);
                            }
                            System.out.println("产品数为" + c.productNum + "， 生产结束");
                            // 唤醒消费者
                            c.notify();
                        }else{
                            // 这部分为有产品，不生产的状态
                            try {
                                // 让生产的线程等待
                                c.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }, "生产者").start();

        // 消费者
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (c) {
                    // 无限循环代表无限消费次数
                    while (true) {
                        // 如果店员处的产品为0，则开始生产
                        if (c.productNum == 4) {
                            System.out.println("产品数为4时，开始消费");
                            while (c.productNum > 0) {
                                // 消费产品，减少库存
                                c.productNum--;
                                System.out.println("库存" + c.productNum);
                            }
                            System.out.println("产品数为" + c.productNum + "， 结束消费");

                            // 唤醒生产者的线程
                            c.notify();
                        } else {
                            try {
                                // 让消费者的线程等待
                                c.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }, "消费者").start();

    }
}

class Clerk{
    public static int productNum = 0;

}