public class LambdaIntro {
    public static void main(String[] args) {
        // 使用匿名内部类的方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类的新线程执行了");
            }
        }).start();

        System.out.println();

        // 使用Lamdba的方式
        new Thread(() -> {
            System.out.println("Lambda表达式的新线程执行了");
        }).start();
    }
}
