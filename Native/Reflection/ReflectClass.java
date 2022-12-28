public class ReflectClass {
    public static void main(String[] args) throws ClassNotFoundException {
        Test1.main();
    }
}
class Person1{
    public String name;
    int age;
}

class Test1{
    public static void main() {
        Person1 p = new Person1();
        // 1.clazz对象中就包含了这个对象p所属的Person1类的所有信息
        Class clazz = p.getClass();
        System.out.println(clazz);
        // 2.通过类名.class创建指定类的class实例
        Class clazz1 = Person1.class;
        System.out.println(clazz1);
        // 3.通过Class的静态方法forName(String className)来获取类的实例
        // forName方法中的参数是要获取的Class实例的类的全路径（包名.类名）;
        // 比较常用的方式
        try {
            Class clazz2 = Class.forName("Person1");
            System.out.println(clazz2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 4.通过ClassLoader方式进行获取（做个了解，很少用到）
        try {
            ClassLoader cl = new Person1().getClass().getClassLoader();
            Class clazz3 = cl.loadClass("Person1");
            System.out.println(clazz3);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}