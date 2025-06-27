package waitAndNotify01;

public class Desk {
    /**
     * 缓冲区
     * 控制生产者和消费者的执行
     */

    // 表示当前缓冲区是否有数据
    public static int foodFlag = 0;
    // 表示消费者最多可以消费10个
    public static int count = 10;
    // 锁对象
    public static Object lock = new Object();


}
