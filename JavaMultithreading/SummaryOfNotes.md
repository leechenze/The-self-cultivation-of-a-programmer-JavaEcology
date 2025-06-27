博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;

视频地址:BV1LG4y1T7n2

并发: 在同一时刻, 有多个指令在单个CPU上交替执行
并行: 在同一时刻, 有多个指令在多个CPU上同时执行




多线程的实现方式:
    
    1.继承Thread类的方式进行实现
    2.实现Runnable接口的方式进行实现
    3.利用Callable接口和Feture接口方式实现

继承Thread类的方式进行实现(threadCase01)
实现Runnable接口的方式进行实现(threadCase02)
利用Callable接口和Feture接口方式实现(threadCase03)

三种方式选择:
    如果不需要继承其他的类时, 可以直接使用Thread类实现, 用方法一即可
    如果需要继承其他类, 且不需要返回值, 不需要运行结果时, 可以用方法二
    如果需要继承其他类, 并且需要返回值时, 可以用方法三, 也是最推荐,推全面的.

    
Thread中常见的成员方法(threadMethod01)
    | 返回类型 / 方法签名                                                       | 功能描述                       |
    | ---------------------------------------------------------------------- | -------------------------- |
    | `void start()`                                                         | 启动线程(内部会调用 `run()`, 只能调用一次) |
    | `void run()`                                                           | 线程的执行体(可重写, 自定义线程逻辑)        |
    | `static void sleep(long millis)`                                       | 当前线程休眠指定毫秒(不释放锁)           |
    | `static void sleep(long millis, int nanos)`                            | 精确到纳秒的休眠                   |
    | `static void yield()`                                                  | 让出 CPU 使用权(可能马上被再次调度)(礼让线程/出让线程)      |
    | `void join()`                                                          | 当前线程等待该线程执行完毕(插入线程/插队线程)              |
    | `void join(long millis)`                                               | 等待该线程最多 millis 毫秒          |
    | `void join(long millis, int nanos)`                                    | 等待该线程最多 millis + nanos     |
    | `void interrupt()`                                                     | 中断该线程(设置中断标志位)             |
    | `boolean isInterrupted()`                                              | 判断该线程是否被中断(不清除标志)          |
    | `static boolean interrupted()`                                         | 判断当前线程是否被中断(**清除**中断标志)    |
    | `boolean isAlive()`                                                    | 判断线程是否还活着(未终止)             |
    | `String getName()`                                                     | 获取线程名字                     |
    | `void setName(String name)`                                            | 设置线程名字                     |
    | `long getId()`                                                         | 获取线程 ID(从 JDK 5 开始)        |
    | `int getPriority()`                                                    | 获取线程优先级(默认 5)              |
    | `void setPriority(int newPriority)`                                    | 设置线程优先级(范围 1\~10)          |
    | `Thread.State getState()`                                              | 获取线程当前状态(NEW、RUNNABLE 等)   |
    | `ThreadGroup getThreadGroup()`                                         | 获取线程所属线程组                  |
    | `static Thread currentThread()`                                        | 返回当前正在运行的线程对象              |
    | `boolean isDaemon()`                                                   | 判断该线程是否是守护线程               |
    | `void setDaemon(boolean on)`                                           | 设置为守护线程(需在 `start()` 前设置)  |
    | `ClassLoader getContextClassLoader()`                                  | 获取线程的上下文类加载器               |
    | `void setContextClassLoader(ClassLoader cl)`                           | 设置线程的上下文类加载器               |
    | `static boolean holdsLock(Object obj)`                                 | 判断当前线程是否持有 obj 的监视器锁       |
    | `StackTraceElement[] getStackTrace()`                                  | 获取该线程的栈追踪信息                |
    

线程的优先级(threadMethod02)
    在计算机中,线程的调度有两种
        1.抢占式调度(强调随机性)
        2.非抢占式调度(强调顺序轮流式)
    java线程采取的方式是第一种随机性的抢占式的调度方式
    线程的优先级分为1-10档,默认为5档,线程的优先级越大,那抢到cpu的概率就越大


守护线程(threadMethod03)
    当其他线程执行完毕之后, 守护线程会陆续结束, 守护线程并不会执行完.
    应用场景:
        比如在qq给朋友发送文件, 聊天窗口是一个线程, 传输文件也是一个线程
        当聊天窗口关闭后, 传输文件也应该退出, 那么这时, 传输文件的线程就可以设为一个守护线程
        

礼让线程(threadMethod04)
    礼让线程会让每个线程的执行尽可能的均匀执行
    如threadMethod4, 打印的plane和tank就会比较均匀,但是并不能保证百分百的均匀
    

插入线程(threadMethod05)
    设为插入线程后将会由于当前线程前执行, 如threadMethod5
    更具体来说, 它是在之前还是之后执行, 具体是由线程的join方法在当前线程的执行前还是执行后调用来决定的


线程生命周期
    线程的生命周期从 创建(new) 开始, 经过 启动(start) 进入 运行(runnable) 状态, 
    期间可能因为调用 sleep() 进入 阻塞(timed waiting) 状态, 醒来后继续运行, 最后在任务完成或异常时进入 终止(terminated) 状态.


线程安全问题演示(threadSafe01)
    问题1: 相同的票概率性的出现了多次
    问题2: 出现了超出范围的票号ticket
    

同步方法(threadSafe02)
    将同步代码块 synchronized 改造为同步方法


lock锁(threadSafe03)
    Lock是接口, 不能示例化, 这里采用它的实现类 ReentrantLock 来示例化
    

死锁(deadlock01)
    deadlock, 死锁演示, 演示的目的是警示两个锁最好不要嵌套使用
    

生产者和消费者
    生产者和消费者也叫等待唤醒机制, 是一个十分经典的多线程协作模式
    生产者线程：
        当缓冲区满时, 生产者线程调用 wait() 进入等待状态, 直到被消费者通过 notify() 或 notifyAll() 唤醒.
    消费者线程：
        当缓冲区空时, 消费者线程调用 wait() 进入等待状态, 直到被生产者通过 notify() 或 notifyAll() 唤醒.


消费者代码实现(waitAndNotify01.Foodie)
生产者代码实现(waitAndNotify01.Cook)
    Cook    表示生产者线程
    Foodie  表示消费者线程
    Desk    表示缓冲区
    

阻塞队列实现等待唤醒机制(waitAndNotify02)
    使用阻塞队列时, 生产者在队列满时自动阻塞, 消费者在队列空时自动阻塞, 无需显式使用 wait/notify
    put数据时, 放不进去会进行等待, 也叫做阻塞.
    take数据时, 取出第一个数据, 取不到会等待, 也叫做阻塞.
    阻塞队列的继承结构
        Iterable
            阻塞队列可以使用迭代器或者增强for进行遍历的
        Collection
            阻塞队列还实现了Collection接口,所以阻塞队列实际上就是一个单列集合
        Queue
            队列
        BlockingQueue
            阻塞队列
    以上四个都是接口,不能直接创建它们的对象, 要创建的是两个实现类的对象
        ArrayBlockingQueue
            底层是数组实现,是有界的,即必须指定队列长度
        LinkedBlockingQueue
            底层是链表,是无界的,最大值是int的最大值
        

线程的状态
    NEW	            线程已创建但尚未调用 start(), 还未启动.
    RUNNABLE	    线程已启动, 正在运行或准备运行(由 CPU 调度).
    BLOCKED	        线程在等待进入同步代码块或方法(等待锁, 阻塞状态).
    WAITING	        线程无限期等待另一个线程显式唤醒(如 wait()、join()).
    TIMED_WAITING	线程等待一定时间后自动唤醒(如 sleep()、wait(timeout)), 也称为计时等待.
    TERMINATED	    线程已执行完成或异常终止.


线程池(threadPool01)
    线程池通过复用一组固定数量的线程来执行多个任务, 避免频繁创建和销毁线程, 从而提高性能并控制资源使用
    线程池的实现:
        Executors: 线程池的工具类,通过调用方法返回不同类型的线程池对象
            public static ExecutorService newCachedThreadPool()
                创建没有上限的线程池
            public static ExecutorService newFixedThreadPool(int nThreads)
                创建有上限的线程池
    
    
自定义线程池(threadPool02)

    public ThreadPoolExecutor(
        int corePoolSize,                       // ① 核心线程数
        int maximumPoolSize,                    // ② 最大线程数
        long keepAliveTime,                     // ③ 非核心线程的存活时间
        TimeUnit unit,                          // ④ 非核心线程的存活时间单位
        BlockingQueue<Runnable> workQueue,      // ⑤ 任务队列
        ThreadFactory threadFactory,            // ⑥ 线程工厂（可指定线程名等）
        RejectedExecutionHandler handler        // ⑦ 拒绝策略(是ThreadPoolExecutor的静态内部类对象,一般用第一个默认的拒绝策略即可)
    )
    
    核心概念:
        核心线程
        临时线程
        队列长度
