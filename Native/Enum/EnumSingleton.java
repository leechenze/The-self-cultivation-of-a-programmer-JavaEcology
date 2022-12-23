public class EnumSingleton {
    public static void main(String[] args) {
        // Season1.SPRING，这段执行就是获取一个Season对象
        Season1 spring = Season1.SPRING;
        spring.showInfo();

        Season1 spring1 = Season1.SPRING;
        // 每次执行Season1.SPRING获得的都是相同的对象，证明枚举类中的每个枚举都是单例模式
        System.out.println(spring.equals(spring1));
        spring1.test();

    }
}

enum Season1 implements iTest{
    // 此处相当于在调用有参的私有构造函数: Season1
    SPRING("春天", "春暖花开"),
    SUMMER("夏天", "夏日炎炎"),
    AUTUMN("秋天", "秋高气爽"),
    WINTER("冬天", "寒风彻骨");

    private final String name;
    private final String desc;

    private Season1 (String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public void showInfo() {
        System.out.println(this.name + ": " + this.desc);
    }

    @Override
    public void test() {
        System.out.println("这是实现的iTest的test的方法");
    }
}

interface iTest {
    void test();
}