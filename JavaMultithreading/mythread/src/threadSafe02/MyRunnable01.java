package threadSafe02;

import threadMethod02.MyRunnable;

public class MyRunnable01 implements Runnable{
    /**
     * 循环
     * 先以同步代码块的形式写,最后将synchronized代码块抽离为一个方法(通过快捷键cmd+opt+m)即可
     * 抽离为synchronized方法之后,原来的synchronized代码块就可以删掉了
     * 判断共享数据(ticket)是否到了末尾
     */

    // 这里ticket就没必要声明为static了, 因为MyRunnable只会创建一次
    // 如果是MyThread的话, 那么就可能会有多个对象, 可能会被创建三次(窗口1,2,3)
    int ticket = 0;

    @Override
    public void run() {
        while(true){
            // 这里的同步代码块改为同步方法, 快捷键(cmd+opt+m)
            if (cusMethod()) break;
        }
    }


    private synchronized boolean cusMethod() {
        synchronized (MyRunnable01.class) {
            if(ticket < 100) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ticket++;
                System.out.println(Thread.currentThread().getName() + " 正在卖第 " + ticket + " 张票");
            }else{
                return true;
            }
        }
        return false;
    }
}
