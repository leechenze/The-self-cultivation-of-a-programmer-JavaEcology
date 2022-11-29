package StudyObject;

public class StudyObject {
    public void studyObject(Object obj) {

    }
    public static void main(String[] args) {
        Father father = new Father();
        Son son = new Son();
        Sun sun = new Sun();

        // equals() 判断实例对象是否是同一个对象（堆内存指向是否相等）
        Father father1 = new Father();
        System.out.println(father.equals(father1)); // false
        father = father1;
        System.out.println(father.equals(father1)); // true

        // hashCode()
        System.out.println(father.hashCode());
        // toString()   获取当前引用对象所在的内存地址
        System.out.println(father.toString());

        // 对象类型转换
        // 从子类到父类的类型转换可以自动进行
        Father father2 = new Father();
        Son son1 = new Son();
        Father father3 = son1;
        // 从父类到子类的类型转换必须通过造型实现（强制类型转换）；
        Father father4 = new Son();
        Son son2 = (Son) father4;
        // 示例:
        Object obj = "hello world";
        String str = (String) obj; // 必须强制类型转换
        System.out.println(str);

    }
}
