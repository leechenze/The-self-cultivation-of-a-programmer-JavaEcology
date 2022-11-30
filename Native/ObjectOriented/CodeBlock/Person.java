package CodeBlock;

public class Person {
    static int age;
    String name;
    public Person() {
        this.name = "张三";
        System.out.println("执行构造方法");
    }
    public static void showAge() {
        System.out.println(age);
    }
    // 非静态代码块
    {
        System.out.println("执行非静态代码块");
    }
    // 静态代码块
    static {
        // 这里只能操作静态修饰的属性和方法
        // name = "";
        showAge();
        age = 1;
    }
}
