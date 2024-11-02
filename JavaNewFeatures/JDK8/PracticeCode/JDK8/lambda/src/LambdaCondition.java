public class LambdaCondition {
    public static void main(String[] args) {
        // 方法的参数或局部变量类型必须为接口才能使用Lambda
        test(() -> {
        });

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        };

        Flyable f = () -> {
            System.out.println("我会飞啦");
        };
    }

    public static void test(Flyable a) {
        new Person() {
        };
    }
}

// 检测这个接口是不是只有一个抽象方法(检测是否是函数式接口)
@FunctionalInterface
interface Flyable {
    // 接口中有且仅有一个抽象方法
    public abstract void eat();
    // public abstract void eat2();
}