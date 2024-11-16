public class InterfaceStaticFunctionUse {
    public static void main(String[] args) {
        BBB bbb = new BBB();
        // BBB.test01();
        // bbb.test01();
        AAA.test01();
    }
}


interface AAA {
    public static void test01() {
        System.out.println("接口静态方法");
    }
}

class BBB implements AAA {

}