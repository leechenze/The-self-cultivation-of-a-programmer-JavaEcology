package implementRunnable;

/**
 * 通过实现Runnable接口实现多线程
 */
public class TestRunnable implements Runnable {
    // 验证多个相同线程来处理同一份资源
    // t1 和 t2中会共享run这一个实例，共享count变量
    int count = 0;
    @Override
    public void run() {
        // 获取线程实现类的实例名称 "t1" 和 "t2"
        System.out.println(Thread.currentThread().getName() + "-Runnable多线程运行的代码");
        for(int i = 0; i < 5; i++) {
            count ++;
            System.out.println("这是Runnable多线程的逻辑代码" + count);
        }
    }
}
