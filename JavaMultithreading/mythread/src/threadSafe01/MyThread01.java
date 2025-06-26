package threadSafe01;

public class MyThread01 extends Thread{

    // 加上static表示所有类的对象都共享一个static.
    // 否则就会出现三个线程独立占用ticket变量, 导致三个线程卖300张票的结果.
    static int ticket = 0;

    // 创建锁对象, 一定要是唯一的, 所以要加static关键字
    static Object obj = new Object();
    // 或者直接用 MyThread01.class, 这是一个唯一的, 所以可以不用static关键字, 注意是当前类的字节码文件对象, 不是Thread
    Class<MyThread01> cls = MyThread01.class;


    @Override
    public void run() {
        // 注意synchronized不能写在while外面, 思考一下
        // 如果窗口1(线程1)抢到了, 锁就会关闭,其他的线程不能再进来了
        // 所以就会导致 while 中的线程都是同一个线程, 一直到执行结束, 也就是一个线程将所有的票都卖完
        // 当第一个执行完毕, 第二个和第三个线程进来时, ticket < 100已经不成立了, 所以会直接break掉.
        // 从而导致最终一个窗口1就把所有的100张票都卖完了.
        while (true) {
            // 同步代码块(上锁)
            // synchronized(obj) {
            // synchronized(cls) {
            synchronized(MyThread01.class) {
                if(ticket < 100) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    ticket++;
                    System.out.println(getName() + " 正在卖第 " + ticket + " 张票");
                }else{
                    break;
                }
            }
        }
    }
}
