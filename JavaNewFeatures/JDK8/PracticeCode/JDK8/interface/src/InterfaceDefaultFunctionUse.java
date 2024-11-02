public class InterfaceDefaultFunctionUse {
    public static void main(String[] args) {
        BB bb = new BB();
        bb.test01();

        CC cc = new CC();
        cc.test01();
    }
}

interface AA {
    public default void test01() {
        System.out.println("接口 AA 的默认方法 test01");
    }
}

class BB implements AA {

}

class CC implements AA {
    @Override
    public void test01() {
        // AA.super.test01();
        System.out.println("CC 类重写 AA 接口的默认方法 test01");
    }
}

