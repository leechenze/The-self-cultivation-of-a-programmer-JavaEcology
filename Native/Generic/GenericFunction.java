public class GenericFunction {
    public static void main(String[] args) {
        Cc<Object> c = new Cc<Object>();
        c.test0("111");
        c.test1(1);
        c.test1(true);
    }
}
class Cc<E> {
    private E e;
    public static void test3() {
        // 在静态方法中不能使用类定义的泛型,如果使用泛型，只能使用静态方法自己定义的泛型(如test4);
        System.out.println(this.e);
    }
    // 静态方法的泛型方法
    public static <T> void test4(T t) {
        System.out.println(t);
    }
    public <T> void test(T s) {
        T t = s;
    }
    public String test0 (String s) {
        return s;
    }
    // 有返回值的泛型方法
    public <T> T test1(T s) {
        return s;
    }
    public void test2 (String... strs) {
        for(String s : strs) {
            System.out.print(s);
        }
    }
    // 形参为可变参数的泛型方法
    public <T> void test2(T... strs) {
        for (T s : strs) {
            System.out.print(s);
        }
    }
}