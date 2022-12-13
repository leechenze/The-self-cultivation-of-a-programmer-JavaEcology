public class GenericInterface {
    public static void main(String[] args) {
        B1<Object> b1 = new B1<Object>();
        B1<String> b11 = new B1<String>();

        // 如果所有的传入具体数据类型时，那么在构造实例时则无需再指定具体类型
        B2 b2 = new B2();
    }
}

interface IB<T> {
    T test(T t);
}


/**
 * 未传入实参：未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一并加到类中
 */
class B1<T> implements IB<T> {
    @Override
    public T test(T t) {
        return t;
    }
}

/**
 * 传入实参：如果实现接口时指定接口的泛型的具体数据类型，
 * 那么这个类实现接口所有方法的位置都要泛型替换实际的具体数据类型
 */
class B2 implements IB<String> {
    @Override
    public String test(String s) {
        return s;
    }
}