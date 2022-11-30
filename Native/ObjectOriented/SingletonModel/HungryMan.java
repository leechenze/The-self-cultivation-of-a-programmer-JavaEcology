package SingletonModel;

public class HungryMan {
    // 是有的HungryMan类型的类变量；
    private static HungryMan hungryman = new HungryMan();

    // 私有化构造方法时，调用类不能使用new创建对象；
    private HungryMan() {}

    public static HungryMan getInstance() {
        return hungryman;
    }
}
