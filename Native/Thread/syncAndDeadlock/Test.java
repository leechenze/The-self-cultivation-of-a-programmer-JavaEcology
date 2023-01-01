package syncAndDeadlock;

public class Test {
    public static void main(String[] args) {
        // 定义账户对象
        Account a = new Account();
        Account a1 = new Account();
        // 定义多线程对象
        User u_wechat = new User(a, 2000);
        User u_alipay = new User(a, 2000);
        Thread wechat = new Thread(u_wechat, "wechat");
        Thread alipay = new Thread(u_alipay, "alipay");
        wechat.start();
        alipay.start();
    }
}

class Account {
    public static int money = 3000;

    // 多线程调用提款方法是有问题的：线程共享资源时，一个线程在执行这个方法没有完毕时，另一个线程又开始执行这个方法
    // 此时就会导致共享的变量money在操作时就有问题了
    // 解决方法：通过同步锁（synchronized）先让一个线程整体执行完这个方法，另一个线程再执行
    // 直接在方法上加上synchronized的关键字
    // 注意：在普通方法上加同步锁，锁的是整个对象，而不是某一个方法(如User的run方法的示例，判断如果是支付宝的线程改用drawing1方法)
    // 不同的账户对象是不同的锁，也就是说普通方法上加synchronized，线程使用不同的此方法的对象，还是会有共享资源问题(如Test类中的示例，u_alipay改用a1对象)
    // 以上情况如果是静态方法的话也不会有这个问题 （如drawing2方法所示）
    public synchronized void drawing(int m) {
        String name = Thread.currentThread().getName();
        // 提款判断账户余额是否充足
        if (money < m) {
            System.out.println(name + "操作账户余额不足,剩余金额" + money);
        } else {
            System.out.println(name + "账号原有金额" + money);
            System.out.println("取款金额" + m);
            money = money - m;
            System.out.println("剩余金额" + money);
        }
    }

    public synchronized void drawing1(int m) {
        String name = Thread.currentThread().getName();
        // 提款判断账户余额是否充足
        if (money < m) {
            System.out.println(name + "操作账户余额不足,剩余金额" + money);
        } else {
            System.out.println(name + "账号原有金额" + money);
            System.out.println("取款金额" + m);
            money = money - m;
            System.out.println("剩余金额" + money);
        }
    }

    // 如果是静态的方法加 synchronized 同步锁时，对于所有对象都是同一个锁
    // 如User的run方法中所示
    public static synchronized void drawing2(int m) {
        String name = Thread.currentThread().getName();
        // 提款判断账户余额是否充足
        if (money < m) {
            System.out.println(name + "操作账户余额不足,剩余金额" + money);
        } else {
            System.out.println(name + "账号原有金额" + money);
            System.out.println("取款金额" + m);
            money = money - m;
            System.out.println("剩余金额" + money);
        }
    }

    // 使用synchronized锁一段代码块 （此时在Test类中声明的u_alipay线程对象再从a1对象改回a对象）
    public void drawing3(int m) {
        // 参数this表示给当前的对象的代码块加同步锁
        // synchronized中的代码块无论是几个方法里面的，其实都是用的同一个同步锁
        // drawing4 示例根据不同的对象拥有不同的锁
        synchronized (this) {
            String name = Thread.currentThread().getName();
            // 提款判断账户余额是否充足
            if (money < m) {
                System.out.println(name + "操作账户余额不足,剩余金额" + money);
            } else {
                System.out.println(name + "账号原有金额" + money);
                System.out.println("取款金额" + m);
                money = money - m;
                System.out.println("剩余金额" + money);
            }
        }
    }

    // synchronized代码块 根据不同的对象拥有不同的锁
    // 只需要将不同的对象通过参数传递进来写入synchronized的参数中即可
    // 此时u_alipay的a再改为a1，改为不同的对象进行验证，结果无误
    public void drawing4(int m, Account a) {
        // synchonized中的参数传入哪个对象就是给哪个对象加锁
        synchronized (a) {
            String name = Thread.currentThread().getName();
            // 提款判断账户余额是否充足
            if (money < m) {
                System.out.println(name + "操作账户余额不足,剩余金额" + money);
            } else {
                System.out.println(name + "账号原有金额" + money);
                System.out.println("取款金额" + m);
                money = money - m;
                System.out.println("剩余金额" + money);
            }
        }
    }

    public void drawing5(int m, Account a) throws InterruptedException {
        // synchonized中的参数传入哪个对象就是给哪个对象加锁
        synchronized (a) {
            String name = Thread.currentThread().getName();

            // 如果是微信操作先不执行，先让支付宝操作完，微信再操作
            if (name.equals("wechat")) {
                a.wait();
            } else if (name.equals("alipay")) {
                // a.notify();
                a.notifyAll();
            }

            // 提款判断账户余额是否充足
            if (money < m) {
                System.out.println(name + "操作账户余额不足,剩余金额" + money);
            } else {
                System.out.println(name + "账号原有金额" + money);
                System.out.println("取款金额" + m);
                money = money - m;
                System.out.println("剩余金额" + money);
            }
        }
    }



}




class User implements Runnable{
    Account account;
    int money;

    public User(Account account, int money) {
        this.account = account;
        this.money = money;
    }

    @Override
    public void run() {
//        if (Thread.currentThread().getName() == "wechat") {
//            account.drawing(money);
//        }else{
//            account.drawing1(money);
//        }
        // 验证静态方法也可以解决资源共享的问题
        // account.drawing2(money);
        // 使用synchronized锁一段代码块
        // account.drawing3(money);
        // 使用synchronized锁定代码块，根据不同的对象拥有不同的锁
        // account.drawing4(money, account);
        // 如果是微信操作先不执行，先让支付宝操作完，微信再操作
        try {
            account.drawing5(money, account);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}