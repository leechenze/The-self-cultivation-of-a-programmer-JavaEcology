public class GenericClass {
    public static void main(String[] args) {
        A<String> a1 = new A<String>();
        a1.setKey("1");
        String s1 = a1.getKey();

        A<Integer> a2 = new A<Integer>();
        a2.setKey(1);
        Integer i1 = a2.getKey();

        A<Object> a3 = new A<Object>();
        a3.setKey(new Object());
        Object obj = a3.getKey();

        // 同样的类，在new对象时泛型指定不同的数据类型，这些对象不能互相赋值
        // a1 = a2; No No No
    }
}

class A<T> {
    private T key;
    public void setKey(T key){
        this.key = key;
    }
    public T getKey() {
        return this.key;
    }
}