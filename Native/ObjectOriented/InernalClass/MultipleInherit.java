package InernalClass;

/**
 * 在方法A中重写TestB和testC方法
 * 通过内部类来变相实现类的多重继承（同时继承了B类和C类）
 */
class A{
    public void testB() {
        new InerB().testB();
    }
    public void testC() {
        new InerC().testC();
    }
    private class InerB extends B{
        @Override
        public void testB(){
            System.out.println("重写之后的testB方法");
        }
    }
    private class InerC extends C{
        @Override
        public void testC(){
            System.out.println("重写之后的testC方法");
        }
    }
}

class B{
    public void testB(){

    }
}
class C {
    public void testC(){

    }
}

public class MultipleInherit {
    public static void main(String[] args) {
        A a = new A();
        a.testB();
        a.testC();
    }
}
