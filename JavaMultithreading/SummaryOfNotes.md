博学之, 审问之, 慎思之, 明辨之, 笃行之;
零、壹、贰、叁、肆、伍、陆、柒、捌、玖、拾;



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
    | `void start()`                                                         | 启动线程（内部会调用 `run()`，只能调用一次） |
    | `void run()`                                                           | 线程的执行体（可重写，自定义线程逻辑）        |
    | `static void sleep(long millis)`                                       | 当前线程休眠指定毫秒（不释放锁）           |
    | `static void sleep(long millis, int nanos)`                            | 精确到纳秒的休眠                   |
    | `static void yield()`                                                  | 让出 CPU 使用权（可能马上被再次调度）(礼让线程/出让线程)      |
    | `void join()`                                                          | 当前线程等待该线程执行完毕(插入线程/插队线程)              |
    | `void join(long millis)`                                               | 等待该线程最多 millis 毫秒          |
    | `void join(long millis, int nanos)`                                    | 等待该线程最多 millis + nanos     |
    | `void interrupt()`                                                     | 中断该线程（设置中断标志位）             |
    | `boolean isInterrupted()`                                              | 判断该线程是否被中断（不清除标志）          |
    | `static boolean interrupted()`                                         | 判断当前线程是否被中断（**清除**中断标志）    |
    | `boolean isAlive()`                                                    | 判断线程是否还活着（未终止）             |
    | `String getName()`                                                     | 获取线程名字                     |
    | `void setName(String name)`                                            | 设置线程名字                     |
    | `long getId()`                                                         | 获取线程 ID（从 JDK 5 开始）        |
    | `int getPriority()`                                                    | 获取线程优先级（默认 5）              |
    | `void setPriority(int newPriority)`                                    | 设置线程优先级（范围 1\~10）          |
    | `Thread.State getState()`                                              | 获取线程当前状态（NEW、RUNNABLE 等）   |
    | `ThreadGroup getThreadGroup()`                                         | 获取线程所属线程组                  |
    | `static Thread currentThread()`                                        | 返回当前正在运行的线程对象              |
    | `boolean isDaemon()`                                                   | 判断该线程是否是守护线程               |
    | `void setDaemon(boolean on)`                                           | 设置为守护线程（需在 `start()` 前设置）  |
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
    线程的生命周期从 创建（new） 开始，经过 启动（start） 进入 运行（runnable） 状态，
    期间可能因为调用 sleep() 进入 阻塞（timed waiting） 状态，醒来后继续运行，最后在任务完成或异常时进入 终止（terminated） 状态。


线程安全问题演示(threadSafe01)
    问题1: 相同的票概率性的出现了多次
    问题2: 出现了超出范围的票号ticket
    

同步方法(threadSafe02)
    将同步代码块 synchronized 改造为同步方法


lock锁(xxxxxx)
    TODO






