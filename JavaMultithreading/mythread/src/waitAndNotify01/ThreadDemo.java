package waitAndNotify01;

public class ThreadDemo {
    public static void main(String[] args) {
        // 创建线程对象
        Cook cook = new Cook();
        Foodie foodie = new Foodie();

        // 给线程设置名字
        cook.setName("cook");
        foodie.setName("foodie");

        // 开启线程
        cook.start();
        foodie.start();

    }
}
